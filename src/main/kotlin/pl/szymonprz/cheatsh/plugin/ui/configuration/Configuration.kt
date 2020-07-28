package pl.szymonprz.cheatsh.plugin.ui.configuration

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import pl.szymonprz.cheatsh.plugin.ui.configuration.Action.enableComments
import pl.szymonprz.cheatsh.plugin.infrastructure.storage.Storage
import pl.szymonprz.cheatsh.plugin.ui.GeneralPanel
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel

class Configuration(project: Project) : Configurable {

    private val storage: Storage = ServiceManager.getService(project, Storage::class.java)

    private val TEXT_DISPLAY_NAME = "Cheat.sh"
    private var generalPanel: GeneralPanel? = null
    private val checkboxes: MutableMap<Action, JCheckBox> = mutableMapOf()


    override fun isModified(): Boolean {
        return checkboxes[enableComments]?.let { it.isSelected != storage.commentsEnabled } ?: false
    }

    override fun getDisplayName(): String = TEXT_DISPLAY_NAME

    override fun apply() {
        checkboxes[enableComments]?.let { storage.commentsEnabled = it.isSelected }
    }

    override fun createComponent(): JComponent {
        checkboxes[enableComments] = JCheckBox(enableComments.text, storage.commentsEnabled)
        val general = GeneralPanel(checkboxes)
        generalPanel = general
        return initRootPanel(general.getPanel())
    }

    private fun initRootPanel(general: JPanel): JPanel {
        val panel = JPanel()
        panel.layout = GridBagLayout()
        val c = GridBagConstraints()
        c.anchor = GridBagConstraints.NORTH
        c.fill = GridBagConstraints.HORIZONTAL
        c.weightx = 1.0
        c.gridx = 0

        c.gridy = 0
        panel.add(general, c)

        c.gridy = 1
        c.weighty = 1.0
        c.fill = GridBagConstraints.BOTH
        val filler = JPanel()
        filler.isOpaque = false
        panel.add(filler, c)

        return panel
    }

    override fun disposeUIResources() {
        checkboxes.clear()
        generalPanel = null
    }

    override fun reset() {
        checkboxes[enableComments]?.let { it.isSelected = storage.commentsEnabled }
    }
}