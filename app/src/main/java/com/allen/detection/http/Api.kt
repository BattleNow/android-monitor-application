package com.allen.detection.http

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface Api {
    @Multipart
    @POST("/phone")
    fun upload(
            @Part file: MultipartBody.Part
    ): Call<ResponseBody>
}