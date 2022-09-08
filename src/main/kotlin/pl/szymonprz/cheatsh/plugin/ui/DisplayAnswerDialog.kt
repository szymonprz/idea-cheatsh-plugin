package pl.szymonprz.cheatsh.plugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.vfs.VirtualFile
import pl.szymonprz.cheatsh.plugin.domain.cheatsh.CheatshAnswerProvider
import pl.szymonprz.cheatsh.plugin.infrastructure.http.CheatshAnswerLoader
import pl.szymonprz.cheatsh.plugin.infrastructure.storage.Storage
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.ActionEvent
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.util.concurrent.atomic.AtomicInteger
import javax.swing.*


class DisplayAnswerDialog(storage: Storage, project: Project, currentFile: VirtualFile) : DialogWrapper(false) {
    private val answerNumber = AtomicInteger()
    private val answerProvider =
        CheatshAnswerProvider(storage.commentsEnabled, currentFile.extension, CheatshAnswerLoader())

    private val question = JTextField("", 60)

    private val answer = ScrollableEditorTextField(project, currentFile.fileType)
    private val onWrittenQuestionAnswerHandler =
        OnWrittenQuestionAnswerHandler(answerProvider, question, saveAnswerInTextArea())

    private val keyPressedListener =
        KeyPressedAdapter(onWrittenQuestionAnswerHandler, answerNumber)

    private val previousAnswerHandler =
        PreviousAnswerHandler(answerProvider, question, answerNumber, saveAnswerInTextArea())
    private val previousAnswerAction = object : AbstractAction("Previous answer") {
        override fun actionPerformed(e: ActionEvent?) {
            val worker = object: SwingWorker<Void, Void>(){
                override fun doInBackground(): Void? {
                    previousAnswerHandler.execute()
                    return null
                }
            }
            worker.execute()
        }
    }

    private val nextAnswerHandler =
        NextAnswerHandler(answerProvider, question, answerNumber, saveAnswerInTextArea())
    private val nextAnswerAction = object : AbstractAction("Next answer") {
        override fun actionPerformed(e: ActionEvent?) {
            val worker = object: SwingWorker<Void, Void>(){
                override fun doInBackground(): Void? {
                    nextAnswerHandler.execute()
                    return null
                }
            }
            worker.execute()
        }
    }

    private val nextAnswerTitle = "nextAnswer"
    private val previousAnswerTitle = "previousAnswer"

    init {
        title = "Find snippet"
        init()
        getButton(previousAnswerAction)?.let {
            it.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK), previousAnswerTitle)
            it.actionMap.put(previousAnswerTitle, previousAnswerAction)
        }
        getButton(nextAnswerAction)?.let {
            it.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), nextAnswerTitle)
            it.actionMap.put(nextAnswerTitle, nextAnswerAction)
        }
    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel(GridBagLayout())
        val questionLabel = JLabel("Snippet for: ")
        panel.add(
            questionLabel, GridBagConstraints(
                1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                Insets(0, 0, 5, 5), 0, 0
            )
        )

        question.columns = 60
        question.addKeyListener(keyPressedListener)
        panel.add(
            question, GridBagConstraints(
                2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                Insets(0, 0, 5, 5), 0, 0
            )
        )
        panel.add(
            answer, GridBagConstraints(
                1, 1, 2, 1, 2.0, 2.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                Insets(0, 0, 5, 5), 0, 0
            )
        )
        panel.preferredSize = Dimension(600, 400)
        setResizable(false)
        return panel
    }

    override fun getTitle(): String {
        return question.text
    }

    fun getLoadedAnswer(): String {
        return answer.text
    }

    override fun dispose() {
        super.dispose()
        question.removeKeyListener(keyPressedListener)
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return question
    }

    override fun createLeftSideActions(): Array<Action> {
        return arrayOf(previousAnswerAction, nextAnswerAction)
    }

    private fun saveAnswerInTextArea(): (String) -> Unit = {
        SwingUtilities.invokeLater {
            answer.text = it
        }
    }

}
