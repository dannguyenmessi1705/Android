package com.didan.jetpack.compose.constrainlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.didan.jetpack.compose.constrainlayout.ui.theme.JetpackConstrainLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackConstrainLayoutTheme {
                ConstraintLayoutScreen()
            }
        }
    }
}