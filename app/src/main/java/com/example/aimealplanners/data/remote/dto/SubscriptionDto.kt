package com.example.aimealplanners.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PurchaseSubscriptionRequest(
    val planId: String,
    val purchaseToken: String? = null,
    val platform: String = "android"
)

@JsonClass(generateAdapter = true)
data class SubscriptionResponse(
    val success: Boolean,
    val message: String,
    val subscriptionId: String? = null
)

@JsonClass(generateAdapter = true)
data class SubscriptionStatusResponse(
    val isActive: Boolean,
    val planType: String,
    val expiresAt: String? = null,
    val mealPlansGenerated: Int,
    val mealPlansLimit: Int,
    val mealsRegenerated: Int,
    val mealsRegeneratedLimit: Int,
    val foodImagesScanned: Int,
    val foodImagesScannedLimit: Int
)
