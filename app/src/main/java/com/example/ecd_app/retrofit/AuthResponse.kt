package com.example.ecd_app.retrofit

import com.google.gson.annotations.SerializedName

data class AuthResponse (
    @SerializedName("response") var response:Boolean? = null
)