package pl.szymonprz.cheatsh.plugin.answerclient

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.async.Callback
import com.mashape.unirest.http.exceptions.UnirestException

class InvokeCallbackOnComplete<T>(private val callback: (result: T) -> Unit) : Callback<T> {
    override fun cancelled() {
    }

    override fun completed(response: HttpResponse<T>?) {
        if(response!=null){
            callback(response.body)
        }
    }

    override fun failed(e: UnirestException?) {
    }
}