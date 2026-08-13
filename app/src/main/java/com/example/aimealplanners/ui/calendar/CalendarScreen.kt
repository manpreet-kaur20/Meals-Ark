package com.example.aimealplanners.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aimealplanners.domain.model.MealPlan
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import java.time.temporal.TemporalAdjusters

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel
) {
    val mealPlans by viewModel.monthMealPlans.collectAsState()
    val today = LocalDate.now()
    val startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth())
    val endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth())
    
    val daysInMonth = (0 until endOfMonth.dayOfMonth).map { startOfMonth.plusDays(it.toLong()) }
    val firstDayOfWeek = startOfMonth.dayOfWeek.value % 7 // 0 for Sunday
    val paddingDays = (0 until firstDayOfWeek).map { null }
    val calendarDays = paddingDays + daysInMonth

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "${today.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${today.year}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            days.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(calendarDays) { date ->
                if (date != null) {
                    val hasPlan = mealPlans.any { it.date == date && (it.breakfastDishId != null || it.lunchDishId != null || it.dinnerDishId != null) }
                    CalendarDayItem(date = date, isToday = date == today, hasPlan = hasPlan)
                } else {
                    Spacer(modifier = Modifier.size(40.dp))
                }
            }
        }
    }
}

@Composable
fun CalendarDayItem(date: LocalDate, isToday: Boolean, hasPlan: Boolean) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = if (isToday) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                color = if (isToday) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
            )
            if (hasPlan) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(color = MaterialTheme.colorScheme.secondary, shape = CircleShape)
                )
            }
        }
    }
}
