package pl.szymonprz.cheatsh.plugin.answerclient

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import pl.szymonprz.cheatsh.plugin.model.Storage
import pl.szymonprz.cheatsh.plugin.question.QuestionBuilder

class ExecuteActionOnAnswer(private val project: Project, private val currentFile: VirtualFile?) {

    fun apply(question: String, action: (answer: String) -> Unit) {
        val storage = ServiceManager.getService(project, Storage::class.java)
        val buildQuestion = QuestionBuilder(storage, question, currentFile).build()
        CheatshAnswerLoader().answerFor(buildQuestion) { answerFromCheatSh ->
            action(answerFromCheatSh)
        }
    }
}