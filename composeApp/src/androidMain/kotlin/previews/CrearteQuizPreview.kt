package previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sesi.quizly.ui.components.QuestionBlock
import com.sesi.quizly.ui.quiz.CreateQuizScreen

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun CreateQuizScreenPreview() {
    CreateQuizScreen()
}

@Preview(showSystemUi = true, device = "id:pixel_7")
@Composable
fun QuestionBlockPreview(){
    QuestionBlock(index = 0, questions = listOf(), question = "Pregunta 1",isVisible = true){}
}