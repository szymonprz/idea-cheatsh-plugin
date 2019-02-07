package pl.szymonprz.cheatsh.plugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.components.JBScrollPane
import pl.szymonprz.cheatsh.plugin.answerclient.ExecuteActionOnAnswer
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.*


class DisplayAnswerDialog(private val project: Project?, private val currentFile: VirtualFile?) : DialogWrapper(false) {
    private val question = JTextField("", 60)
    private val answer = JTextArea(20, 60)

    private val keyPressedListener = KeyPressedAdapter(project, currentFile, { question.text }) {
        answer.text = it
    }

    init {
        title = "Preview answers"
        setResizable(false)
        init()
    }

    override fun createCenterPanel(): JComponent? {
        val panel = JPanel(GridBagLayout())
        val constraints = GridBagConstraints()
        val questionLabel = JLabel("Question: ")
        constraints.gridx = 0
        constraints.gridy = 0
        panel.add(questionLabel, constraints)
        constraints.gridx = 1
        constraints.gridy = 0
        constraints.weightx = 1.0
        question.addKeyListener(keyPressedListener)
        panel.add(question, constraints)

        constraints.gridx = 0
        constraints.gridy = 1
        constraints.weightx = 0.0
        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridwidth = 2
        constraints.insets = Insets(10, 0, 0, 1)
        answer.isEditable = false
        val scroll = JBScrollPane(answer)
        scroll.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
        scroll.horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        panel.add(scroll, constraints)
        return panel
    }

    override fun getTitle(): String {
        return question.text
    }

    fun getLoadedAnswer(): String? {
        return answer.text
    }

    override fun dispose() {
        super.dispose()
        question.removeKeyListener(keyPressedListener)
    }

    override fun getPreferredFocusedComponent(): JComponent? {
        return question
    }

    class KeyPressedAdapter(
        project: Project?,
        currentFile: VirtualFile?,
        questionProvider: () -> String?,
        action: (answer: String) -> Unit
    ) : KeyAdapter() {

        private val timer = Timer(1000) {
            val question = questionProvider()
            if (!question.isNullOrEmpty()) {
                project?.let { projectHandle ->
                    ExecuteActionOnAnswer(projectHandle, currentFile).apply(question, action)
                }
            }
        }

        init {
            timer.isRepeats = false
        }

        override fun keyPressed(e: KeyEvent?) {
            if (timer.isRunning) {
                timer.restart()
            } else {
                timer.start()
            }
        }
    }
}