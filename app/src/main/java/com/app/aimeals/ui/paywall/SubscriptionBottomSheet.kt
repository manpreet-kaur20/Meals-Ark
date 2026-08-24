package com.app.aimeals.ui.paywall

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.aimeals.presentation.viewmodel.SubscriptionUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionBottomSheet(
    selectedPlan: String, // "Annual" or "Monthly"
    uiState: SubscriptionUiState,
    onDismiss: () -> Unit,
    onConfirmPurchase: (planType: String, activity: Activity) -> Unit,
    onSuccessContinue: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val isAnnual = selectedPlan.equals("Annual", ignoreCase = true)
    val priceText = if (isAnnual) "₹1,499.00" else "₹299.00"
    val originalPriceText = if (isAnnual) "₹2,999.99" else "₹499.00"
    val durationText = if (isAnnual) "year" else "month"

    ModalBottomSheet(
        onDismissRequest = {
            if (uiState !is SubscriptionUiState.Loading) {
                onDismiss()
            }
        },
        sheetState = sheetState,
        containerColor = Color(0xFF0F172A),
        contentColor = Color.White,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = Color(0xFF475569)
            )
        },
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Bar with Title & Close
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF1E293B)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("⛵", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Meal Ark Premium",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF3C649)
                        )
                        Text(
                            text = "Google Play In-App Billing",
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }

                IconButton(
                    onClick = onDismiss,
                    enabled = uiState !is SubscriptionUiState.Loading,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        tint = Color(0xFF94A3B8)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Check if Success State
            if (uiState is SubscriptionUiState.Success) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFF132F2B),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF0F9F80)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Success",
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Welcome to Premium!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Your $selectedPlan subscription is now active. Enjoy unlimited access to all Meal Ark AI features!",
                            fontSize = 13.sp,
                            color = Color(0xFFCBD5E1),
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = onSuccessContinue,
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF3C649),
                                contentColor = Color(0xFF0F172A)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(
                                text = "Start Cooking",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                return@ModalBottomSheet
            }

            // Selected Plan Card
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF1E293B),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFF3C649)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isAnnual) "Annual Premium" else "Monthly Premium",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = if (isAnnual) "Best Value • Billed annually" else "Flexible • Cancel anytime",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8)
                            )
                        }

                        if (isAnnual) {
                            Surface(
                                color = Color(0xFFF3C649),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "SAVE 50%",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = Color(0xFF334155), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text(
                                text = "Total Due Today",
                                fontSize = 12.sp,
                                color = Color(0xFF94A3B8)
                            )
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = priceText,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = " / $durationText",
                                    fontSize = 13.sp,
                                    color = Color(0xFF94A3B8),
                                    modifier = Modifier.padding(bottom = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = originalPriceText,
                            fontSize = 15.sp,
                            color = Color(0xFF64748B),
                            textDecoration = TextDecoration.LineThrough,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // What's Included Feature List
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF152232),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Everything Unlocked with Premium:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFCBD5E1)
                    )

                    val perks = listOf(
                        "Unlimited personalized weekly AI meal plans",
                        "Instant AI cooking assistant & recipe adjustments",
                        "Food photo scanning & camera nutrition tracking",
                        "Smart grocery lists with automatic pantry sync"
                    )

                    perks.forEach { perk ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF0F9F80).copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Check,
                                    contentDescription = "Included",
                                    tint = Color(0xFF0F9F80),
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = perk,
                                fontSize = 12.sp,
                                color = Color(0xFFE2E8F0)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Error Display (if any)
            AnimatedVisibility(
                visible = uiState is SubscriptionUiState.Error,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                if (uiState is SubscriptionUiState.Error) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF450A0A),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ErrorOutline,
                                contentDescription = "Error",
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = uiState.message,
                                fontSize = 12.sp,
                                color = Color(0xFFFCA5A5)
                            )
                        }
                    }
                }
            }

            // Google Play Security Note
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "Secure",
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Encrypted & Secured with Google Play Billing",
                    fontSize = 11.sp,
                    color = Color(0xFF94A3B8)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Subscribe Button
            Button(
                onClick = {
                    if (activity != null) {
                        onConfirmPurchase(selectedPlan, activity)
                    }
                },
                enabled = uiState !is SubscriptionUiState.Loading,
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF3C649),
                    contentColor = Color(0xFF0F172A),
                    disabledContainerColor = Color(0xFF64748B),
                    disabledContentColor = Color(0xFF334155)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                if (uiState is SubscriptionUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color(0xFF0F172A),
                        strokeWidth = 2.5.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Processing purchase...",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "Pay $priceText • Start Subscription",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Subscription renews automatically. You can manage or cancel your subscription anytime in your Google Play Account settings.",
                fontSize = 10.sp,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
