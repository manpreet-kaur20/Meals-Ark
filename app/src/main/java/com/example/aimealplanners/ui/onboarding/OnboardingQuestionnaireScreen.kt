package com.example.aimealplanners.ui.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Egg
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material.icons.outlined.TextSnippet
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aimealplanners.R
import com.example.aimealplanners.ui.auth.GoogleLogo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class OptionItem(
    val title: String,
    val subtitle: String? = null
)

data class AttributionItem(
    val title: String,
    val icon: ImageVector? = null,
    val isGoogle: Boolean = false
)

val goalOptions = listOf(
    OptionItem("Fat Loss"),
    OptionItem("Build Muscle"),
    OptionItem("Improve Endurance"),
    OptionItem("Improve Overall Health"),
    OptionItem("Boost Energy"),
    OptionItem("Athletic Performance")
)

val activityOptions = listOf(
    OptionItem("Sedentary", "desk job, no exercise"),
    OptionItem("Lightly Active", "exercise 1-3 days/week"),
    OptionItem("Moderately Active", "exercise 3-5 days/week"),
    OptionItem("Very Active", "exercise 6-7 days/week")
)

val initialCuisines = listOf(
    "American", "Australian", "British",
    "Chinese", "German", "Greek",
    "Indian", "Italian", "Japanese",
    "Korean", "Mediterranean", "Mexican",
    "Pakistani", "Spanish", "Thai"
)

val dietaryApproachOptions = listOf(
    OptionItem("No specific diet"),
    OptionItem("Vegetarian"),
    OptionItem("Vegan"),
    OptionItem("Ketogenic"),
    OptionItem("Low-carb"),
    OptionItem("Flexitarian")
)

val initialAllergies = listOf(
    "Tree nuts", "Peanuts",
    "Shellfish", "Eggs",
    "Soy", "Gluten",
    "Sesame", "Mustard"
)

val budgetOptions = listOf(
    OptionItem("Low"),
    OptionItem("Medium"),
    OptionItem("High")
)

val attributionOptions = listOf(
    AttributionItem("Google", isGoogle = true),
    AttributionItem("Social media (IG, TikTok, etc.)", icon = Icons.Outlined.Share),
    AttributionItem("AI chat (ChatGPT, Claude, etc.)", icon = Icons.Outlined.Psychology),
    AttributionItem("App Store / Play Store", icon = Icons.Outlined.Shop),
    AttributionItem("Ads", icon = Icons.Outlined.Campaign),
    AttributionItem("Friends / Family", icon = Icons.Outlined.Group),
    AttributionItem("Influencer", icon = Icons.Outlined.Star),
    AttributionItem("Other", icon = Icons.Outlined.Language)
)

enum class BottomSheetType {
    SEX, AGE, HEIGHT, WEIGHT, PANTRY_INFO
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingQuestionnaireScreen(
    onBackToSplash: () -> Unit = {},
    onQuestionnaireComplete: () -> Unit = {}
) {
    // 12 Steps:
    // 1: Goals, 2: Activity, 3: About You, 4: Progress, 5: Cuisines, 6: Diet,
    // 7: Allergies, 8: Budget, 9: Variety, 10: Targets, 11: Requests, 12: Attribution
    var currentStep by remember { mutableIntStateOf(1) }

    // State values
    val selectedGoals = remember { mutableStateListOf<String>() }
    var selectedSex by remember { mutableStateOf<String?>(null) }
    var heightDisplayValue by remember { mutableStateOf<String?>(null) }
    var weightDisplayValue by remember { mutableStateOf<String?>(null) }

    var selectedAge by remember { mutableIntStateOf(30) }
    var isSexSelected by remember { mutableStateOf(false) }
    var isAgeSelected by remember { mutableStateOf(false) }
    var isHeightSelected by remember { mutableStateOf(false) }
    var isWeightSelected by remember { mutableStateOf(false) }

    var selectedActivity by remember { mutableStateOf("Lightly Active") }

    // Cuisines State
    val cuisinesList = remember { mutableStateListOf(*initialCuisines.toTypedArray()) }
    val selectedCuisines = remember { mutableStateListOf<String>() }
    var customCuisineInput by remember { mutableStateOf("") }

    // Diet State
    var selectedDietApproach by remember { mutableStateOf("No specific diet") }

    // Allergies State
    val allergiesList = remember { mutableStateListOf(*initialAllergies.toTypedArray()) }
    val selectedAllergies = remember { mutableStateListOf<String>() }
    var hasNoAllergies by remember { mutableStateOf(true) }
    var customAllergyInput by remember { mutableStateOf("") }

    // Budget State
    var selectedBudget by remember { mutableStateOf("Medium") }

    // Variety State
    var sameLunchDinner by remember { mutableStateOf(false) }
    var pantryFirst by remember { mutableStateOf(false) }
    var mealVariety by remember { mutableStateOf("Low") }

    // Targets State
    val suggestedCalorie = 1300
    var calorieGoal by remember { mutableFloatStateOf(1300f) }

    // Special Requests State
    var specialRequestsText by remember { mutableStateOf("") }

    // Attribution State
    var selectedAttribution by remember { mutableStateOf("App Store / Play Store") }

    var activeBottomSheet by remember { mutableStateOf<BottomSheetType?>(null) }
    var showMaxToast by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val progress = (currentStep / 12f).coerceIn(0.08f, 1.0f)

    fun addCustomCuisine() {
        if (customCuisineInput.isNotBlank()) {
            val trimmed = customCuisineInput.trim()
            if (!cuisinesList.contains(trimmed)) {
                cuisinesList.add(trimmed)
            }
            if (!selectedCuisines.contains(trimmed)) {
                selectedCuisines.add(trimmed)
            }
            customCuisineInput = ""
        }
    }

    fun addCustomAllergy() {
        if (customAllergyInput.isNotBlank()) {
            val trimmed = customAllergyInput.trim()
            if (!allergiesList.contains(trimmed)) {
                allergiesList.add(trimmed)
            }
            if (!selectedAllergies.contains(trimmed)) {
                selectedAllergies.add(trimmed)
            }
            hasNoAllergies = false
            customAllergyInput = ""
        }
    }

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
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            // Top Navigation & Progress Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    onClick = {
                        if (currentStep > 1) {
                            currentStep--
                        } else {
                            onBackToSplash()
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    shadowElevation = 6.dp,
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Progress Bar Line
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color(0xFFF1F5F9))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progress)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0xFF0F9F80))
                    )
                }
            }

            // Main Step Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (currentStep) {
                    1 -> GoalsStepContent(
                        selectedGoals = selectedGoals,
                        onGoalToggle = { goal ->
                            if (selectedGoals.contains(goal)) {
                                selectedGoals.remove(goal)
                            } else {
                                if (selectedGoals.size < 3) {
                                    selectedGoals.add(goal)
                                } else {
                                    showMaxToast = true
                                    coroutineScope.launch {
                                        delay(2000)
                                        showMaxToast = false
                                    }
                                }
                            }
                        }
                    )
                    2 -> ActivityStepContent(
                        selectedActivity = selectedActivity,
                        onActivitySelect = { selectedActivity = it }
                    )
                    3 -> AboutYouStepContent(
                        sexValue = if (isSexSelected) selectedSex else null,
                        ageValue = if (isAgeSelected) "$selectedAge years" else null,
                        heightValue = if (isHeightSelected) heightDisplayValue else null,
                        weightValue = if (isWeightSelected) weightDisplayValue else null,
                        onOpenSheet = { activeBottomSheet = it }
                    )
                    4 -> ProgressMotivationStepContent()
                    5 -> CuisinesStepContent(
                        cuisinesList = cuisinesList,
                        selectedCuisines = selectedCuisines,
                        customCuisineInput = customCuisineInput,
                        onCustomInputChanged = { customCuisineInput = it },
                        onAddCustomCuisine = { addCustomCuisine() },
                        onCuisineToggle = { cuisine ->
                            if (selectedCuisines.contains(cuisine)) {
                                selectedCuisines.remove(cuisine)
                            } else {
                                selectedCuisines.add(cuisine)
                            }
                        }
                    )
                    6 -> DietaryApproachStepContent(
                        selectedDietApproach = selectedDietApproach,
                        onDietApproachSelect = { selectedDietApproach = it }
                    )
                    7 -> AllergiesStepContent(
                        allergiesList = allergiesList,
                        selectedAllergies = selectedAllergies,
                        hasNoAllergies = hasNoAllergies,
                        customAllergyInput = customAllergyInput,
                        onCustomInputChanged = { customAllergyInput = it },
                        onAddCustomAllergy = { addCustomAllergy() },
                        onNoAllergiesToggle = {
                            hasNoAllergies = true
                            selectedAllergies.clear()
                        },
                        onAllergyToggle = { allergy ->
                            hasNoAllergies = false
                            if (selectedAllergies.contains(allergy)) {
                                selectedAllergies.remove(allergy)
                                if (selectedAllergies.isEmpty()) hasNoAllergies = true
                            } else {
                                selectedAllergies.add(allergy)
                            }
                        }
                    )
                    8 -> BudgetStepContent(
                        selectedBudget = selectedBudget,
                        onBudgetSelect = { selectedBudget = it }
                    )
                    9 -> VarietyPreferencesStepContent(
                        sameLunchDinner = sameLunchDinner,
                        onSameLunchDinnerToggle = { sameLunchDinner = !sameLunchDinner },
                        pantryFirst = pantryFirst,
                        onPantryFirstToggle = { pantryFirst = !pantryFirst },
                        onOpenPantryInfo = { activeBottomSheet = BottomSheetType.PANTRY_INFO },
                        mealVariety = mealVariety,
                        onMealVarietySelect = { mealVariety = it }
                    )
                    10 -> TargetsStepContent(
                        suggestedCalorie = suggestedCalorie,
                        currentCalorie = calorieGoal,
                        onCalorieChange = { calorieGoal = it },
                        onResetCalorie = { calorieGoal = suggestedCalorie.toFloat() }
                    )
                    11 -> SpecialRequestsStepContent(
                        requestsText = specialRequestsText,
                        onTextChange = { if (it.length <= 200) specialRequestsText = it }
                    )
                    12 -> AttributionStepContent(
                        selectedAttribution = selectedAttribution,
                        onAttributionSelect = { selectedAttribution = it }
                    )
                }
            }

            // Bottom Continue / Create Plan Button
            val isContinueEnabled = when (currentStep) {
                1 -> selectedGoals.isNotEmpty()
                else -> true
            }

            val buttonText = if (currentStep == 12) "Create my plan" else "Continue"

            Button(
                onClick = {
                    if (isContinueEnabled) {
                        if (currentStep < 12) {
                            currentStep++
                        } else {
                            onQuestionnaireComplete()
                        }
                    }
                },
                enabled = isContinueEnabled,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isContinueEnabled) Color(0xFF0F9F80) else Color(0xFF80E4B7),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFA7F3D0),
                    disabledContentColor = Color.White.copy(alpha = 0.7f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(bottom = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = buttonText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = buttonText,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Toast Popup Notification for Max 3 Selection
        AnimatedVisibility(
            visible = showMaxToast,
            enter = fadeIn() + slideInVertically(initialOffsetY = { -40 }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { -40 }),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 60.dp)
        ) {
            Surface(
                color = Color(0xFF71717A).copy(alpha = 0.92f),
                shape = RoundedCornerShape(24.dp),
                shadowElevation = 8.dp
            ) {
                Text(
                    text = "Choose up to 3 goals.",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                )
            }
        }

        // Interactive Bottom Sheets
        if (activeBottomSheet != null) {
            ModalBottomSheet(
                onDismissRequest = { activeBottomSheet = null },
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                containerColor = Color.White,
                dragHandle = {
                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 12.dp)
                            .size(width = 44.dp, height = 4.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFCBD5E1))
                    )
                }
            ) {
                when (activeBottomSheet) {
                    BottomSheetType.SEX -> SexPickerBottomSheet(
                        currentSex = selectedSex,
                        onConfirm = { sex ->
                            selectedSex = sex
                            isSexSelected = true
                            activeBottomSheet = null
                        }
                    )
                    BottomSheetType.AGE -> NumberPickerBottomSheet(
                        title = "Age",
                        minValue = 14,
                        maxValue = 99,
                        initialValue = selectedAge,
                        unitSuffix = " years",
                        onConfirm = { age ->
                            selectedAge = age
                            isAgeSelected = true
                            activeBottomSheet = null
                        }
                    )
                    BottomSheetType.HEIGHT -> HeightPickerBottomSheet(
                        onConfirm = { displayStr ->
                            heightDisplayValue = displayStr
                            isHeightSelected = true
                            activeBottomSheet = null
                        }
                    )
                    BottomSheetType.WEIGHT -> WeightPickerBottomSheet(
                        onConfirm = { displayStr ->
                            weightDisplayValue = displayStr
                            isWeightSelected = true
                            activeBottomSheet = null
                        }
                    )
                    BottomSheetType.PANTRY_INFO -> PantryInfoBottomSheet(
                        onDismiss = { activeBottomSheet = null }
                    )
                    null -> {}
                }
            }
        }
    }
}

// Suggested Targets Step Content (Screenshots 1 & 2)
@Composable
fun TargetsStepContent(
    suggestedCalorie: Int,
    currentCalorie: Float,
    onCalorieChange: (Float) -> Unit,
    onResetCalorie: () -> Unit
) {
    val calInt = currentCalorie.roundToInt()
    val isModified = calInt != suggestedCalorie

    // Macro Calculation based on calorie value
    val minProtein = (calInt * 0.19 / 4).roundToInt()
    val maxProtein = (calInt * 0.35 / 4).roundToInt()

    val minCarbs = (calInt * 0.30 / 4).roundToInt()
    val maxCarbs = (calInt * 0.65 / 4).roundToInt()

    val minFat = (calInt * 0.20 / 9).roundToInt()
    val maxFat = (calInt * 0.35 / 9).roundToInt()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.captain_gus_progress),
            contentDescription = "Captain Gus Targets Mascot",
            modifier = Modifier
                .size(115.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "TARGETS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F9F80),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Suggested targets",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Here is a starting point based on your details; macros update with calories.",
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Daily Calorie Goal Header Label
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = "Calorie Goal",
                tint = Color(0xFF0F9F80),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Daily calorie goal",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Calorie Goal Slider Card (Screenshots 1 & 2)
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFE2E8F0)),
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
                            text = "Suggested",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                        Text(
                            text = "$suggestedCalorie cal",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F9F80)
                        )
                    }

                    if (isModified) {
                        Row(
                            modifier = Modifier.clickable { onResetCalorie() },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Refresh,
                                contentDescription = "Reset",
                                tint = Color(0xFF64748B),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Reset",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF64748B)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Selected: ",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF64748B)
                    )
                    Text(
                        text = "$calInt calories",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F9F80)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Slider(
                    value = currentCalorie,
                    onValueChange = onCalorieChange,
                    valueRange = 1000f..3500f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color(0xFF0F9F80),
                        inactiveTrackColor = Color(0xFFF1F5F9)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Daily Macro Ranges Header Label
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Egg,
                contentDescription = "Macro Ranges",
                tint = Color(0xFF0F9F80),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Daily macro ranges",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 3 Macro Cards Row (Protein, Carbs, Fat)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Protein Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFEFF6FF),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFBFDBFE)),
                modifier = Modifier
                    .weight(1f)
                    .height(84.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Egg,
                            contentDescription = "Protein",
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Protein",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2563EB)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "$minProtein-$maxProtein" + "g",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E40AF)
                    )
                }
            }

            // Carbs Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFFFFBEB),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFDE68A)),
                modifier = Modifier
                    .weight(1f)
                    .height(84.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = "Carbs",
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Carbs",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFD97706)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "$minCarbs-$maxCarbs" + "g",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB45309)
                    )
                }
            }

            // Fat Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFFFF1F2),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFECDD3)),
                modifier = Modifier
                    .weight(1f)
                    .height(84.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.LocalFireDepartment,
                            contentDescription = "Fat",
                            tint = Color(0xFFE11D48),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Fat",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE11D48)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "$minFat-$maxFat" + "g",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFBE123C)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Special Requests Step Content (Screenshot 3)
@Composable
fun SpecialRequestsStepContent(
    requestsText: String,
    onTextChange: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.second),
            contentDescription = "Captain Gus Requests Mascot",
            modifier = Modifier
                .size(115.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "REQUESTS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F9F80),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Any special requests?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Add requests, likes/dislikes, schedule notes, cooking preferences, etc.",
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.TextSnippet,
                    contentDescription = "Special Requests",
                    tint = Color(0xFF0F9F80),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Special requests",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
            }

            Surface(
                color = Color(0xFFECFDF5),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "OPTIONAL",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F9F80),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = requestsText,
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    text = "Examples: Low-prep lunches for tight schedule, no spicy food, high-protein snacks, kid-approved meals...",
                    color = Color(0xFF94A3B8),
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            },
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF14919B),
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "${requestsText.length}/200",
            fontSize = 11.sp,
            color = Color(0xFF94A3B8),
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Attribution Step Content (Screenshot 4)
@Composable
fun AttributionStepContent(
    selectedAttribution: String,
    onAttributionSelect: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.captain_gus_cuisines),
            contentDescription = "Captain Gus Attribution Mascot",
            modifier = Modifier
                .size(115.dp)
                .padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "How did you hear about Meal Ark?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        attributionOptions.forEach { item ->
            val isSelected = selectedAttribution == item.title
            Surface(
                onClick = { onAttributionSelect(item.title) },
                shape = RoundedCornerShape(18.dp),
                color = if (isSelected) Color(0xFFECFDF5) else Color.White,
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    if (isSelected) Color(0xFF14919B) else Color(0xFFE2E8F0)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = Color(0xFFF8FAFC),
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                if (item.isGoogle) {
                                    GoogleLogo(modifier = Modifier.size(18.dp))
                                } else if (item.icon != null) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title,
                                        tint = Color(0xFF475569),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            text = item.title,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                    }

                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = "Selected",
                            tint = Color(0xFF14919B),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Allergies Step Content
@Composable
fun AllergiesStepContent(
    allergiesList: List<String>,
    selectedAllergies: List<String>,
    hasNoAllergies: Boolean,
    customAllergyInput: String,
    onCustomInputChanged: (String) -> Unit,
    onAddCustomAllergy: () -> Unit,
    onNoAllergiesToggle: () -> Unit,
    onAllergyToggle: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.captain_gus_cuisines),
            contentDescription = "Captain Gus Allergies Mascot",
            modifier = Modifier
                .size(115.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "ALLERGIES",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F9F80),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Any allergies?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "We will avoid these in generated meals.",
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            onClick = onNoAllergiesToggle,
            shape = RoundedCornerShape(16.dp),
            color = if (hasNoAllergies) Color(0xFFECFDF5) else Color.White,
            border = androidx.compose.foundation.BorderStroke(
                1.5.dp,
                if (hasNoAllergies) Color(0xFF14919B) else Color(0xFFE2E8F0)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "No allergies",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )

                if (hasNoAllergies) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF14919B)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = "No allergies",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, Color(0xFFCBD5E1), CircleShape)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        val chunkedAllergies = allergiesList.chunked(2)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            chunkedAllergies.forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowItems.forEach { allergy ->
                        val isSelected = selectedAllergies.contains(allergy)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) Color(0xFFFEF2F2) else Color.White)
                                .border(
                                    1.5.dp,
                                    if (isSelected) Color(0xFFFCA5A5) else Color(0xFFE2E8F0),
                                    RoundedCornerShape(16.dp)
                                )
                                .clickable { onAllergyToggle(allergy) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = allergy,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    if (rowItems.size < 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = customAllergyInput,
                onValueChange = onCustomInputChanged,
                placeholder = { Text("Add allergy or restriction", color = Color(0xFF94A3B8), fontSize = 13.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onAddCustomAllergy() }),
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF14919B),
                    unfocusedBorderColor = Color(0xFFE2E8F0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Surface(
                onClick = onAddCustomAllergy,
                shape = RoundedCornerShape(18.dp),
                color = Color(0xFF54D1B5),
                modifier = Modifier.size(52.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add Allergy",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Budget Step Content
@Composable
fun BudgetStepContent(
    selectedBudget: String,
    onBudgetSelect: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.captain_gus_cuisines),
            contentDescription = "Captain Gus Budget Mascot",
            modifier = Modifier
                .size(115.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "BUDGET",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F9F80),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "What budget fits you?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Meal plans will respect your grocery style.",
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        budgetOptions.forEach { option ->
            val isSelected = selectedBudget == option.title
            SelectableCard(
                title = option.title,
                isSelected = isSelected,
                onClick = { onBudgetSelect(option.title) }
            )
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}

// Variety & Meal Planning Preferences Step Content
@Composable
fun VarietyPreferencesStepContent(
    sameLunchDinner: Boolean,
    onSameLunchDinnerToggle: () -> Unit,
    pantryFirst: Boolean,
    onPantryFirstToggle: () -> Unit,
    onOpenPantryInfo: () -> Unit,
    mealVariety: String,
    onMealVarietySelect: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.captain_gus_progress),
            contentDescription = "Captain Gus Variety Mascot",
            modifier = Modifier
                .size(115.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "VARIETY",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F9F80),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Meal planning preferences",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tune repeats, pantry meals, and weekly variety.",
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFE2E8F0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSameLunchDinnerToggle() }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Same lunch and dinner?",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Keeps each day simple",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }

                    if (sameLunchDinner) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF14919B)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.CheckCircle,
                                contentDescription = "Checked",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, Color(0xFFCBD5E1), CircleShape)
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = Color(0xFFF1F5F9)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPantryFirstToggle() }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Pantry-first meals?",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Use what you already have",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            onClick = onOpenPantryInfo,
                            color = Color(0xFFECFDF5),
                            shape = CircleShape,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = "Pantry Info",
                                    tint = Color(0xFF0F9F80),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        if (pantryFirst) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF14919B)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.CheckCircle,
                                    contentDescription = "Checked",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, Color(0xFFCBD5E1), CircleShape)
                            )
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = Color(0xFFF1F5F9)
                )

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Meal variety through the week",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Choose how much your plan can reuse meals",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        color = Color(0xFFECFDF5),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                        ) {
                            listOf("Low", "Medium", "High").forEach { level ->
                                val isSel = mealVariety == level
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(if (isSel) Color(0xFF14919B) else Color.Transparent)
                                        .clickable { onMealVarietySelect(level) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = level,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSel) Color.White else Color(0xFF0F172A)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Pantry Info Bottom Sheet
@Composable
fun PantryInfoBottomSheet(
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Surface(
                onClick = onDismiss,
                color = Color(0xFFF1F5F9),
                shape = CircleShape,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Pantry-first meals",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "You will be able to tell Meal Ark what you have in your kitchen and fridge. Meal Ark will then prioritize meal plans that use those ingredients if you enable this option.",
            fontSize = 14.sp,
            color = Color(0xFF475569),
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(28.dp))
    }
}

// Cuisines Step Content
@Composable
fun CuisinesStepContent(
    cuisinesList: List<String>,
    selectedCuisines: List<String>,
    customCuisineInput: String,
    onCustomInputChanged: (String) -> Unit,
    onAddCustomCuisine: () -> Unit,
    onCuisineToggle: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.captain_gus_cuisines),
            contentDescription = "Captain Gus Cuisines Mascot",
            modifier = Modifier
                .size(115.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "CUISINES",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F9F80),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Which cuisines do you enjoy?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Choose any favorites. You can skip this too.",
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        val chunkedCuisines = cuisinesList.chunked(3)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            chunkedCuisines.forEach { rowCuisines ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowCuisines.forEach { cuisine ->
                        val isSelected = selectedCuisines.contains(cuisine)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) Color(0xFFECFDF5) else Color.White)
                                .border(
                                    1.5.dp,
                                    if (isSelected) Color(0xFF14919B) else Color(0xFFE2E8F0),
                                    RoundedCornerShape(16.dp)
                                )
                                .clickable { onCuisineToggle(cuisine) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cuisine,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    repeat(3 - rowCuisines.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = customCuisineInput,
                onValueChange = onCustomInputChanged,
                placeholder = { Text("Add another cuisine", color = Color(0xFF94A3B8), fontSize = 13.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { onAddCustomCuisine() }),
                shape = RoundedCornerShape(18.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF14919B),
                    unfocusedBorderColor = Color(0xFFE2E8F0),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Surface(
                onClick = onAddCustomCuisine,
                shape = RoundedCornerShape(18.dp),
                color = Color(0xFF54D1B5),
                modifier = Modifier.size(52.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "Add Cuisine",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Dietary Approach Step Content
@Composable
fun DietaryApproachStepContent(
    selectedDietApproach: String,
    onDietApproachSelect: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.captain_gus_cuisines),
            contentDescription = "Captain Gus Dietary Approach Mascot",
            modifier = Modifier
                .size(115.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "DIET",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F9F80),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Any dietary approach?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Choose the style that best matches how you eat.",
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        dietaryApproachOptions.forEach { item ->
            val isSelected = selectedDietApproach == item.title
            SelectableCard(
                title = item.title,
                isSelected = isSelected,
                onClick = { onDietApproachSelect(item.title) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// Height Bottom Sheet Component
@Composable
fun HeightPickerBottomSheet(
    onConfirm: (String) -> Unit
) {
    var unitMode by remember { mutableStateOf("cm") }
    var cmValue by remember { mutableIntStateOf(175) }
    var ftValue by remember { mutableIntStateOf(5) }
    var inValue by remember { mutableIntStateOf(9) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Height",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Surface(
            color = Color(0xFFF1F5F9),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .width(220.dp)
                .height(44.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                val isCm = unitMode == "cm"
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isCm) Color(0xFFECFDF5) else Color.Transparent)
                        .border(
                            1.5.dp,
                            if (isCm) Color(0xFF14919B) else Color.Transparent,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { unitMode = "cm" },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "cm",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isCm) Color(0xFF0F9F80) else Color(0xFF64748B)
                    )
                }

                val isFt = unitMode == "ft/in"
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isFt) Color(0xFFECFDF5) else Color.Transparent)
                        .border(
                            1.5.dp,
                            if (isFt) Color(0xFF14919B) else Color.Transparent,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { unitMode = "ft/in" },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ft/in",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isFt) Color(0xFF0F9F80) else Color(0xFF64748B)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (unitMode == "cm") {
            val items = (120..220).toList()
            val listState = rememberLazyListState(
                initialFirstVisibleItemIndex = (cmValue - 120).coerceIn(0, items.size - 1)
            )
            val selectedIdx by remember { derivedStateOf { listState.firstVisibleItemIndex + 1 } }
            cmValue = items.getOrElse(selectedIdx.coerceIn(0, items.size - 1)) { 175 }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(52.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF8FAFC))
                )

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 64.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(items.size) { idx ->
                        val valItem = items[idx]
                        val isSelected = valItem == cmValue

                        Text(
                            text = if (isSelected) "$valItem cm" else "$valItem",
                            fontSize = if (isSelected) 22.sp else 16.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color(0xFF0F172A) else Color(0xFF94A3B8),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        } else {
            val ftItems = (3..7).toList()
            val inItems = (0..11).toList()

            val ftListState = rememberLazyListState(
                initialFirstVisibleItemIndex = (ftValue - 3).coerceIn(0, ftItems.size - 1)
            )
            val inListState = rememberLazyListState(
                initialFirstVisibleItemIndex = inValue.coerceIn(0, inItems.size - 1)
            )

            val ftIdx by remember { derivedStateOf { ftListState.firstVisibleItemIndex + 1 } }
            val inIdx by remember { derivedStateOf { inListState.firstVisibleItemIndex + 1 } }

            ftValue = ftItems.getOrElse(ftIdx.coerceIn(0, ftItems.size - 1)) { 5 }
            inValue = inItems.getOrElse(inIdx.coerceIn(0, inItems.size - 1)) { 9 }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .width(130.dp)
                            .height(52.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF8FAFC))
                    )
                    Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                    Box(
                        modifier = Modifier
                            .width(130.dp)
                            .height(52.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF8FAFC))
                    )
                }

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    LazyColumn(
                        state = ftListState,
                        modifier = Modifier.width(130.dp),
                        contentPadding = PaddingValues(vertical = 64.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(ftItems.size) { idx ->
                            val valItem = ftItems[idx]
                            val isSelected = valItem == ftValue

                            Text(
                                text = if (isSelected) "$valItem ft" else "$valItem",
                                fontSize = if (isSelected) 20.sp else 15.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color(0xFF0F172A) else Color(0xFF94A3B8),
                                modifier = Modifier.padding(vertical = 10.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    LazyColumn(
                        state = inListState,
                        modifier = Modifier.width(130.dp),
                        contentPadding = PaddingValues(vertical = 64.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(inItems.size) { idx ->
                            val valItem = inItems[idx]
                            val isSelected = valItem == inValue

                            Text(
                                text = if (isSelected) "$valItem in" else "$valItem",
                                fontSize = if (isSelected) 20.sp else 15.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color(0xFF0F172A) else Color(0xFF94A3B8),
                                modifier = Modifier.padding(vertical = 10.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val str = if (unitMode == "cm") "$cmValue cm" else "$ftValue ft $inValue in"
                onConfirm(str)
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0F9F80),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = "OK",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Weight Bottom Sheet Component
@Composable
fun WeightPickerBottomSheet(
    onConfirm: (String) -> Unit
) {
    var unitMode by remember { mutableStateOf("kg") }
    var kgValue by remember { mutableIntStateOf(75) }
    var lbsValue by remember { mutableIntStateOf(165) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Weight",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Surface(
            color = Color(0xFFF1F5F9),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .width(220.dp)
                .height(44.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                val isKg = unitMode == "kg"
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isKg) Color(0xFFECFDF5) else Color.Transparent)
                        .border(
                            1.5.dp,
                            if (isKg) Color(0xFF14919B) else Color.Transparent,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { unitMode = "kg" },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "kg",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isKg) Color(0xFF0F9F80) else Color(0xFF64748B)
                    )
                }

                val isLbs = unitMode == "lbs"
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isLbs) Color(0xFFECFDF5) else Color.Transparent)
                        .border(
                            1.5.dp,
                            if (isLbs) Color(0xFF14919B) else Color.Transparent,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { unitMode = "lbs" },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "lbs",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isLbs) Color(0xFF0F9F80) else Color(0xFF64748B)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val items = if (unitMode == "kg") (35..200).toList() else (75..450).toList()
        val currentValue = if (unitMode == "kg") kgValue else lbsValue
        val listState = rememberLazyListState(
            initialFirstVisibleItemIndex = (currentValue - items.first()).coerceIn(0, items.size - 1)
        )
        val selectedIdx by remember { derivedStateOf { listState.firstVisibleItemIndex + 1 } }
        val selVal = items.getOrElse(selectedIdx.coerceIn(0, items.size - 1)) { currentValue }

        if (unitMode == "kg") kgValue = selVal else lbsValue = selVal

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF8FAFC))
            )

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items.size) { idx ->
                    val valItem = items[idx]
                    val isSelected = valItem == selVal

                    Text(
                        text = if (isSelected) "$valItem $unitMode" else "$valItem",
                        fontSize = if (isSelected) 22.sp else 16.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color(0xFF0F172A) else Color(0xFF94A3B8),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val str = if (unitMode == "kg") "$kgValue kg" else "$lbsValue lbs"
                onConfirm(str)
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0F9F80),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = "OK",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Progress Motivation Content
@Composable
fun ProgressMotivationStepContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.captain_gus_progress),
            contentDescription = "Captain Gus Progress Mascot",
            modifier = Modifier
                .size(115.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "PROGRESS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F9F80),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Real progress has ups and downs",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Some weeks you'll see good progress. Other weeks you may not.",
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFE2E8F0)),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    val w = size.width
                    val h = size.height

                    val p1 = Offset(0f, h * 0.7f)
                    val p2 = Offset(w * 0.28f, h * 0.58f)
                    val p3 = Offset(w * 0.52f, h * 0.45f)
                    val p4 = Offset(w * 0.78f, h * 0.32f)
                    val p5 = Offset(w, h * 0.15f)

                    val path = Path().apply {
                        moveTo(p1.x, p1.y)
                        cubicTo(w * 0.14f, h * 0.7f, w * 0.18f, h * 0.55f, p2.x, p2.y)
                        cubicTo(w * 0.38f, h * 0.65f, w * 0.44f, h * 0.45f, p3.x, p3.y)
                        cubicTo(w * 0.60f, h * 0.45f, w * 0.68f, h * 0.30f, p4.x, p4.y)
                        cubicTo(w * 0.88f, h * 0.32f, w * 0.94f, h * 0.12f, p5.x, p5.y)
                    }

                    val fillPath = Path().apply {
                        addPath(path)
                        lineTo(w, h)
                        lineTo(0f, h)
                        close()
                    }

                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF14919B).copy(alpha = 0.25f), Color.White)
                        )
                    )

                    drawPath(
                        path = path,
                        color = Color(0xFF0F9F80),
                        style = Stroke(width = 4.dp.toPx())
                    )

                    drawCircle(
                        color = Color(0xFF0F9F80),
                        radius = 6.dp.toPx(),
                        center = p5
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Week 1", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                    Text("Week 4", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                    Text("Week 8", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                    Text("Week 12", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFECFDF5),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA7F3D0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            val annotated = buildAnnotatedString {
                withStyle(SpanStyle(color = Color(0xFF0F172A), fontSize = 13.sp)) {
                    append("Meal Ark adapts your plans and keeps you focused on what matters most: ")
                }
                withStyle(SpanStyle(color = Color(0xFF0F172A), fontWeight = FontWeight.Bold, fontSize = 13.sp)) {
                    append("consistency!")
                }
            }

            Text(
                text = annotated,
                modifier = Modifier.padding(18.dp),
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}

// Sex Bottom Sheet Component
@Composable
fun SexPickerBottomSheet(
    currentSex: String?,
    onConfirm: (String) -> Unit
) {
    var tempSex by remember { mutableStateOf(currentSex ?: "Female") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sex",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val isFemale = tempSex == "Female"
            Surface(
                onClick = { tempSex = "Female" },
                shape = RoundedCornerShape(20.dp),
                color = if (isFemale) Color(0xFFECFDF5) else Color(0xFFF8FAFC),
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    if (isFemale) Color(0xFF14919B) else Color(0xFFE2E8F0)
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(115.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(if (isFemale) Color(0xFF14919B) else Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Female,
                            contentDescription = "Female",
                            tint = if (isFemale) Color.White else Color(0xFF64748B),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Female",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                }
            }

            val isMale = tempSex == "Male"
            Surface(
                onClick = { tempSex = "Male" },
                shape = RoundedCornerShape(20.dp),
                color = if (isMale) Color(0xFFECFDF5) else Color(0xFFF8FAFC),
                border = androidx.compose.foundation.BorderStroke(
                    1.5.dp,
                    if (isMale) Color(0xFF14919B) else Color(0xFFE2E8F0)
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(115.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(if (isMale) Color(0xFF14919B) else Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Male,
                            contentDescription = "Male",
                            tint = if (isMale) Color.White else Color(0xFF64748B),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Male",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = { onConfirm(tempSex) },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0F9F80),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = "OK",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Age Number Picker Bottom Sheet
@Composable
fun NumberPickerBottomSheet(
    title: String,
    minValue: Int,
    maxValue: Int,
    initialValue: Int,
    unitSuffix: String = "",
    onConfirm: (Int) -> Unit
) {
    val items = (minValue..maxValue).toList()
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = (initialValue - minValue).coerceIn(0, items.size - 1)
    )
    val selectedIdx by remember { derivedStateOf { listState.firstVisibleItemIndex + 1 } }
    val currentSelectedValue = items.getOrElse(selectedIdx.coerceIn(0, items.size - 1)) { initialValue }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF8FAFC))
            )

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items.size) { idx ->
                    val valItem = items[idx]
                    val isSelected = valItem == currentSelectedValue

                    Text(
                        text = "$valItem$unitSuffix",
                        fontSize = if (isSelected) 22.sp else 16.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color(0xFF0F172A) else Color(0xFF94A3B8),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onConfirm(currentSelectedValue) },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0F9F80),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = "OK",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AboutYouStepContent(
    sexValue: String?,
    ageValue: String?,
    heightValue: String?,
    weightValue: String?,
    onOpenSheet: (BottomSheetType) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.third),
            contentDescription = "Captain Gus About You Mascot",
            modifier = Modifier
                .size(115.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "ABOUT YOU",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F9F80),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "About you",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "This helps us calculate your target calories.",
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        AboutYouRowCard(
            icon = Icons.Outlined.Person,
            label = "Sex",
            valueText = sexValue ?: "select",
            isSelected = sexValue != null,
            onClick = { onOpenSheet(BottomSheetType.SEX) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        AboutYouRowCard(
            icon = Icons.Outlined.Cake,
            label = "Age",
            valueText = ageValue ?: "select",
            isSelected = ageValue != null,
            onClick = { onOpenSheet(BottomSheetType.AGE) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        AboutYouRowCard(
            icon = Icons.Outlined.Straighten,
            label = "Height",
            valueText = heightValue ?: "select",
            isSelected = heightValue != null,
            onClick = { onOpenSheet(BottomSheetType.HEIGHT) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        AboutYouRowCard(
            icon = Icons.Outlined.Scale,
            label = "Weight",
            valueText = weightValue ?: "select",
            isSelected = weightValue != null,
            onClick = { onOpenSheet(BottomSheetType.WEIGHT) }
        )
    }
}

@Composable
fun AboutYouRowCard(
    icon: ImageVector,
    label: String,
    valueText: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFE2E8F0)),
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = Color(0xFFF8FAFC),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = label,
                            tint = Color(0xFF475569),
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = valueText,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                    color = if (isSelected) Color(0xFF0F172A) else Color(0xFF0F9F80)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                    contentDescription = "Select",
                    tint = Color(0xFF0F9F80),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun GoalsStepContent(
    selectedGoals: List<String>,
    onGoalToggle: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.first),
            contentDescription = "Captain Gus Goals Mascot",
            modifier = Modifier
                .size(115.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "GOALS",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F9F80),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "What are you working toward?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Pick the goals that should shape your meal plan.",
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Choose up to 3",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF475569)
        )

        Spacer(modifier = Modifier.height(14.dp))

        goalOptions.forEach { goal ->
            val isSelected = selectedGoals.contains(goal.title)
            SelectableCard(
                title = goal.title,
                isSelected = isSelected,
                onClick = { onGoalToggle(goal.title) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun ActivityStepContent(
    selectedActivity: String,
    onActivitySelect: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.second),
            contentDescription = "Captain Gus Activity Mascot",
            modifier = Modifier
                .size(115.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "ACTIVITY",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F9F80),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "How active are your days?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0F172A),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "This helps estimate how much fuel you need.",
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        activityOptions.forEach { activity ->
            val isSelected = selectedActivity == activity.title
            SelectableCard(
                title = activity.title,
                subtitle = activity.subtitle,
                isSelected = isSelected,
                onClick = { onActivitySelect(activity.title) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun SelectableCard(
    title: String,
    subtitle: String? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFFECFDF5) else Color.White
    val borderColor = if (isSelected) Color(0xFF14919B) else Color(0xFFE2E8F0)

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(if (subtitle != null) 66.dp else 56.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF14919B)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, Color(0xFFCBD5E1), CircleShape)
                )
            }
        }
    }
}
