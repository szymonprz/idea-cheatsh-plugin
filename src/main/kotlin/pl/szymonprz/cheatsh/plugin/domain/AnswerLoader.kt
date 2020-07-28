package pl.szymonprz.cheatsh.plugin.domain

interface AnswerLoader {
    fun answerFor(question: String): String
}