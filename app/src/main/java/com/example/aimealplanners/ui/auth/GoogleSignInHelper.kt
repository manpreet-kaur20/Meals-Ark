package com.example.aimealplanners.ui.auth

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class GoogleAuthManager(private val context: Context) {

    fun getGoogleSignInClient(webClientId: String = ""): GoogleSignInClient {
        val gsoBuilder = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()

        if (webClientId.isNotBlank()) {
            gsoBuilder.requestIdToken(webClientId)
        }

        return GoogleSignIn.getClient(context, gsoBuilder.build())
    }
}

@Composable
fun rememberGoogleSignInLauncher(
    onSuccess: (GoogleSignInAccount) -> Unit,
    onError: (String) -> Unit
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult()
) { result ->
    val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
    try {
        val account = task.getResult(ApiException::class.java)
        if (account != null) {
            onSuccess(account)
        } else {
            onError("Google sign-in returned null account")
        }
    } catch (e: ApiException) {
        onError("Google sign-in error (code ${e.statusCode}): ${e.message}")
    } catch (e: Exception) {
        onError(e.localizedMessage ?: "Google sign-in failed")
    }
}
