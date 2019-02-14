package pl.szymonprz.cheatsh.plugin.ui

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.concurrent.atomic.AtomicInteger
import javax.swing.Timer

class KeyPressedAdapter(
    answerHandler: AbstractAnswerHandler,
    private val answerNumber: AtomicInteger
) : KeyAdapter() {

    private val timer = Timer(1000) {
        answerHandler.execute()
    }

    init {
        timer.isRepeats = false
    }

    override fun keyPressed(e: KeyEvent?) {
        val isControlDown = e?.isControlDown ?: false
        if(!isControlDown){
            if (timer.isRunning) {
                answerNumber.set(0)
                timer.restart()
            } else {
                timer.start()
            }
        }
    }
}