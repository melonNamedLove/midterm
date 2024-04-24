package com.daelim.springtest.main.api.model.dto


data class userData(
    val fullName:String = "",
    val email: String = "",
    val password: String = ""
)
data class userLoginData (
    val email: String = "",
    val password: String = ""
)