package pl.szymonprz.cheatsh.plugin.ui.action

import pl.szymonprz.cheatsh.plugin.answerclient.NoAnswerAvailableException
import pl.szymonprz.cheatsh.plugin.domain.AbstractAnswerHandler
import pl.szymonprz.cheatsh.plugin.domain.AnswerProvider

class ReplaceQuestionWithAnswerHandler(
    answerProvider: AnswerProvider,
    question: String,
    private val action: (answer: String) -> Unit,
    private val fallback: (answer: NoAnswerAvailableException) -> Unit
) : AbstractAnswerHandler(answerProvider, { question }) {
    override fun doOnSuccess(answer: String) {
        action(answer)
    }

    override fun doOnError(e: NoAnswerAvailableException) {
        fallback(e)
    }
}