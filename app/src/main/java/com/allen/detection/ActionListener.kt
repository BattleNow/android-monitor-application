package com.allen.detection

interface ActionListener {
    fun getFileWithPath(path: String, id: String)
    fun getRootFileList(id: String)
    fun downloadFile(url: String, saveTo: String, id: String)
    fun onConnected(id: String)
    fun uploadFile(id: String, path: String)
}