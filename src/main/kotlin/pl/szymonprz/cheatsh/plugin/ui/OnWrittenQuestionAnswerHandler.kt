package pl.szymonprz.cheatsh.plugin.ui

import pl.szymonprz.cheatsh.plugin.answerclient.NoAnswerAvailableException
import pl.szymonprz.cheatsh.plugin.domain.AbstractAnswerHandler
import pl.szymonprz.cheatsh.plugin.domain.AnswerProvider
import javax.swing.JTextField

class OnWrittenQuestionAnswerHandler(
    answerProvider: AnswerProvider,
    questionField: JTextField,
    private val action: (answer: String) -> Unit
) : AbstractAnswerHandler(answerProvider, { questionField.text }) {

    override fun doOnSuccess(answer: String) {
        action(answer)
    }

    override fun doOnError(e: NoAnswerAvailableException) {
        action("no answers for given question")
    }

}