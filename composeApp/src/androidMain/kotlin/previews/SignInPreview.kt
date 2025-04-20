package previews

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sesi.quizly.ui.signin.SignInScreen

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun LoginScreenPreview() {
    Box {
        SignInScreen()
    }
}