package pl.szymonprz.cheatsh.plugin.ui

import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.ui.EditorTextField
import javax.swing.ScrollPaneConstants

class ScrollableEditorTextField(project: Project?, fileType: FileType) :
    EditorTextField(EditorFactory.getInstance().createDocument(""), project, fileType) {

    override fun createEditor(): EditorEx {
        val editor = super.createEditor()
        editor.scrollPane.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        editor.setHorizontalScrollbarVisible(true)
        editor.isEmbeddedIntoDialogWrapper = true
        editor.isOneLineMode = false
        val settings = editor.settings
        settings.additionalColumnsCount = 3
        settings.isVirtualSpace = false
        return editor
    }
}