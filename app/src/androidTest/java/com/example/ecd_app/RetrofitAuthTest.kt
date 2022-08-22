package com.example.ecd_app

import com.example.ecd_app.retrofit.RetrofitService
import com.example.ecd_app.retrofit.WordPressEcdAPI
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.Executors

class RetrofitAuthTest {
    private val mockWebServer = MockWebServer()
    private lateinit var client : OkHttpClient
    private lateinit var api: WordPressEcdAPI

    @Before
    fun setup(){
        mockWebServer.start(8000)
        client = OkHttpClient.Builder()
            .build()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(RetrofitService.ServiceBuilder.gson))
            .callbackExecutor(Executors.newSingleThreadExecutor())
            .client(client)
            .build()
            .create(WordPressEcdAPI::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testValidAuth(){
        val response: MockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody("{\"response\":true}")
        mockWebServer.enqueue(response)
        runBlocking {
            val actual =  api.authenticate("Test").blockingFirst()
            Assert.assertEquals(true,actual.response)
        }
    }

    @Test
    fun testInValidAuth(){
        val response: MockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody("{\"response\":false}")
        mockWebServer.enqueue(response)
        runBlocking {
            val actual =  api.authenticate("Tet").blockingFirst()
            Assert.assertEquals(false,actual.response)
        }
    }

}