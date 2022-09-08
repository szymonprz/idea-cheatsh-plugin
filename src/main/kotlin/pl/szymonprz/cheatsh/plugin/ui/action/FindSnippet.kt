package pl.szymonprz.cheatsh.plugin.ui.action

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.ui.DialogWrapper
import pl.szymonprz.cheatsh.plugin.infrastructure.storage.Storage
import pl.szymonprz.cheatsh.plugin.ui.DisplayAnswerDialog
import pl.szymonprz.cheatsh.plugin.ui.utils.EditorUtils

class FindSnippet : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val project = editor.project
        val document = editor.document
        val psiFile = e.getData(LangDataKeys.PSI_FILE)
        val caretModel = editor.caretModel
        val currentFile = e.getData(PlatformDataKeys.VIRTUAL_FILE)
        if (project != null && currentFile != null) {
            val storage: Storage = project.getService(Storage::class.java)
            val createTableDialog = DisplayAnswerDialog(storage, project, currentFile)
            createTableDialog.show()
            if (createTableDialog.exitCode == DialogWrapper.OK_EXIT_CODE) {
                val start = caretModel.offset
                WriteCommandAction.runWriteCommandAction(project) {
                    val loadedAnswer = createTableDialog.getLoadedAnswer()
                    document.insertString(start, loadedAnswer)
                    EditorUtils.reformatFileInRange(project, psiFile, start, start + loadedAnswer.length)
                }
            }
        }
    }

    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isVisible = e.project != null && editor != null
    }


}