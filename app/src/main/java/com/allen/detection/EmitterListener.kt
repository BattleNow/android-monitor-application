package com.allen.detection

import com.github.nkzawa.emitter.Emitter
import org.json.JSONObject



class EmitterListener : Emitter.Listener {
    override fun call(vararg args: Any) {
        val data = args[0] as JSONObject
        
    }
}