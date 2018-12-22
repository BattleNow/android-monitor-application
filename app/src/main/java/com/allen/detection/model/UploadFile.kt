package com.allen.detection.model

data class UploadFileEvent(
        val directory: List<UploadFile>,
        val id: String
)

data class UploadFile(
        val name: String,
        val type: String,
        val size: Float,
        val date: Long
)

