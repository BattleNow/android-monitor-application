package com.allen.detection.http

import retrofit2.Retrofit


object HttpServer {
    var retrofit: Retrofit? = null
    fun getClient(): Api {
        retrofit ?: let {
            retrofit = Retrofit.Builder()
                    .baseUrl("http://149.28.202.19:3300")
                    .build()
        }
        return retrofit!!.create(Api::class.java)
    }
}