package pl.szymonprz.cheatsh.plugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import pl.szymonprz.cheatsh.plugin.answerclient.NoAnswerAvailableException
import java.util.concurrent.atomic.AtomicInteger
import javax.swing.JTextField

class NextAnswerHandler(
    project: Project,
    currentFile: VirtualFile,
    questionField: JTextField,
    private val answerNumber: AtomicInteger,
    private val action: (answer: String) -> Unit
) : AbstractAnswerHandler(project, currentFile, { questionField.text }) {

    override fun doOnError(e: NoAnswerAvailableException) {
        answerNumber.decrementAndGet()
        action("cannot load next answer for your question, try a different one")
    }

    override fun doOnSuccess(answer: String) {
        action(answer)
    }

    override fun answerNumber(): Int {
        return answerNumber.incrementAndGet()
    }
}