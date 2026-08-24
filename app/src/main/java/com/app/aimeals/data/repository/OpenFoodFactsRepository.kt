package com.app.aimeals.data.repository

import com.app.aimeals.data.remote.api.OpenFoodFactsApi
import com.app.aimeals.data.remote.api.OpenFoodFactsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenFoodFactsRepository @Inject constructor(
    private val api: OpenFoodFactsApi
) {
    suspend fun lookupBarcode(barcode: String): Result<OpenFoodFactsResponse> {
        return try {
            val response = api.getProductByBarcode(barcode)
            if (response.status == 1 && response.product != null) {
                Result.success(response)
            } else {
                Result.failure(Exception("Product not found for barcode: $barcode"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun isFoodProduct(response: OpenFoodFactsResponse): Boolean {
        val product = response.product ?: return false
        val categories = product.categories?.lowercase() ?: ""
        val name = product.productName?.lowercase() ?: ""

        // Check if it has nutriments data (food items always have this)
        val hasNutriments = product.nutriments != null &&
            (product.nutriments.energyKcal100g != null ||
             product.nutriments.proteins100g != null ||
             product.nutriments.carbohydrates100g != null ||
             product.nutriments.fat100g != null)

        // Non-food categories to reject
        val nonFoodKeywords = listOf(
            "electronics", "beauty", "cosmetic", "cleaning", "detergent",
            "shampoo", "soap", "toothpaste", "medicine", "pharmaceutical",
            "battery", "charger", "cable", "phone", "computer", "toy",
            "clothing", "apparel", "shoe", "book", "stationery"
        )

        val isNonFood = nonFoodKeywords.any { keyword ->
            categories.contains(keyword) || name.contains(keyword)
        }

        return hasNutriments && !isNonFood && name.isNotBlank()
    }
}
