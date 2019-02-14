package pl.szymonprz.cheatsh.plugin.action

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleManager
import pl.szymonprz.cheatsh.plugin.ui.ReplaceQuestionWithAnswerHandler
import pl.szymonprz.cheatsh.plugin.utils.EditorUtils


class ReplaceQuestionWithAnswerAction : AnAction("ReplaceQuestionWithAnswerAction") {

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

            ReplaceQuestionWithAnswerHandler(projectHandle, currentFile, question,
                {
                    WriteCommandAction.runWriteCommandAction(projectHandle) {
                        document.replaceString(start, end, it)
                        EditorUtils.reformatFileInRange(projectHandle, psiFile, start, start + it.length)
                    }
                }, {
                    WriteCommandAction.runWriteCommandAction(projectHandle) {
                        document.replaceString(start, end, "no answers for given question")
                    }
                }
            ).execute()
            selectionModel.removeSelection(true)
        }
    }
}