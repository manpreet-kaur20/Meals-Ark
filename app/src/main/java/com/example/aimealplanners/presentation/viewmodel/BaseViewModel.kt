package com.example.aimealplanners.presentation.viewmodel

import androidx.lifecycle.ViewModel
import java.io.IOException
import retrofit2.HttpException

abstract class BaseViewModel : ViewModel() {
    protected fun handleError(e: Throwable): String {
        return when (e) {
            is IOException -> "Network error. Please check your internet connection."
            is HttpException -> {
                when (e.code()) {
                    400 -> "Bad Request"
                    401 -> "Unauthorized"
                    403 -> "Forbidden"
                    404 -> "Not Found"
                    500 -> "Internal Server Error"
                    else -> "HTTP Error: ${e.code()}"
                }
            }
            else -> e.message ?: "An unknown error occurred"
        }
    }
}
