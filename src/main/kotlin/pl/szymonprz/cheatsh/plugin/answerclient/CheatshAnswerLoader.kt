package pl.szymonprz.cheatsh.plugin.answerclient

import com.mashape.unirest.http.Unirest

class CheatshAnswerLoader : AnswerLoader {
    override fun answerFor(question: String, callback: (answer: String) -> Unit) {
        Unirest.get("http://cht.sh/$question?T").header("User-Agent", "curl")
            .asStringAsync(InvokeCallbackOnComplete<String>(callback))
    }
}