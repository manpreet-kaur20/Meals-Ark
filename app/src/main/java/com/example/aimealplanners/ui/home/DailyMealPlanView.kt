package com.example.aimealplanners.ui.home

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.aimealplanners.ui.components.NetworkStatus
import com.example.aimealplanners.ui.components.rememberNetworkStatus
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.aimealplanners.R

data class MealItemData(
    val category: String,
    val categoryIcon: String,
    val title: String,
    val serving: String,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val drawableRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyMealPlanView(
    onOpenStreak: () -> Unit,
    onOpenSettings: () -> Unit,
    onClearPlan: () -> Unit,
    onOpenPremium: () -> Unit,
    onOpenTrackTab: () -> Unit
) {
    val context = LocalContext.current
    val networkStatus by rememberNetworkStatus()
    val isNetworkUnavailable = networkStatus == NetworkStatus.Unavailable

    var isSaved by remember { mutableStateOf(false) }

    var showSaveDialog by remember { mutableStateOf(false) }
    var showClearConfirmDialog by remember { mutableStateOf(false) }
    var showRegenerateConfirmDialog by remember { mutableStateOf(false) }
    var planNameInput by remember { mutableStateOf("Daily Meal Plan - 8/11/2026") }

    val meals = listOf(
        MealItemData("Breakfast", "☕", "Chinese Steamed Egg Custard", "300 g", 250, 22, 5, 15, R.drawable.first),
        MealItemData("Lunch", "🍱", "Australian Beef Salad Bowl", "575 g", 460, 46, 25, 18, R.drawable.good_meal),
        MealItemData("Dinner", "🌙", "American Roasted Turkey Breast", "600 g", 640, 65, 55, 15, R.drawable.avoid_meal),
        MealItemData("Snack", "🍎", "Sliced Cucumber Snack Plate", "375 g", 250, 6, 43, 6, R.drawable.first)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Top Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF10B981)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("AT", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text("Hi, android", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        Text("Let us cook!", fontSize = 12.sp, color = Color(0xFF64748B))
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.first),
                        contentDescription = "Mascot",
                        modifier = Modifier.size(36.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        onClick = onOpenStreak,
                        color = Color(0xFFECFDF5),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Outlined.LocalFireDepartment, contentDescription = "Streak", tint = Color(0xFF10B981), modifier = Modifier.size(20.dp))
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        onClick = onOpenSettings,
                        color = Color(0xFFECFDF5),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(Icons.Outlined.Settings, contentDescription = "Settings", tint = Color(0xFF10B981), modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Title Row & Plan Type Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Your Meal Plan", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F9F80))

                Surface(
                    color = Color(0xFFECFDF5),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA7F3D0))
                ) {
                    Row(modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = Color(0xFF10B981),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Daily", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 6.dp)) {
                            Text("Weekly", fontSize = 11.sp, color = Color(0xFF94A3B8))
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(Icons.Outlined.Lock, contentDescription = "Lock", tint = Color(0xFF94A3B8), modifier = Modifier.size(10.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 3 Action Buttons (Save Plan, Clear, Regenerate)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { showSaveDialog = true },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isSaved) Color(0xFFD1FAE5) else Color(0xFFECFDF5),
                        contentColor = Color(0xFF065F46)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981)),
                    modifier = Modifier.weight(1f).height(42.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Bookmark, contentDescription = "Save", tint = Color(0xFF0F9F80), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isSaved) "Saved" else "Save Plan", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                OutlinedButton(
                    onClick = { showClearConfirmDialog = true },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFECFDF5), contentColor = Color(0xFF065F46)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981)),
                    modifier = Modifier.weight(1f).height(42.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Delete, contentDescription = "Clear", tint = Color(0xFF0F9F80), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Clear", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                OutlinedButton(
                    onClick = { showRegenerateConfirmDialog = true },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFECFDF5), contentColor = Color(0xFF065F46)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981)),
                    modifier = Modifier.weight(1f).height(42.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Refresh, contentDescription = "Regenerate", tint = Color(0xFF0F9F80), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Regenerate", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Grocery & Pantry Banner
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFFEF3C7),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart", tint = Color(0xFFD97706), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Grocery & Pantry", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Lock, contentDescription = "Lock", tint = Color(0xFFD97706), modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Premium", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = "Down", tint = Color(0xFFD97706), modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Macro Summary Card with Calorie Arc Gauge
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left: Calorie Arc Gauge Canvas
                    Box(
                        modifier = Modifier.size(110.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawArc(
                                color = Color(0xFFE2E8F0),
                                startAngle = 135f,
                                sweepAngle = 270f,
                                useCenter = false,
                                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                            )
                            drawArc(
                                color = Color(0xFF0F9F80),
                                startAngle = 135f,
                                sweepAngle = 210f,
                                useCenter = false,
                                style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("1600", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🔥", fontSize = 10.sp)
                                Spacer(modifier = Modifier.width(2.dp))
                                Text("Calories", fontSize = 10.sp, color = Color(0xFF64748B))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Right: 3 Macro Progress Bars (Protein, Carbs, Fat)
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        // Protein
                        Column {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("🍗", fontSize = 10.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Protein", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                }
                                Text("139g", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)).background(Color(0xFFF1F5F9))) {
                                Box(modifier = Modifier.fillMaxWidth(0.85f).height(6.dp).clip(RoundedCornerShape(3.dp)).background(Color(0xFF2563EB)))
                            }
                        }

                        // Carbs
                        Column {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("🌾", fontSize = 10.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Carbs", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                }
                                Text("128g", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)).background(Color(0xFFF1F5F9))) {
                                Box(modifier = Modifier.fillMaxWidth(0.65f).height(6.dp).clip(RoundedCornerShape(3.dp)).background(Color(0xFFEAB308)))
                            }
                        }

                        // Fat
                        Column {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("💧", fontSize = 10.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Fat", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                                }
                                Text("54g", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)).background(Color(0xFFF1F5F9))) {
                                Box(modifier = Modifier.fillMaxWidth(0.75f).height(6.dp).clip(RoundedCornerShape(3.dp)).background(Color(0xFFEA580C)))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 4 Meal Cards (Breakfast, Lunch, Dinner, Snack)
            meals.forEach { meal ->
                MealCardItem(meal = meal, onOpenTrackTab = onOpenTrackTab)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Additional Tips Card
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Additional Tips", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))

                    Spacer(modifier = Modifier.height(12.dp))

                    // Tip 1 (Green)
                    Surface(color = Color(0xFFECFDF5), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("⚡", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Focus on a slight calorie deficit (300-500 calories) and regular exercise for sustainable fat loss.", fontSize = 11.sp, color = Color(0xFF065F46), modifier = Modifier.weight(1f))
                            Icon(Icons.Outlined.OpenInNew, contentDescription = "Link", tint = Color(0xFF065F46), modifier = Modifier.size(14.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Tip 2 (Blue)
                    Surface(color = Color(0xFFEFF6FF), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("🍴", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Prioritize high-protein, high-fiber foods to stay full and preserve muscle during weight loss.", fontSize = 11.sp, color = Color(0xFF1E40AF), modifier = Modifier.weight(1f))
                            Icon(Icons.Outlined.OpenInNew, contentDescription = "Link", tint = Color(0xFF1E40AF), modifier = Modifier.size(14.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Tip 3 (Purple)
                    Surface(color = Color(0xFFFAF5FF), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("🌙", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Schedule regular rest days to support muscle repair and growth.", fontSize = 11.sp, color = Color(0xFF7E22CE), modifier = Modifier.weight(1f))
                            Icon(Icons.Outlined.OpenInNew, contentDescription = "Link", tint = Color(0xFF7E22CE), modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Daily Usage & Upgrade Card
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = Color(0xFFFFF7ED),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFEDD5)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Daily Usage", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        Surface(color = Color(0xFFFEF3C7), shape = RoundedCornerShape(10.dp)) {
                            Text("Free Plan", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706), modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Meal Plan Generations", fontSize = 11.sp, color = Color(0xFF64748B))
                        Text("2/3", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)).background(Color(0xFFF1F5F9))) {
                        Box(modifier = Modifier.fillMaxWidth(0.66f).height(6.dp).clip(RoundedCornerShape(3.dp)).background(Color(0xFF10B981)))
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Meal Regenerations", fontSize = 11.sp, color = Color(0xFF64748B))
                        Text("0/3", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Inner Upgrade Card
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = Color.White.copy(alpha = 0.9f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("⛵", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Upgrade to Premium:", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            val premiumItems = listOf(
                                "Create more personalized weekly plans",
                                "Unlimited meal regenerations with custom input",
                                "Food photo scanning for fast tracking",
                                "Save unlimited meal plans and print recipes",
                                "Chat with your personal cooking assistant",
                                "Smart pantry tracking with AI photo recognition",
                                "Smart grocery lists adjusted to your pantry",
                                "Special requests and advanced customization",
                                "Get recipe suggestions for items in your pantry",
                                "Priority customer support"
                            )

                            premiumItems.forEach { item ->
                                Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text("💡", fontSize = 10.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(item, fontSize = 11.sp, color = Color(0xFF334155), fontWeight = FontWeight.Medium)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Text("T&Cs apply", fontSize = 9.sp, color = Color(0xFF94A3B8), textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())

                            Spacer(modifier = Modifier.height(14.dp))

                            Button(
                                onClick = onOpenPremium,
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEAB308), contentColor = Color(0xFF0F172A)),
                                modifier = Modifier.fillMaxWidth().height(46.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("👑", fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Upgrade to Premium", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // 1. Save Meal Plan Dialog (Screenshot 1)
        if (showSaveDialog) {
            Dialog(onDismissRequest = { showSaveDialog = false }) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 16.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Save Meal Plan", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            IconButton(onClick = { showSaveDialog = false }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color(0xFF64748B), modifier = Modifier.size(18.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Give your meal plan a name to save it for later.", fontSize = 12.sp, color = Color(0xFF64748B))

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Name", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A), modifier = Modifier.width(60.dp))
                            OutlinedTextField(
                                value = planNameInput,
                                onValueChange = { planNameInput = it },
                                singleLine = true,
                                shape = RoundedCornerShape(14.dp),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF10B981), unfocusedBorderColor = Color(0xFFCBD5E1)),
                                modifier = Modifier.weight(1f).height(48.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(22.dp))

                        OutlinedButton(
                            onClick = {
                                isSaved = true
                                showSaveDialog = false
                                Toast.makeText(context, "Meal plan saved!", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFECFDF5), contentColor = Color(0xFF065F46)),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF10B981)),
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            Text("Save Plan", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 2. Clear Meal Plan Confirmation Dialog (Screenshot 2)
        if (showClearConfirmDialog) {
            Dialog(onDismissRequest = { showClearConfirmDialog = false }) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 16.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Clear Meal Plan?", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            IconButton(onClick = { showClearConfirmDialog = false }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color(0xFF64748B), modifier = Modifier.size(18.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            "Are you sure you want to clear this meal plan? Your meal plan is NOT saved. If you want to access it later, save it before clearing.",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B),
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(22.dp))

                        Button(
                            onClick = {
                                showClearConfirmDialog = false
                                onClearPlan() // Resets back to fresh Home Screen!
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444), contentColor = Color.White),
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            Text("Clear", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = { showClearConfirmDialog = false },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF0F172A)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1)),
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            Text("Cancel", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 3. Regenerate Daily Meal Plan Confirmation Dialog (Screenshot 3)
        if (showRegenerateConfirmDialog) {
            Dialog(onDismissRequest = { showRegenerateConfirmDialog = false }) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White,
                    shadowElevation = 16.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("⚠️", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Regenerate Daily Meal Plan?", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                            }
                            IconButton(onClick = { showRegenerateConfirmDialog = false }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color(0xFF64748B), modifier = Modifier.size(18.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            "Your current meal plan has not been saved. If you regenerate now, all current meals and customizations will be permanently lost.",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B),
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        Text("💡 Consider saving it first if you want to access it later.", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))

                        Spacer(modifier = Modifier.height(22.dp))

                        OutlinedButton(
                            onClick = {
                                showRegenerateConfirmDialog = false
                                Toast.makeText(context, "Meal plan regenerated!", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFECFDF5), contentColor = Color(0xFF065F46)),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF10B981)),
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.Refresh, contentDescription = "Regenerate", tint = Color(0xFF065F46), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Regenerate Meal Plan", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedButton(
                            onClick = { showRegenerateConfirmDialog = false },
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF0F172A)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1)),
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            Text("Cancel", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Internet Connection Bottom Sheet
        if (isNetworkUnavailable) {
            BackHandler(enabled = true) {
                // Block back button
            }
            ModalBottomSheet(
                onDismissRequest = { /* Non-dismissible */ },
                sheetState = rememberModalBottomSheetState(
                    confirmValueChange = { false } // Prevent swipe-to-dismiss
                ),
                dragHandle = null,
                containerColor = Color.White,
                scrimColor = Color.Black.copy(alpha = 0.6f)
            ) {
                NoInternetSheetContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoInternetSheetContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 48.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        Surface(
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            color = Color(0xFFFEF2F2)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Outlined.WifiOff,
                    contentDescription = null,
                    tint = Color(0xFFEF4444),
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "No Internet Connection",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = "Your internet connection was lost. Please check your network settings and try again. This app needs internet to provide your personalized meal plans.",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color(0xFF64748B),
            lineHeight = 20.sp
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                color = Color(0xFF10B981),
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Waiting for connection...",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF10B981)
            )
        }
    }
}

@Composable
fun MealCardItem(
    meal: MealItemData,
    onOpenTrackTab: () -> Unit
) {
    val context = LocalContext.current

    Surface(
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Category Badge
            Surface(
                color = Color(0xFFFEF3C7),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(meal.categoryIcon, fontSize = 11.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(meal.category, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = meal.drawableRes),
                    contentDescription = meal.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(16.dp))
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(meal.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Serving: ${meal.serving}", fontSize = 11.sp, color = Color(0xFF64748B))

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🔥", fontSize = 10.sp)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text("${meal.calories}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🍗", fontSize = 10.sp)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text("${meal.protein}g", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🌾", fontSize = 10.sp)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text("${meal.carbs}g", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("💧", fontSize = 10.sp)
                            Spacer(modifier = Modifier.width(2.dp))
                            Text("${meal.fat}g", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // 3 Action Buttons (View Details, Log/Add, Regenerate)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        OutlinedButton(
                            onClick = { Toast.makeText(context, meal.title, Toast.LENGTH_SHORT).show() },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFEFF6FF), contentColor = Color(0xFF1D4ED8)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.weight(1f).height(34.dp)
                        ) {
                            Icon(Icons.Outlined.Visibility, contentDescription = "View", tint = Color(0xFF1D4ED8), modifier = Modifier.size(14.dp))
                        }

                        OutlinedButton(
                            onClick = {
                                Toast.makeText(context, "Logged ${meal.title} to Meal Tracker!", Toast.LENGTH_SHORT).show()
                                onOpenTrackTab()
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFECFDF5), contentColor = Color(0xFF065F46)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF10B981)),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.weight(1f).height(34.dp)
                        ) {
                            Icon(Icons.Outlined.Edit, contentDescription = "Log", tint = Color(0xFF065F46), modifier = Modifier.size(14.dp))
                        }

                        OutlinedButton(
                            onClick = { Toast.makeText(context, "Regenerating ${meal.category}...", Toast.LENGTH_SHORT).show() },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFFFEFCE8), contentColor = Color(0xFFA16207)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFEF08A)),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.weight(1f).height(34.dp)
                        ) {
                            Icon(Icons.Outlined.Refresh, contentDescription = "Regenerate", tint = Color(0xFFA16207), modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
        }
    }
}
