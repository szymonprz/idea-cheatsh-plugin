package pl.szymonprz.cheatsh.plugin.domain

interface AnswerProvider {
    fun answerFor(question: String, questionNumber: Int): String
}