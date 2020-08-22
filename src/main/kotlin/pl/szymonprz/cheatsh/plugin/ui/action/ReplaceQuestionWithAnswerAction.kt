package pl.szymonprz.cheatsh.plugin.ui.action

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.util.TextRange
import pl.szymonprz.cheatsh.plugin.domain.cheatsh.CheatshAnswerProvider
import pl.szymonprz.cheatsh.plugin.infrastructure.http.CheatshAnswerLoader
import pl.szymonprz.cheatsh.plugin.infrastructure.storage.Storage
import pl.szymonprz.cheatsh.plugin.ui.utils.EditorUtils
import javax.swing.SwingWorker


class ReplaceQuestionWithAnswerAction : AnAction("Replace question with answer") {

    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isVisible = e.project != null && editor != null
                && editor.selectionModel.hasSelection()
    }

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val project = editor.project
        val document = editor.document
        val selectionModel = editor.selectionModel
        val psiFile = e.getData(LangDataKeys.PSI_FILE)
        val start = selectionModel.selectionStart
        val end = selectionModel.selectionEnd
        val question = document.getText(TextRange.create(start, end)).replace(Regex("\\s+"), "+")
        val currentFile = e.getData(PlatformDataKeys.VIRTUAL_FILE)
        project?.let { projectHandle ->
            val storage: Storage = ServiceManager.getService(project, Storage::class.java)
            val answerProvider =
                CheatshAnswerProvider(storage.commentsEnabled, currentFile?.extension, CheatshAnswerLoader())

            val handler = ReplaceQuestionWithAnswerHandler(answerProvider,
                question,
                {
                    ApplicationManager.getApplication().invokeLater({
                        WriteCommandAction.runWriteCommandAction(projectHandle) {
                            document.replaceString(start, end, it)
                            EditorUtils.reformatFileInRange(projectHandle, psiFile, start, start + it.length)
                        }
                    }, ModalityState.NON_MODAL)
                },
                {
                    ApplicationManager.getApplication().invokeLater({
                        WriteCommandAction.runWriteCommandAction(projectHandle) {
                            document.replaceString(start, end, "no answers for given question")
                        }
                    }, ModalityState.NON_MODAL)
                }
            )
            val worker = object: SwingWorker<Void, Void>(){
                override fun doInBackground(): Void? {
                    handler.execute()
                    return null
                }
            }
            worker.execute()
            selectionModel.removeSelection(true)


        }
    }
}