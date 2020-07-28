package pl.szymonprz.cheatsh.plugin.ui

import com.intellij.openapi.vfs.VirtualFile
import pl.szymonprz.cheatsh.plugin.answerclient.NoAnswerAvailableException
import pl.szymonprz.cheatsh.plugin.infrastructure.storage.Storage

class ReplaceQuestionWithAnswerHandler(
    storage: Storage,
    currentFile: VirtualFile?,
    question: String,
    private val action: (answer: String) -> Unit,
    private val fallback: (answer: NoAnswerAvailableException) -> Unit
) : AbstractAnswerHandler(storage, currentFile, { question }) {
    override fun doOnSuccess(answer: String) {
        action(answer)
    }

    override fun doOnError(e: NoAnswerAvailableException) {
        fallback(e)
    }
}