package previews

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.sesi.quizly.di.appModule
import com.sesi.quizly.ui.signin.SignInScreen
import io.ktor.client.engine.android.Android
import org.koin.core.context.startKoin

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun LoginScreenPreview() {
    startKoin  {
        modules(appModule(Android.create()))
    }
    SignInScreen(
        snackbarHostState = SnackbarHostState(),
        navController = rememberNavController(),
        preferenceManager = null
    )

}