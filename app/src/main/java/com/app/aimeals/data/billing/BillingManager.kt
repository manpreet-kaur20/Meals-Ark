package com.app.aimeals.data.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

sealed class BillingState {
    data object Idle : BillingState()
    data object Connecting : BillingState()
    data object Connected : BillingState()
    data class Error(val message: String, val responseCode: Int? = null) : BillingState()
}

sealed class PurchaseEvent {
    data class Success(
        val planId: String,
        val purchaseToken: String,
        val orderId: String?
    ) : PurchaseEvent()
    data object Cancelled : PurchaseEvent()
    data class Error(val message: String, val responseCode: Int? = null) : PurchaseEvent()
}

@Singleton
class BillingManager @Inject constructor(
    @ApplicationContext private val context: Context
) : PurchasesUpdatedListener {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _billingState = MutableStateFlow<BillingState>(BillingState.Idle)
    val billingState: StateFlow<BillingState> = _billingState.asStateFlow()

    private val _purchaseEvents = MutableSharedFlow<PurchaseEvent>(extraBufferCapacity = 1)
    val purchaseEvents: SharedFlow<PurchaseEvent> = _purchaseEvents.asSharedFlow()

    private val _productDetailsMap = MutableStateFlow<Map<String, ProductDetails>>(emptyMap())
    val productDetailsMap: StateFlow<Map<String, ProductDetails>> = _productDetailsMap.asStateFlow()

    // Subscription product IDs configured on Google Play Console
    companion object {
        const val PLAN_ANNUAL = "mealark_annual"
        const val PLAN_MONTHLY = "mealark_monthly"

        val SUBSCRIPTION_PRODUCT_IDS = listOf(
            PLAN_ANNUAL,
            PLAN_MONTHLY
        )
    }

    private val pendingPurchasesParams = PendingPurchasesParams.newBuilder()
        .enableOneTimeProducts()
        .build()

    private val billingClient: BillingClient by lazy {
        BillingClient.newBuilder(context)
            .setListener(this)
            .enablePendingPurchases(pendingPurchasesParams)
            .build()
    }

    fun startBillingConnection() {
        if (billingClient.isReady) {
            _billingState.value = BillingState.Connected
            querySubscriptions()
            return
        }

        _billingState.value = BillingState.Connecting

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    _billingState.value = BillingState.Connected
                    querySubscriptions()
                } else {
                    _billingState.value = BillingState.Error(
                        message = billingResult.debugMessage.ifBlank { "Billing service connection failed" },
                        responseCode = billingResult.responseCode
                    )
                }
            }

            override fun onBillingServiceDisconnected() {
                _billingState.value = BillingState.Idle
            }
        })
    }

    fun querySubscriptions() {
        if (!billingClient.isReady) return

        val productList = SUBSCRIPTION_PRODUCT_IDS.map { productId ->
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(productId)
                .setProductType(BillingClient.ProductType.SUBS)
                .build()
        }

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient.queryProductDetailsAsync(params) { billingResult, queryProductDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val map = queryProductDetailsList.associateBy { it.productId }
                _productDetailsMap.value = map
            }
        }
    }

    fun launchBillingFlow(
        activity: Activity,
        planType: String, // "Annual" or "Monthly"
        onFallbackTestPurchase: () -> Unit = {}
    ) {
        val productId = if (planType.equals("Annual", ignoreCase = true)) PLAN_ANNUAL else PLAN_MONTHLY
        val productDetails = _productDetailsMap.value[productId]

        if (productDetails != null && billingClient.isReady) {
            val offerToken = productDetails.subscriptionOfferDetails?.firstOrNull()?.offerToken ?: ""

            val productDetailsParamsList = listOf(
                BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails)
                    .setOfferToken(offerToken)
                    .build()
            )

            val flowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build()

            val result = billingClient.launchBillingFlow(activity, flowParams)
            if (result.responseCode != BillingClient.BillingResponseCode.OK) {
                scope.launch {
                    _purchaseEvents.emit(
                        PurchaseEvent.Error("Unable to launch Google Play billing: ${result.debugMessage}")
                    )
                }
            }
        } else {
            // When running in development / test emulator without Google Play account, provide fallback
            onFallbackTestPurchase()
        }
    }

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                if (purchases != null) {
                    for (purchase in purchases) {
                        handlePurchase(purchase)
                    }
                }
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                scope.launch {
                    _purchaseEvents.emit(PurchaseEvent.Cancelled)
                }
            }
            else -> {
                scope.launch {
                    _purchaseEvents.emit(
                        PurchaseEvent.Error(
                            message = billingResult.debugMessage.ifBlank { "Purchase failed with code ${billingResult.responseCode}" },
                            responseCode = billingResult.responseCode
                        )
                    )
                }
            }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            val planId = purchase.products.firstOrNull() ?: PLAN_ANNUAL

            // Acknowledge purchase if needed
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()

                billingClient.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
                    scope.launch {
                        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            _purchaseEvents.emit(
                                PurchaseEvent.Success(
                                    planId = planId,
                                    purchaseToken = purchase.purchaseToken,
                                    orderId = purchase.orderId
                                )
                            )
                        } else {
                            _purchaseEvents.emit(
                                PurchaseEvent.Error(
                                    "Failed to acknowledge purchase: ${billingResult.debugMessage}"
                                )
                            )
                        }
                    }
                }
            } else {
                scope.launch {
                    _purchaseEvents.emit(
                        PurchaseEvent.Success(
                            planId = planId,
                            purchaseToken = purchase.purchaseToken,
                            orderId = purchase.orderId
                        )
                    )
                }
            }
        }
    }

    fun endBillingConnection() {
        if (billingClient.isReady) {
            billingClient.endConnection()
        }
    }
}
