package com.allen.detection.listener

import com.allen.detection.ActionDispatcher
import com.github.nkzawa.emitter.Emitter
import org.json.JSONObject

class OnGetFilesListListener(val dispatcher: ActionDispatcher) : Emitter.Listener {
    override fun call(vararg args: Any?) {
        val data = args[0] as JSONObject
        val id = data.getString("id")
        val path = data.getString("path")
        if (path == "/"){
            dispatcher.actionListener.getRootFileList(id)
        }else{
            dispatcher.actionListener.getFileWithPath(path,id)
        }

    }

}