package pl.szymonprz.cheatsh.plugin.answerclient

import com.intellij.openapi.application.ApplicationInfo
import com.mashape.unirest.http.Unirest

class CheatshAnswerLoader : AnswerLoader {
    override fun answerFor(question: String, callback: (answer: String) -> Unit) {
        val applicationInfo = ApplicationInfo.getInstance()
        Unirest.get("http://cht.sh/$question?T")
            .header("User-Agent", "curl/${applicationInfo.versionName} ${applicationInfo.fullVersion}")
            .asStringAsync(InvokeCallbackOnComplete<String>(callback))
    }
}