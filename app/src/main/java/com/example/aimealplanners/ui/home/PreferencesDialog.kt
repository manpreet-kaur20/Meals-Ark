package com.example.aimealplanners.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aimealplanners.R

@Composable
fun PreferencesScreen(onDismiss: () -> Unit) {
    var step by remember { mutableIntStateOf(1) } // 1: Personal Info, 2: Fitness Goals, 3: Diet Preferences, 4: Nutrition

    // Form State
    var age by remember { mutableStateOf("31") }
    var sex by remember { mutableStateOf("Female") }
    var heightFeet by remember { mutableStateOf("5") }
    var heightInches by remember { mutableStateOf("9") }
    var weightKg by remember { mutableStateOf("74") }
    var activityLevel by remember { mutableStateOf("Sedentary") }

    var planType by remember { mutableStateOf("Daily plan") }
    var selectedGoals by remember { mutableStateOf(setOf("Fat Loss")) }

    var dietaryApproach by remember { mutableStateOf("No specific diet") }
    var budget by remember { mutableStateOf("Low ($3-$8 per meal)") }
    var cuisines by remember { mutableStateOf(mutableListOf("Chinese", "American", "Australian")) }
    var allergies by remember { mutableStateOf(mutableListOf("Tree nuts", "Shellfish", "Soy", "Gluten")) }

    var calorieGoal by remember { androidx.compose.runtime.mutableFloatStateOf(1600f) }
    var specialRequests by remember { mutableStateOf("") }

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
            // Header Bar with Back Arrow & Centered Title "Preferences"
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    onClick = {
                        if (step > 1) {
                            step -= 1
                        } else {
                            onDismiss()
                        }
                    },
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
                    text = "Preferences",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F9F80),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(38.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Subtitle + Centered Step Indicators & Mascot Header Row
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (step) {
                            1 -> "Personal Information"
                            2 -> "Fitness Goals"
                            3 -> "Diet Preferences"
                            else -> "Nutrition"
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF475569),
                        textAlign = TextAlign.Center
                    )

                    Image(
                        painter = painterResource(id = R.drawable.first),
                        contentDescription = "Mascot",
                        modifier = Modifier
                            .size(46.dp)
                            .align(Alignment.CenterEnd)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Step progress pill bar (4 steps - CENTERED)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(4) { idx ->
                        val isActive = (idx + 1) == step
                        Box(
                            modifier = Modifier
                                .height(6.dp)
                                .width(if (isActive) 28.dp else 8.dp)
                                .clip(CircleShape)
                                .background(if (isActive) Color(0xFF10B981) else Color(0xFFCBD5E1))
                        )
                        if (idx < 3) {
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                when (step) {
                    1 -> Step1PersonalInfoContent(
                        age = age, onAgeChange = { age = it },
                        sex = sex, onSexChange = { sex = it },
                        heightFeet = heightFeet, onHeightFeetChange = { heightFeet = it },
                        heightInches = heightInches, onHeightInchesChange = { heightInches = it },
                        weightKg = weightKg, onWeightKgChange = { weightKg = it },
                        activityLevel = activityLevel, onActivityLevelChange = { activityLevel = it },
                        onCancel = onDismiss,
                        onNext = { step = 2 }
                    )
                    2 -> Step2FitnessGoalsContent(
                        planType = planType, onPlanTypeChange = { planType = it },
                        selectedGoals = selectedGoals, onGoalsChange = { selectedGoals = it },
                        onBack = { step = 1 },
                        onNext = { step = 3 }
                    )
                    3 -> Step3DietPreferencesContent(
                        dietaryApproach = dietaryApproach, onDietaryApproachChange = { dietaryApproach = it },
                        budget = budget, onBudgetChange = { budget = it },
                        cuisines = cuisines,
                        allergies = allergies,
                        onBack = { step = 2 },
                        onNext = { step = 4 }
                    )
                    4 -> Step4NutritionContent(
                        calorieGoal = calorieGoal, onCalorieChange = { calorieGoal = it },
                        specialRequests = specialRequests, onRequestChange = { specialRequests = it },
                        onBack = { step = 3 },
                        onSave = onDismiss
                    )
                }
            }
        }
    }
}

@Composable
fun Step1PersonalInfoContent(
    age: String, onAgeChange: (String) -> Unit,
    sex: String, onSexChange: (String) -> Unit,
    heightFeet: String, onHeightFeetChange: (String) -> Unit,
    heightInches: String, onHeightInchesChange: (String) -> Unit,
    weightKg: String, onWeightKgChange: (String) -> Unit,
    activityLevel: String, onActivityLevelChange: (String) -> Unit,
    onCancel: () -> Unit,
    onNext: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Person, contentDescription = "Person", tint = Color(0xFF0F9F80), modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Basic Information", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Age & Sex
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Age", fontSize = 11.sp, color = Color(0xFF64748B))
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = age,
                    onValueChange = onAgeChange,
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF10B981), unfocusedBorderColor = Color(0xFFCBD5E1)),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text("Sex", fontSize = 11.sp, color = Color(0xFF64748B))
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = sex,
                    onValueChange = onSexChange,
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF10B981), unfocusedBorderColor = Color(0xFFCBD5E1)),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Height
        Text("Height", fontSize = 11.sp, color = Color(0xFF64748B))
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = "ft/in",
                onValueChange = {},
                readOnly = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF10B981), unfocusedBorderColor = Color(0xFFCBD5E1)),
                modifier = Modifier.width(76.dp).height(48.dp)
            )
            OutlinedTextField(
                value = heightFeet,
                onValueChange = onHeightFeetChange,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF10B981), unfocusedBorderColor = Color(0xFFCBD5E1)),
                modifier = Modifier.weight(1f).height(48.dp)
            )
            OutlinedTextField(
                value = heightInches,
                onValueChange = onHeightInchesChange,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF10B981), unfocusedBorderColor = Color(0xFFCBD5E1)),
                modifier = Modifier.weight(1f).height(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Weight
        Text("Weight", fontSize = 11.sp, color = Color(0xFF64748B))
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = "kg",
                onValueChange = {},
                readOnly = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF10B981), unfocusedBorderColor = Color(0xFFCBD5E1)),
                modifier = Modifier.width(76.dp).height(48.dp)
            )
            OutlinedTextField(
                value = weightKg,
                onValueChange = onWeightKgChange,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF10B981), unfocusedBorderColor = Color(0xFFCBD5E1)),
                modifier = Modifier.weight(1f).height(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Activity Level
        Text("Activity Level", fontSize = 11.sp, color = Color(0xFF64748B))
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = activityLevel,
            onValueChange = onActivityLevelChange,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF10B981), unfocusedBorderColor = Color(0xFFCBD5E1)),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text("Mostly seated lifestyle with little or no exercise", fontSize = 10.sp, color = Color(0xFF64748B))

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(
                onClick = onCancel,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1)),
                modifier = Modifier.weight(1f).height(46.dp)
            ) {
                Text("Cancel", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            }

            Button(
                onClick = onNext,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F9F80), contentColor = Color.White),
                modifier = Modifier.weight(1f).height(46.dp)
            ) {
                Text("Next", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun Step2FitnessGoalsContent(
    planType: String, onPlanTypeChange: (String) -> Unit,
    selectedGoals: Set<String>, onGoalsChange: (Set<String>) -> Unit,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val goalsList = listOf("Fat Loss", "Build Muscle", "Improve Endurance", "Improve Overall Health", "Boost Energy", "Athletic Performance")

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Restaurant, contentDescription = "Plan", tint = Color(0xFF0F9F80), modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Plan Type", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Radio Option: Daily Plan
        Surface(
            onClick = { onPlanTypeChange("Daily plan") },
            shape = RoundedCornerShape(14.dp),
            color = Color(0xFFECFDF5),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF10B981)),
            modifier = Modifier.fillMaxWidth().height(46.dp)
        ) {
            Row(modifier = Modifier.padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.CheckCircle, contentDescription = "Selected", tint = Color(0xFF10B981), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Daily plan", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Option: Weekly Plan (Premium)
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
            modifier = Modifier.fillMaxWidth().height(46.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.RadioButtonUnchecked, contentDescription = "Unselected", tint = Color(0xFF94A3B8), modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Weekly plan", fontSize = 13.sp, color = Color(0xFF64748B))
                }

                Surface(color = Color(0xFFFEF3C7), shape = RoundedCornerShape(8.dp)) {
                    Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Lock, contentDescription = "Premium", tint = Color(0xFFD97706), modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Premium", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.FitnessCenter, contentDescription = "Goals", tint = Color(0xFF0F9F80), modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Fitness Goals", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        }

        Spacer(modifier = Modifier.height(2.dp))
        Text("Select up to 3 goals", fontSize = 11.sp, color = Color(0xFF64748B))

        Spacer(modifier = Modifier.height(10.dp))

        // 2-Column Goals Grid
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            goalsList.chunked(2).forEach { row ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    row.forEach { goal ->
                        val isSelected = selectedGoals.contains(goal)
                        Surface(
                            onClick = {
                                val newSet = selectedGoals.toMutableSet()
                                if (isSelected) newSet.remove(goal) else newSet.add(goal)
                                onGoalsChange(newSet)
                            },
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) Color(0xFFECFDF5) else Color.White,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) Color(0xFF10B981) else Color(0xFFE2E8F0)),
                            modifier = Modifier.weight(1f).height(42.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(goal, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color(0xFF0F172A))
                                if (isSelected) {
                                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF10B981)))
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(
                onClick = onBack,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1)),
                modifier = Modifier.weight(1f).height(46.dp)
            ) {
                Text("Back", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            }

            Button(
                onClick = onNext,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F9F80), contentColor = Color.White),
                modifier = Modifier.weight(1f).height(46.dp)
            ) {
                Text("Next", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun Step3DietPreferencesContent(
    dietaryApproach: String, onDietaryApproachChange: (String) -> Unit,
    budget: String, onBudgetChange: (String) -> Unit,
    cuisines: MutableList<String>,
    allergies: MutableList<String>,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Dietary Approach", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = dietaryApproach,
            onValueChange = onDietaryApproachChange,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF10B981), unfocusedBorderColor = Color(0xFFCBD5E1)),
            modifier = Modifier.fillMaxWidth().height(46.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text("Budget", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = budget,
            onValueChange = onBudgetChange,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF10B981), unfocusedBorderColor = Color(0xFFCBD5E1)),
            modifier = Modifier.fillMaxWidth().height(46.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text("Cuisine Preferences", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    cuisines.take(3).forEach { c ->
                        Surface(color = Color(0xFFECFDF5), shape = RoundedCornerShape(10.dp)) {
                            Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(c, fontSize = 10.sp, color = Color(0xFF0F9F80))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("✕", fontSize = 9.sp, color = Color(0xFF0F9F80))
                            }
                        }
                    }
                }
                Icon(Icons.Outlined.ChevronRight, contentDescription = "More", tint = Color(0xFF64748B), modifier = Modifier.size(18.dp))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text("Allergies & Restrictions", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    allergies.take(3).forEach { a ->
                        Surface(color = Color(0xFFFFF1F2), shape = RoundedCornerShape(10.dp)) {
                            Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(a, fontSize = 10.sp, color = Color(0xFFE11D48))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("✕", fontSize = 9.sp, color = Color(0xFFE11D48))
                            }
                        }
                    }
                }
                Icon(Icons.Outlined.ChevronRight, contentDescription = "More", tint = Color(0xFF64748B), modifier = Modifier.size(18.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(
                onClick = onBack,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1)),
                modifier = Modifier.weight(1f).height(46.dp)
            ) {
                Text("Back", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            }

            Button(
                onClick = onNext,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F9F80), contentColor = Color.White),
                modifier = Modifier.weight(1f).height(46.dp)
            ) {
                Text("Next", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun Step4NutritionContent(
    calorieGoal: Float, onCalorieChange: (Float) -> Unit,
    specialRequests: String, onRequestChange: (String) -> Unit,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Daily Calorie Goal", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        Spacer(modifier = Modifier.height(6.dp))
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Surface(color = Color(0xFFECFDF5), shape = RoundedCornerShape(10.dp)) {
                        Text("Suggested: 1800 cal", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F9F80), modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onCalorieChange(1800f) }) {
                        Icon(Icons.Outlined.Refresh, contentDescription = "Reset", tint = Color(0xFF0F172A), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reset", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Text("Selected: ", fontSize = 12.sp, color = Color(0xFF64748B))
                    Text("${calorieGoal.toInt()} calories", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F9F80))
                }

                Slider(
                    value = calorieGoal,
                    onValueChange = onCalorieChange,
                    valueRange = 1200f..3500f,
                    colors = SliderDefaults.colors(thumbColor = Color(0xFF10B981), activeTrackColor = Color(0xFF10B981))
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text("Daily Macro Ranges", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Surface(shape = RoundedCornerShape(14.dp), color = Color(0xFFEFF6FF), modifier = Modifier.weight(1f).height(60.dp)) {
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text("🍗 Protein", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1D4ED8))
                    Text("83-153g", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E40AF))
                }
            }
            Surface(shape = RoundedCornerShape(14.dp), color = Color(0xFFFEFCE8), modifier = Modifier.weight(1f).height(60.dp)) {
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text("🌾 Carbs", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFA16207))
                    Text("112-232g", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF854D0E))
                }
            }
            Surface(shape = RoundedCornerShape(14.dp), color = Color(0xFFFFF1F2), modifier = Modifier.weight(1f).height(60.dp)) {
                Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text("💧 Fat", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFBE123C))
                    Text("36-62g", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9F1239))
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Special Requests", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            Spacer(modifier = Modifier.width(6.dp))
            Surface(color = Color(0xFFFEF3C7), shape = RoundedCornerShape(8.dp)) {
                Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Lock, contentDescription = "Premium", tint = Color(0xFFD97706), modifier = Modifier.size(10.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("Premium", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = specialRequests,
            onValueChange = onRequestChange,
            placeholder = { Text("Add specific meal requests, likes/dislikes, spice preferences, health considerations, etc.", fontSize = 11.sp, color = Color(0xFF94A3B8)) },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF10B981), unfocusedBorderColor = Color(0xFFE2E8F0)),
            modifier = Modifier.fillMaxWidth().height(80.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(
                onClick = onBack,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFCBD5E1)),
                modifier = Modifier.weight(1f).height(46.dp)
            ) {
                Text("Back", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            }

            Button(
                onClick = onSave,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F9F80), contentColor = Color.White),
                modifier = Modifier.weight(1f).height(46.dp)
            ) {
                Text("Save", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
