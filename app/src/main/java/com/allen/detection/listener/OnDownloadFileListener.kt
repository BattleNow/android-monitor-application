package com.allen.detection.listener

import com.allen.detection.ActionDispatcher
import com.github.nkzawa.emitter.Emitter
import org.json.JSONObject

class OnDownloadFileListener(val dispatcher: ActionDispatcher) : Emitter.Listener {
    override fun call(vararg args: Any?) {
        val data = args[0] as JSONObject
        val from = data.getString("from")
        val to = data.getString("to")
        val id = data.getString("id")
        dispatcher.actionListener.downloadFile(from, to,id)
    }
}