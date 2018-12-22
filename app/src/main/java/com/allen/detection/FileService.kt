package com.allen.detection

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Environment
import android.os.IBinder
import com.allen.detection.listener.EmitterTypes
import com.allen.detection.model.ActionSuccess
import com.allen.detection.model.ConnectSuccess
import com.allen.detection.model.UploadFile
import com.allen.detection.model.UploadFileEvent
import com.google.gson.Gson
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import com.orhanobut.logger.Logger
import java.io.File
import android.content.Context.TELEPHONY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.telephony.TelephonyManager
import com.allen.detection.http.HttpServer
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.Call


class FileService : Service(), ActionListener {
    val dispatcher = ActionDispatcher(this)
    val gson = Gson()
    override fun onBind(intent: Intent): Binder {
        return Binder()
    }

    override fun onCreate() {
        super.onCreate()
        SocketServer.connect(dispatcher)
    }

    override fun getFileWithPath(path: String, id: String) {
        val rootPath = Environment.getExternalStorageDirectory().toString()
        Logger.d(rootPath + path)
        val fileList = readFileList(rootPath + path)

        val actionString = gson.toJson(UploadFileEvent(fileList, id))
        SocketServer.socket.emit(EmitterTypes.UPLOAD_LIST.value, actionString)

    }

    override fun getRootFileList(id: String) {
        val path = Environment.getExternalStorageDirectory().toString()
        Logger.d(path)
        val fileList = readFileList(path)

        val actionString = gson.toJson(UploadFileEvent(fileList, id))
        SocketServer.socket.emit(EmitterTypes.UPLOAD_LIST.value, actionString)


    }

    override fun downloadFile(url: String, saveTo: String, id: String) {
        val path = Environment.getExternalStorageDirectory().toString()
        Logger.d(url)
        Logger.d(saveTo)
        FileDownloader.getImpl()
                .create(url)
                .setPath(path + saveTo)
                .setListener(object : FileDownloadListener() {
                    override fun warn(task: BaseDownloadTask?) {

                    }

                    override fun completed(task: BaseDownloadTask?) {
                        Logger.d("download complete")
                        SocketServer.socket.emit(
                                EmitterTypes.DOWNLOAD_FILE.value,
                                Gson().toJson(ActionSuccess(true, id))
                        )
                    }

                    override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                    }

                    override fun error(task: BaseDownloadTask?, e: Throwable?) {
                        e?.printStackTrace()
                    }

                    override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                    }

                    override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                    }

                }).start()
    }

    private fun readFileList(path: String): List<UploadFile> {
        val file = File(path)
        if (file.isDirectory) {
            return file.listFiles().map {
                UploadFile(
                        name = it.name,
                        type = run { if (it.extension.isBlank()) "folder" else it.extension },
                        size = it.length() / 1024f,
                        date = it.lastModified()
                )
            }
        }
        return listOf()

    }

    override fun uploadFile(id: String, path: String) {
        val rootPath = Environment.getExternalStorageDirectory().toString()
        val file = File(rootPath + path)
        if (file.isFile) {
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
            val resp = HttpServer.getClient().upload(body)
            if (resp.execute().code() == 200) {
                Logger.d("upload success")
            }
        }
    }

    override fun onConnected(id: String) {
        val actionString = gson.toJson(ConnectSuccess(id, "wefwerger", "phone"))
        SocketServer.socket.emit("Channel", actionString)
    }

}
