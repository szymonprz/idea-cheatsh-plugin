package pl.szymonprz.cheatsh.plugin.ui

import com.intellij.openapi.vfs.VirtualFile
import pl.szymonprz.cheatsh.plugin.answerclient.NoAnswerAvailableException
import pl.szymonprz.cheatsh.plugin.domain.question.QuestionBuilder
import pl.szymonprz.cheatsh.plugin.infrastructure.http.CheatshAnswerLoader
import pl.szymonprz.cheatsh.plugin.infrastructure.storage.Storage
import javax.swing.SwingUtilities

abstract class AbstractAnswerHandler(
    private val storage: Storage,
    private val currentFile: VirtualFile?,
    private val questionSupplier: () -> String?
) {

    fun execute() {
        if (shouldExecute()) {
            val askedQuestion = questionSupplier()
            if (!askedQuestion.isNullOrEmpty()) {
                val builtQuestion = QuestionBuilder(storage, askedQuestion, currentFile?.extension)
                    .askForAnswerNumber(answerNumber())
                    .build()
                try {
                    SwingUtilities.invokeLater {
                        val answer = CheatshAnswerLoader()
                            .answerFor(builtQuestion)
                        doOnSuccess(answer)
                    }
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