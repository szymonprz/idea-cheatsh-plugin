package pl.szymonprz.cheatsh.plugin.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.util.TextRange
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
                WriteCommandAction.runWriteCommandAction(project) {
                    document.replaceString(start, end, answer)
                }
            }
            selectionModel.removeSelection(true)
        }
    }

}