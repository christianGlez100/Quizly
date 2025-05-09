package com.sesi.quizly.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen() {
    bodyProfile()
}

@Composable
fun bodyProfile() {
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)){
        Text(text = "Profile")
    }
}
