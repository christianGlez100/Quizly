package previews

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.sesi.quizly.di.appModule
import com.sesi.quizly.ui.profile.ProfileScreen
import io.ktor.client.engine.android.Android
import org.koin.core.context.startKoin

@Preview(showBackground = true, device = Devices.PIXEL_7_PRO)
@Composable
fun ProfileScreenPreview() {
    startKoin  {
        modules(appModule(Android.create()))
    }
    ProfileScreen(
        preferenceManager = null,
        snackBarHostState = SnackbarHostState(),
        navController = rememberNavController()
    )
}