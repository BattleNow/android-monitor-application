package com.allen.detection.listener

import com.allen.detection.ActionDispatcher
import com.github.nkzawa.emitter.Emitter
import com.orhanobut.logger.Logger
import org.json.JSONObject

class OnConnectedListener(val dispatcher: ActionDispatcher) : Emitter.Listener {
    override fun call(vararg args: Any?) {
        Logger.d(args[0])
        val data = args[0] as JSONObject
        val id = data.getString("id")
        dispatcher.actionListener.onConnected(id)
        Logger.d("Connect succeed with id $id")
    }

}