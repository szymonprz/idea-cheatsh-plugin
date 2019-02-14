package pl.szymonprz.cheatsh.plugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import pl.szymonprz.cheatsh.plugin.answerclient.NoAnswerAvailableException
import javax.swing.JTextField

class OnWrittenQuestionAnswerHandler(
    project: Project,
    currentFile: VirtualFile,
    questionField: JTextField,
    private val action: (answer: String) -> Unit
) : AbstractAnswerHandler(project, currentFile, { questionField.text }) {

    override fun doOnSuccess(answer: String) {
        action(answer)
    }

    override fun doOnError(e: NoAnswerAvailableException) {
        action("no answers for given question")
    }

}