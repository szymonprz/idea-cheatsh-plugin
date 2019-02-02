package pl.szymonprz.cheatsh.plugin.answerclient

interface AnswerLoader {
    fun answerFor(question: String, callback: (answer: String) -> Unit)
}