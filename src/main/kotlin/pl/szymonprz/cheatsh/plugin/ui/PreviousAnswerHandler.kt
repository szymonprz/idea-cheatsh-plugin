package pl.szymonprz.cheatsh.plugin.ui

import pl.szymonprz.cheatsh.plugin.answerclient.NoAnswerAvailableException
import pl.szymonprz.cheatsh.plugin.domain.AbstractAnswerHandler
import pl.szymonprz.cheatsh.plugin.domain.AnswerProvider
import java.util.concurrent.atomic.AtomicInteger
import javax.swing.JTextField

class PreviousAnswerHandler(
    answerProvider: AnswerProvider,
    questionField: JTextField,
    private val answerNumber: AtomicInteger,
    private val action: (answer: String) -> Unit
) : AbstractAnswerHandler(answerProvider, { questionField.text }) {

    override fun doOnError(e: NoAnswerAvailableException) {
        answerNumber.incrementAndGet()
        action("cannot load previous answer for your question, try a different one")
    }

    override fun doOnSuccess(answer: String) {
        action(answer)
    }

    override fun answerNumber(): Int {
        return answerNumber.decrementAndGet()
    }

    override fun shouldExecute(): Boolean {
        return answerNumber.get() > 0
    }
}