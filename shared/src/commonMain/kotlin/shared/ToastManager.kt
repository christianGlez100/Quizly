package shared

import androidx.compose.runtime.Composable

@Composable
expect fun showToast(message: String, duration: ToastDuration)
