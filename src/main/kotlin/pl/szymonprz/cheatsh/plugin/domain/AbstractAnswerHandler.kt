package pl.szymonprz.cheatsh.plugin.domain

import pl.szymonprz.cheatsh.plugin.answerclient.NoAnswerAvailableException

abstract class AbstractAnswerHandler(
    private val answerProvider: AnswerProvider,
    private val questionSupplier: () -> String?
) {

    fun execute() {
        if (shouldExecute()) {
            val askedQuestion = questionSupplier()
            if (!askedQuestion.isNullOrEmpty()) {
                try {
                    val answer = answerProvider.answerFor(askedQuestion, answerNumber())
                    doOnSuccess(answer)

                } catch (e: NoAnswerAvailableException) {
                    doOnError(e)
                }
            }
        }
    }

    abstract fun doOnSuccess(answer: String)
    abstract fun doOnError(e: NoAnswerAvailableException)
    protected open fun answerNumber() = 0
    protected open fun shouldExecute(): Boolean = true
}