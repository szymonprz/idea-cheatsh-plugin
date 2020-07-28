package pl.szymonprz.cheatsh.plugin.ui

import pl.szymonprz.cheatsh.plugin.domain.AbstractAnswerHandler
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.concurrent.atomic.AtomicInteger
import javax.swing.SwingUtilities
import javax.swing.Timer

class KeyPressedAdapter(
    answerHandler: AbstractAnswerHandler,
    private val answerNumber: AtomicInteger
) : KeyAdapter() {

    private val timer = Timer(1000) {
        SwingUtilities.invokeLater {
            answerHandler.execute()
        }
    }

    init {
        timer.isRepeats = false
    }

    override fun keyPressed(e: KeyEvent?) {
        val isControlDown = e?.isControlDown ?: false
        if (!isControlDown) {
            if (timer.isRunning) {
                answerNumber.set(0)
                timer.restart()
            } else {
                timer.start()
            }
        }
    }
}