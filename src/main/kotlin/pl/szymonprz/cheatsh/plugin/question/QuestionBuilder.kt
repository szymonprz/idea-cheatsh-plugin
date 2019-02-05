package pl.szymonprz.cheatsh.plugin.question

import pl.szymonprz.cheatsh.plugin.model.Storage


class QuestionBuilder(private val storage: Storage) {
    var context = ""
    var question = ""
    var contextFromQuestion = false

    fun fromFile(extension: String): QuestionBuilder {
        if (contextFromQuestion) return this
        context = LanguageSelector().langFromExtension(extension)
        return this
    }

    fun fromQuestion(value: String): QuestionBuilder {
        if (value.contains("/")) {
            val questionWithContext = value.split("/")
            context = questionWithContext[0]
            question = encode(questionWithContext.drop(1).joinToString(separator = "/"))
            contextFromQuestion = true
        } else {
            question = encode(value)
        }
        return this
    }

    fun build(): String {
        return applyContext(applyCommentsModificator(question))
    }

    private fun applyContext(question: String): String {
        return if (context != "") "$context/$question" else question
    }

    private fun applyCommentsModificator(question: String): String {
        return if (!storage.commentsEnabled) "$question?Q" else question
    }

    private fun encode(question: String): String {
        return question.replace(Regex("\\s+"), "+")
    }

}