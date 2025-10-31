package previews

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sesi.quizly.ui.HomeScreen
import com.sesi.quizly.ui.QuizlyHeader

@Preview
@Composable
fun HeaderPreview() {
    Box{
        QuizlyHeader(title = "Quizly"){}
    }
}


@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun HomeScreenPreview() {
    Box {
        HomeScreen(snackbarHostState = SnackbarHostState())
    }
}