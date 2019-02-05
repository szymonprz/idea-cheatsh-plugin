package pl.szymonprz.cheatsh.plugin.ui

import com.intellij.ui.IdeBorderFactory
import pl.szymonprz.cheatsh.plugin.model.Action
import java.awt.Dimension
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JCheckBox
import javax.swing.JPanel

class GeneralPanel(private val checkboxes: Map<Action, JCheckBox>) {
    private val TEXT_TITLE_ACTIONS = "General"

    fun getPanel(): JPanel {
        val panel = JPanel()
        panel.border = IdeBorderFactory.createTitledBorder(TEXT_TITLE_ACTIONS)
        panel.layout = BoxLayout(panel, BoxLayout.PAGE_AXIS)
        checkboxes.forEach { panel.add(it.value) }
        panel.add(Box.createHorizontalGlue())
        panel.minimumSize = Dimension(Short.MAX_VALUE.toInt(), 0)
        return panel
    }
}