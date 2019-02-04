package pl.szymonprz.cheatsh.plugin.question


class QuestionBuilder {
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
        return if (context != "") {
            "$context/$question?Q"
        } else "$question?Q"
    }

    private fun encode(question: String): String {
        return question.replace(Regex("\\s+"), "+")
    }

}