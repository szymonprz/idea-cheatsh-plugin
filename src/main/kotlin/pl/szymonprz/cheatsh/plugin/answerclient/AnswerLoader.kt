package pl.szymonprz.cheatsh.plugin.answerclient

interface AnswerLoader {
    fun answerFor(question: String): String
}