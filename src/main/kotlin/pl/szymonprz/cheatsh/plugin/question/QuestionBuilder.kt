package pl.szymonprz.cheatsh.plugin.question

import com.intellij.openapi.vfs.VirtualFile
import pl.szymonprz.cheatsh.plugin.model.Storage


class QuestionBuilder(private val storage: Storage, private val askedQuestion: String, private val editingFile: VirtualFile? = null) {
    private var context = ""
    private var question = ""
    private var contextFromQuestion = false

    fun build(): String {
        if (editingFile != null) {
            this.prepareContext(editingFile.extension ?: "")
        }
        prepareQuestion()
        return applyContext(applyCommentsModificator(question))
    }

    private fun prepareContext(extension: String) {
        if (!contextFromQuestion){
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
        return if (!storage.commentsEnabled) "$question?Q" else question
    }

    private fun encode(question: String): String {
        return question.replace(Regex("\\s+"), "+")
    }

}