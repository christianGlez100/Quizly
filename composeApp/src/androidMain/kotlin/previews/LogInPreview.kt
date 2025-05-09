package previews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.sesi.quizly.di.appModule
import com.sesi.quizly.ui.MainScreenModel
import com.sesi.quizly.ui.login.LogInScreenPre
import io.ktor.client.engine.android.Android
import org.koin.core.context.startKoin
import shared.di.platformModule

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun LogInScreenPreview() {
   /* startKoin  {
        modules(appModule(Android.create()))
        modules(platformModule())
    }
    val mainScreenModel: MainScreenModel = remember { MainScreenModel() }
    val preferenceManager = mainScreenModel.preferences*/
    LogInScreenPre()
}