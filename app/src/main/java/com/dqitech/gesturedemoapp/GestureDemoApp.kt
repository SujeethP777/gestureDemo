package com.dqitech.gesturedemoapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController

@Composable
fun GestureDemoApp() {
    val navController = rememberNavController()
    Surface(color = Color.Blue, modifier = Modifier.fillMaxSize()) {
        GestureNavigation(navController)
    }
}