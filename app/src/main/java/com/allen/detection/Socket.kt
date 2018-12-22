package com.allen.detection

import com.allen.detection.listener.*
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.orhanobut.logger.Logger


object SocketServer {
    lateinit var socket: Socket
    fun connect(dispatcher: ActionDispatcher) {
        try {
            socket = IO.socket("http://149.28.202.19:3030")
            Logger.d("开始连接...")
            socket.on(EventTypes.UPLOAD_LIST.value, OnGetFilesListListener(dispatcher))
            socket.on(EventTypes.DOWNLOAD_FILE.value, OnDownloadFileListener(dispatcher))
            socket.on(EventTypes.OBTAIN.value, OnObtainFile(dispatcher))
            socket.on("Channel", OnConnectedListener(dispatcher))
            socket.connect()
        } catch (t: Throwable) {
            Logger.e("connect failed!", t)
        }
    }
}