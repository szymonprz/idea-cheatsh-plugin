package pl.szymonprz.cheatsh.plugin.answerclient

import com.intellij.openapi.application.ApplicationInfo
import com.mashape.unirest.http.Unirest

class CheatshAnswerLoader : AnswerLoader {
    override fun answerFor(question: String): String {
        val applicationInfo = ApplicationInfo.getInstance()
        val response = Unirest.get("http://cht.sh/$question?T")
            .header("User-Agent", "curl/${applicationInfo.versionName} ${applicationInfo.fullVersion}")
            .asString()
        return response.body
    }
}