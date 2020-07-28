package pl.szymonprz.cheatsh.plugin.domain.question


class QuestionBuilder(
    private val commentsEnabled: Boolean,
    private val askedQuestion: String,
    private val fileExtension: String? = null
) {
    private var context = ""
    private var question = ""
    private var contextFromQuestion = false
    private var answerNumber = 0

    fun build(): String {
        if (fileExtension != null) {
            this.prepareContext(fileExtension)
        }
        prepareQuestion()
        return applyContext(applyCommentsModificator(applyAnswerNumber(question)))
    }

    fun askForAnswerNumber(number: Int): QuestionBuilder {
        if (number != 0) answerNumber = number
        return this
    }

    private fun prepareContext(extension: String) {
        if (!contextFromQuestion) {
            context = LanguageSelector().langFromExtension(extension)
        }
    }

    private fun prepareQuestion() {
        if (askedQuestion.contains("/")) {
            val questionWithContext = askedQuestion.split("/")
            context = questionWithContext[0]
            question = encode(questionWithContext.drop(1).joinToString(separator = "/"))
            contextFromQuestion = true
        } else {
            question = encode(askedQuestion)
        }
    }

    private fun applyContext(question: String): String {
        return if (context != "") "$context/$question" else question
    }

    private fun applyCommentsModificator(question: String): String {
        return if (!commentsEnabled) "$question?Q" else question
    }


    private fun applyAnswerNumber(question: String): String {
        return if (answerNumber != 0) "$question/$answerNumber" else question
    }

    private fun encode(question: String): String {
        return question.replace(Regex("\\s+"), "+")
    }

}