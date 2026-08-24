package com.app.aimeals.data.remote.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenFoodFactsResponse(
    @Json(name = "status") val status: Int? = null,
    @Json(name = "status_verbose") val statusVerbose: String? = null,
    @Json(name = "product") val product: OpenFoodProduct? = null
)

@JsonClass(generateAdapter = true)
data class OpenFoodProduct(
    @Json(name = "product_name") val productName: String? = null,
    @Json(name = "brands") val brands: String? = null,
    @Json(name = "categories") val categories: String? = null,
    @Json(name = "image_url") val imageUrl: String? = null,
    @Json(name = "quantity") val quantity: String? = null,
    @Json(name = "serving_size") val servingSize: String? = null,
    @Json(name = "nutriments") val nutriments: Nutriments? = null
)

@JsonClass(generateAdapter = true)
data class Nutriments(
    @Json(name = "energy-kcal_100g") val energyKcal100g: Double? = null,
    @Json(name = "energy-kcal_serving") val energyKcalServing: Double? = null,
    @Json(name = "proteins_100g") val proteins100g: Double? = null,
    @Json(name = "proteins_serving") val proteinsServing: Double? = null,
    @Json(name = "carbohydrates_100g") val carbohydrates100g: Double? = null,
    @Json(name = "carbohydrates_serving") val carbohydratesServing: Double? = null,
    @Json(name = "fat_100g") val fat100g: Double? = null,
    @Json(name = "fat_serving") val fatServing: Double? = null,
    @Json(name = "sugars_100g") val sugars100g: Double? = null,
    @Json(name = "fiber_100g") val fiber100g: Double? = null,
    @Json(name = "sodium_100g") val sodium100g: Double? = null,
    @Json(name = "salt_100g") val salt100g: Double? = null
)
