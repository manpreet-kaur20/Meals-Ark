package com.app.aimeals.ui.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AppWebViewScreen(
    title: String,
    url: String,
    onBack: () -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }
    var webView: WebView? by remember { mutableStateOf(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button (matching screenshot style)
                Surface(
                    onClick = {
                        if (webView?.canGoBack() == true) {
                            webView?.goBack()
                        } else {
                            onBack()
                        }
                    },
                    color = Color.White,
                    shape = RoundedCornerShape(14.dp),
                    shadowElevation = 3.dp,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF1F5F9)),
                    modifier = Modifier.size(42.dp)
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
            }

            // Loading Progress Bar
            AnimatedVisibility(visible = isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF0F9F80),
                    trackColor = Color(0xFFECFDF5)
                )
            }

            // WebView content
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        settings.setSupportZoom(true)
                        settings.builtInZoomControls = false

                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                isLoading = true
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isLoading = false
                            }
                        }

                        loadUrl(url)
                        webView = this
                    }
                },
                update = { view ->
                    if (view.url != url) {
                        view.loadUrl(url)
                    }
                }
            )
        }
    }
}
