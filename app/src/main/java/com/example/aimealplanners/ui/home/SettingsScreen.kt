package com.example.aimealplanners.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onOpenAccount: () -> Unit,
    onOpenPreferences: () -> Unit,
    onOpenPremium: () -> Unit,
    onLogOut: () -> Unit
) {
    var pushNotificationsEnabled by remember { mutableStateOf(true) }
    var isDarkTheme by remember { mutableStateOf(false) }
    var showLogOutConfirm by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // Header Bar with Back Arrow
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    onClick = onBack,
                    color = Color.White,
                    shape = CircleShape,
                    shadowElevation = 2.dp,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF0F172A),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Settings",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F9F80)
                )

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(38.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                // Account Card (Screenshot 1)
                Surface(
                    onClick = onOpenAccount,
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF10B981)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("A", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text("Account", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            Text("Personal info, plan, and security", fontSize = 11.sp, color = Color(0xFF64748B))
                        }

                        Icon(Icons.Outlined.ChevronRight, contentDescription = "Open", tint = Color(0xFF64748B), modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Section: GENERAL
                Text("GENERAL", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        // Preferences
                        SettingsRow(
                            icon = Icons.Outlined.Tune,
                            title = "Preferences",
                            subtitle = "Customize profile, goals, diet, etc.",
                            onClick = onOpenPreferences
                        )

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        // Push Notifications
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(color = Color(0xFFF8FAFC), shape = CircleShape, modifier = Modifier.size(36.dp)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Outlined.Notifications, contentDescription = "Push", tint = Color(0xFF0F172A), modifier = Modifier.size(18.dp))
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text("Push Notifications", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                Text(if (pushNotificationsEnabled) "Enabled" else "Disabled", fontSize = 11.sp, color = Color(0xFF64748B))
                            }

                            Switch(
                                checked = pushNotificationsEnabled,
                                onCheckedChange = { pushNotificationsEnabled = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF10B981))
                            )
                        }

                        HorizontalDivider(color = Color(0xFFF1F5F9))

                        // Theme
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(color = Color(0xFFF8FAFC), shape = CircleShape, modifier = Modifier.size(36.dp)) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Outlined.LightMode, contentDescription = "Theme", tint = Color(0xFF0F172A), modifier = Modifier.size(18.dp))
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text("Theme", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                Text(if (isDarkTheme) "Dark mode" else "Light mode", fontSize = 11.sp, color = Color(0xFF64748B))
                            }

                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = { isDarkTheme = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF10B981))
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Section: PLUS
                Text("PLUS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    onClick = onOpenPremium,
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(color = Color(0xFFFEF3C7), shape = CircleShape, modifier = Modifier.size(38.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("👑", fontSize = 18.sp)
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text("Upgrade to Premium", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            Text("Unlock all premium features", fontSize = 11.sp, color = Color(0xFF64748B))
                        }

                        Icon(Icons.Outlined.ChevronRight, contentDescription = "Open", tint = Color(0xFF64748B), modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Section: LEGAL
                Text("LEGAL", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        SettingsRow(icon = Icons.Outlined.Shield, title = "Privacy Policy", subtitle = "How we protect and use your data")
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                        SettingsRow(icon = Icons.Outlined.Description, title = "Terms of Service", subtitle = "App usage terms and conditions")
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                        SettingsRow(icon = Icons.Outlined.Description, title = "Disclaimer", subtitle = "Important information about app usage")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Section: SUPPORT
                Text("SUPPORT", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SettingsRow(icon = Icons.Outlined.HelpOutline, title = "Help & Support", subtitle = "Browse FAQs and get assistance")
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Section: LOG OUT
                Text("LOG OUT", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    onClick = { showLogOutConfirm = true },
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(color = Color(0xFFFEE2E2), shape = CircleShape, modifier = Modifier.size(38.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.AutoMirrored.Rounded.Logout, contentDescription = "Log Out", tint = Color(0xFFDC2626), modifier = Modifier.size(18.dp))
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text("Log Out", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                            Text("Sign out of your account", fontSize = 11.sp, color = Color(0xFF64748B))
                        }

                        Icon(Icons.Outlined.ChevronRight, contentDescription = "Open", tint = Color(0xFF64748B), modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Log Out Confirmation Dialog
        if (showLogOutConfirm) {
            AlertDialog(
                onDismissRequest = { showLogOutConfirm = false },
                title = { Text("Log Out", fontWeight = FontWeight.Bold) },
                text = { Text("Are you sure you want to log out of your account?") },
                confirmButton = {
                    Button(
                        onClick = {
                            showLogOutConfirm = false
                            onBack()
                            onLogOut()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
                    ) {
                        Text("Log Out")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showLogOutConfirm = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(color = Color(0xFFF8FAFC), shape = CircleShape, modifier = Modifier.size(36.dp)) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = title, tint = Color(0xFF0F172A), modifier = Modifier.size(18.dp))
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            Text(subtitle, fontSize = 11.sp, color = Color(0xFF64748B))
        }

        Icon(Icons.Outlined.ChevronRight, contentDescription = "Open", tint = Color(0xFF64748B), modifier = Modifier.size(20.dp))
    }
}

// Meal Ark Premium Screen (Screenshot 3)
@Composable
fun MealArkPremiumScreen(onDismiss: () -> Unit) {
    var selectedPlan by remember { mutableStateOf("12") } // "1" or "12"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar with Close X and Ship Logo Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Rounded.Close, contentDescription = "Close", tint = Color(0xFF0F172A))
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⛵", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Meal Ark Premium", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                }

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(28.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Plan Cards Side-by-Side (1 Month vs 12 Months)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 1 Month Plan
                val is1Month = selectedPlan == "1"
                Surface(
                    onClick = { selectedPlan = "1" },
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFF1F5F9),
                    border = androidx.compose.foundation.BorderStroke(
                        1.5.dp,
                        if (is1Month) Color(0xFFEAB308) else Color.Transparent
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("1 month", fontSize = 12.sp, color = Color(0xFF64748B))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("₹249.99", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        Text("Billed monthly", fontSize = 10.sp, color = Color(0xFF64748B))
                    }
                }

                // 12 Months Plan (Save 50%)
                val is12Months = selectedPlan == "12"
                Surface(
                    onClick = { selectedPlan = "12" },
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFFF1F5F9),
                    border = androidx.compose.foundation.BorderStroke(
                        1.5.dp,
                        if (is12Months) Color(0xFFEAB308) else Color.Transparent
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(100.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Surface(
                            color = Color(0xFFFDE047),
                            shape = RoundedCornerShape(bottomStart = 10.dp, topEnd = 16.dp),
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Text(
                                text = "Save 50%",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("12 months", fontSize = 12.sp, color = Color(0xFF64748B))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("₹1,499.00", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            Text("Billed Annually", fontSize = 10.sp, color = Color(0xFF64748B))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 9 Premium Features List
            val premiumFeatures = listOf(
                "Create customizable weekly meal plans",
                "Unlimited meal regenerations with custom input",
                "Food photo scanning for instant tracking",
                "Chat with cooking assistant to help you make your meals",
                "Smart grocery lists with AI pantry tracking",
                "Save unlimited meal plans with printable recipes",
                "Advanced meal customization with special requests",
                "Get recipe suggestions for items in your pantry",
                "Priority customer support"
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                premiumFeatures.forEach { feature ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = Color(0xFFFEF3C7),
                            shape = CircleShape,
                            modifier = Modifier.size(20.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("✓", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = feature,
                            fontSize = 12.sp,
                            color = Color(0xFF334155),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Golden Subscribe Button
            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEAB308),
                    contentColor = Color(0xFF0F172A)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Subscribe", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "By proceeding with the payment, you agree to our Terms of Service and Privacy Policy.",
                fontSize = 10.sp,
                color = Color(0xFF94A3B8),
                textAlign = TextAlign.Center
            )
        }
    }
}
