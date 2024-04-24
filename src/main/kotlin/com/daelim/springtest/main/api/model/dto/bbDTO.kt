package com.daelim.springtest.main.api.model.dto

data class bbDTO (          //기존 nullable 제거
    val num: Int = 0,
    val title: String = "",
    val detail: String = "",
    val writer: String = "",
    val time: String = "",
)


data class bbDtoRequest(        //기존 nullable 제거
    val num: Int,
    val title: String,
    val detail: String,
    val writer: String,
)


data class bbDtoReadable(
    val num: Int,
    )
