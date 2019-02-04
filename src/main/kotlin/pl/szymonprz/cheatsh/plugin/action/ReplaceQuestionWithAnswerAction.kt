package pl.szymonprz.cheatsh.plugin.action

import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleManager
import pl.szymonprz.cheatsh.plugin.answerclient.CheatshAnswerLoader
import pl.szymonprz.cheatsh.plugin.question.QuestionBuilder


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
        val text = document.getText(TextRange.create(start, end)).replace(Regex("\\s+"), "+")
        val currentFile = e.getData(PlatformDataKeys.VIRTUAL_FILE)
        var questionBuilder = QuestionBuilder()
        if (currentFile != null) {
            questionBuilder = questionBuilder.fromFile(currentFile.extension ?: "")
        }

        val question = questionBuilder.fromQuestion(text)
            .build()
        project?.let {
            CheatshAnswerLoader().answerFor(question) { answer ->
                WriteCommandAction.runWriteCommandAction(it) {
                    document.replaceString(start, end, answer)
                    reformatFileInRange(it, psiFile, start, start + answer.length)
                }
            }
            selectionModel.removeSelection(true)
        }
    }

    private fun reformatFileInRange(
        project: Project,
        psiFile: PsiFile?,
        start: Int,
        end: Int
    ) {
        val codeStyleManager = CodeStyleManager.getInstance(project)
        psiFile?.let { file ->
            codeStyleManager.reformatText(file, start, end)
        }
    }

}