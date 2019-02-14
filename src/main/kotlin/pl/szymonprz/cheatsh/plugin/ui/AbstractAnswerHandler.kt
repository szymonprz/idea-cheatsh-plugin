package pl.szymonprz.cheatsh.plugin.ui

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import pl.szymonprz.cheatsh.plugin.answerclient.CheatshAnswerLoader
import pl.szymonprz.cheatsh.plugin.answerclient.NoAnswerAvailableException
import pl.szymonprz.cheatsh.plugin.model.Storage
import pl.szymonprz.cheatsh.plugin.question.QuestionBuilder
import javax.swing.SwingUtilities

abstract class AbstractAnswerHandler(
    project: Project,
    private val currentFile: VirtualFile?,
    private val questionSupplier: () -> String?
) {

    private val storage: Storage = ServiceManager.getService(project, Storage::class.java)

    fun execute() {
        if (shouldExecute()) {
            val askedQuestion = questionSupplier()
            if (!askedQuestion.isNullOrEmpty()) {
                val builtQuestion = QuestionBuilder(storage, askedQuestion, currentFile?.extension)
                    .askForAnswerNumber(answerNumber())
                    .build()
                try {
                    SwingUtilities.invokeLater {
                        val answer = CheatshAnswerLoader().answerFor(builtQuestion)
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