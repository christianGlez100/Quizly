package com.sesi.quizly.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ButtonQCustom(
    textButton: String,
    isEnabled: Boolean = false,
    textColor: Color,
    buttonContainerColor: Color,
    buttonContentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = buttonContainerColor,
            contentColor = buttonContentColor,
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
        ),
        enabled = isEnabled
    ) {
        Text(
            textButton,
            color = textColor,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
        )
    }
}