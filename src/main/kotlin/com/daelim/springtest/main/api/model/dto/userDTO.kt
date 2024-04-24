package com.daelim.springtest.main.api.model.dto

data class userData (
    val userNum: Int = 0,
    val userName: String = "",
    val userId: String = "",
    val userPw: String = "",
    var login: String ?= "",
)
data class addUserData (
    val userNum: Int = 0,
    val userName: String = "",
    val userId: String = "",
    val userPw: String = "",
)

data class loginData(
    val userId: String = "",
    val userPw: String = "",
)

data class loginListData(
    val userValid: String =""
)