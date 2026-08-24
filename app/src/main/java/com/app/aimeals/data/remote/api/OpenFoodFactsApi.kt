package com.app.aimeals.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path


interface OpenFoodFactsApi {
    @GET("api/v2/product/{barcode}.json")
    suspend fun getProductByBarcode(
        @Path("barcode") barcode: String
    ): OpenFoodFactsResponse
}
