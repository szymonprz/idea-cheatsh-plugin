package pl.szymonprz.cheatsh.plugin.domain.cheatsh

import pl.szymonprz.cheatsh.plugin.domain.AnswerLoader
import pl.szymonprz.cheatsh.plugin.domain.AnswerProvider
import pl.szymonprz.cheatsh.plugin.domain.question.QuestionBuilder

class CheatshAnswerProvider(private val commentsEnabled: Boolean,
                            private val extension: String?,
                            private val answerLoader: AnswerLoader) : AnswerProvider {
    override fun answerFor(question: String, questionNumber: Int): String {
        val builtQuestion = QuestionBuilder(commentsEnabled, question, extension)
            .askForAnswerNumber(questionNumber)
            .build()
        return answerLoader.answerFor(builtQuestion)
    }
}