package com.example.aimealplanners

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.aimealplanners.ui.MainScaffold
import com.example.aimealplanners.ui.theme.AIMealPlannersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIMealPlannersTheme {
                MainScaffold()
            }
        }
    }
}
