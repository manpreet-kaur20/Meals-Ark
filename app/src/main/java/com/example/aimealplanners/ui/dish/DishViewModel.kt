package com.example.aimealplanners.ui.dish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aimealplanners.domain.model.Dish
import com.example.aimealplanners.domain.usecase.GetDishesUseCase
import com.example.aimealplanners.domain.usecase.SaveDishUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DishViewModel @Inject constructor(
    private val getDishesUseCase: GetDishesUseCase,
    private val saveDishUseCase: SaveDishUseCase
) : ViewModel() {

    val dishes: StateFlow<List<Dish>> = getDishesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addDish(name: String, category: String, memo: String, url: String) {
        viewModelScope.launch {
            val dish = Dish(
                name = name,
                category = category,
                memo = memo,
                url = url
            )
            saveDishUseCase(dish)
        }
    }
    init {
        // Populate dummy data if empty (simplified for this task)
        viewModelScope.launch {
            if (dishes.value.isEmpty()) {
                val dummyDishes = listOf(
                    Dish(name = "Avocado Toast", category = "Breakfast", memo = "Quick and healthy", url = ""),
                    Dish(name = "Chicken Quinoa Bowl", category = "Lunch", memo = "Meal prep friendly", url = ""),
                    Dish(name = "Salmon with Asparagus", category = "Dinner", memo = "Omega-3 rich", url = ""),
                    Dish(name = "Berry Smoothie", category = "Breakfast", memo = "Refreshing", url = ""),
                    Dish(name = "Lentil Soup", category = "Lunch", memo = "Hearty and vegan", url = "")
                )
                dummyDishes.forEach { saveDishUseCase(it) }
            }
        }
    }
}
