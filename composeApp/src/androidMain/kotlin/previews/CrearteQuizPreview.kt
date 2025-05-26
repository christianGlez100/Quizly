package previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sesi.quizly.ui.quiz.CreateQuizScreen

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun CreateQuizScreenPreview() {
    CreateQuizScreen()
}