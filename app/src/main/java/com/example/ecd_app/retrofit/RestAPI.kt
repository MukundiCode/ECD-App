package com.example.ecd_app.retrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RestAPI {
    private val wpEcdAPI: WordPressEcdAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ecdportal.azurewebsites.net/wp-json/wp/v2/ecd_content/100")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        wpEcdAPI = retrofit.create(WordPressEcdAPI::class.java)
    }

//    fun getPost(after: String, limit: String): Call<PostJS> {
//        return wpEcdAPI.getTop(after, limit)
//    }

}