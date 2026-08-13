package com.example.aimealplanners.ui.planner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aimealplanners.domain.model.Dish
import com.example.aimealplanners.domain.model.MealPlan
import com.example.aimealplanners.ui.dish.DishViewModel
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import java.time.DayOfWeek
import java.time.temporal.TemporalAdjusters

@Composable
fun WeeklyPlannerScreen(
    plannerViewModel: MealPlannerViewModel,
    dishViewModel: DishViewModel
) {
    val mealPlans by plannerViewModel.mealPlans.collectAsState()
    val dishes by dishViewModel.dishes.collectAsState()

    val today = LocalDate.now()
    val startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val weekDays = (0..6).map { startOfWeek.plusDays(it.toLong()) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(weekDays) { date ->
            val plan = mealPlans.find { it.date == date } ?: MealPlan(date)
            DayPlanCard(
                date = date,
                plan = plan,
                allDishes = dishes,
                onDishSelected = { slot, dishId ->
                    when (slot) {
                        "Breakfast" -> plannerViewModel.updateMealPlan(date, breakfastId = dishId)
                        "Lunch" -> plannerViewModel.updateMealPlan(date, lunchId = dishId)
                        "Dinner" -> plannerViewModel.updateMealPlan(date, dinnerId = dishId)
                    }
                }
            )
        }
    }
}

@Composable
fun DayPlanCard(
    date: LocalDate,
    plan: MealPlan,
    allDishes: List<Dish>,
    onDishSelected: (String, Long) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())}, ${date.dayOfMonth} ${date.month}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            MealSlot(
                slotName = "Breakfast",
                dishId = plan.breakfastDishId,
                allDishes = allDishes,
                onDishSelected = { onDishSelected("Breakfast", it) }
            )
            MealSlot(
                slotName = "Lunch",
                dishId = plan.lunchDishId,
                allDishes = allDishes,
                onDishSelected = { onDishSelected("Lunch", it) }
            )
            MealSlot(
                slotName = "Dinner",
                dishId = plan.dinnerDishId,
                allDishes = allDishes,
                onDishSelected = { onDishSelected("Dinner", it) }
            )
        }
    }
}

@Composable
fun MealSlot(
    slotName: String,
    dishId: Long?,
    allDishes: List<Dish>,
    onDishSelected: (Long) -> Unit
) {
    var showPicker by remember { mutableStateOf(false) }
    val selectedDish = allDishes.find { it.id == dishId }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = slotName, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
            Text(
                text = selectedDish?.name ?: "Assign Dish",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.clickable { showPicker = true }
            )
        }
    }

    if (showPicker) {
        DishPicker(
            dishes = allDishes,
            onDismiss = { showPicker = false },
            onSelected = {
                onDishSelected(it.id)
                showPicker = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishPicker(
    dishes: List<Dish>,
    onDismiss: () -> Unit,
    onSelected: (Dish) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Dish") },
        text = {
            LazyColumn {
                items(dishes) { dish ->
                    ListItem(
                        headlineContent = { Text(dish.name) },
                        supportingContent = { Text(dish.category) },
                        modifier = Modifier.clickable { onSelected(dish) }
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
