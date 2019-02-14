package pl.szymonprz.cheatsh.plugin.utils

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleManager

object EditorUtils {
    fun reformatFileInRange(
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