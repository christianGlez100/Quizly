package previews

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sesi.quizly.di.appModule
import com.sesi.quizly.ui.signin.SignInScreen
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun LoginScreenPreview() {
    startKoin  {
        modules(appModule())
    }
        SignInScreen()

}