package shared

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
actual fun showToast(message: String, duration: ToastDuration) {
    val duration = when (duration) {
        ToastDuration.SHORT -> Toast.LENGTH_SHORT
        ToastDuration.LONG -> Toast.LENGTH_LONG
    }
    Toast.makeText(LocalContext.current, message, duration).show()
}