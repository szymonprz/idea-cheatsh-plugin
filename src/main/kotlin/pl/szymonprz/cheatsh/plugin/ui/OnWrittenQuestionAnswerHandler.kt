package pl.szymonprz.cheatsh.plugin.ui

import com.intellij.openapi.vfs.VirtualFile
import pl.szymonprz.cheatsh.plugin.answerclient.NoAnswerAvailableException
import pl.szymonprz.cheatsh.plugin.infrastructure.storage.Storage
import javax.swing.JTextField

class OnWrittenQuestionAnswerHandler(
    storage: Storage,
    currentFile: VirtualFile,
    questionField: JTextField,
    private val action: (answer: String) -> Unit
) : AbstractAnswerHandler(storage, currentFile, { questionField.text }) {

    override fun doOnSuccess(answer: String) {
        action(answer)
    }

    override fun doOnError(e: NoAnswerAvailableException) {
        action("no answers for given question")
    }

}