package pl.szymonprz.cheatsh.plugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import pl.szymonprz.cheatsh.plugin.answerclient.NoAnswerAvailableException

class ReplaceQuestionWithAnswerHandler(
    project: Project,
    currentFile: VirtualFile?,
    question: String,
    private val action: (answer: String) -> Unit,
    private val fallback: (answer: NoAnswerAvailableException) -> Unit
) : AbstractAnswerHandler(project, currentFile, { question }) {
    override fun doOnSuccess(answer: String) {
        action(answer)
    }

    override fun doOnError(e: NoAnswerAvailableException) {
        fallback(e)
    }
}