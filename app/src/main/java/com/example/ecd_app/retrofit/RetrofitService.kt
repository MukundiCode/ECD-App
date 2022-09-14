/**
 * @author Tinashe Mukundi CHitamba
 * This class initializes the retrofit service using the provided url.
 * NOTE: The URL can't be accessed from strings.xml
 */

package com.example.ecd_app.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class RetrofitService
{
    object ServiceBuilder {
        var gson = GsonBuilder()
            .setLenient()
            .create()

        private val client = OkHttpClient
            .Builder()
            .connectTimeout(5,TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl("https://youandyourbaby.bhabhisana.org.za/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .callbackExecutor(Executors.newSingleThreadExecutor())
            .client(client)
            .build()
            .create(WordPressEcdAPI::class.java)

        fun buildService(): WordPressEcdAPI {
            return retrofit
        }
    }

}