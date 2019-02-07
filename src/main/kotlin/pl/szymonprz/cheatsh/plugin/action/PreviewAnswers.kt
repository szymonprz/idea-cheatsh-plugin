package pl.szymonprz.cheatsh.plugin.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.DialogWrapper
import pl.szymonprz.cheatsh.plugin.ui.DisplayAnswerDialog

class PreviewAnswers : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val project = editor.project
        val currentFile = e.getData(PlatformDataKeys.VIRTUAL_FILE)
        val createTableDialog = DisplayAnswerDialog(project, currentFile)
        createTableDialog.show()

        if (createTableDialog.exitCode == DialogWrapper.OK_EXIT_CODE) {
            println("pressed ok" + createTableDialog.getLoadedAnswer())
        } else {
            println("pressed cancel")
        }
    }

    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        e.presentation.isVisible = e.project != null && editor != null
    }


}