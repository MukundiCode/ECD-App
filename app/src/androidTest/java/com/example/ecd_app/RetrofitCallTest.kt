package com.example.ecd_app

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ecd_app.retrofit.PostJS
import com.example.ecd_app.retrofit.RetrofitService
import com.example.ecd_app.retrofit.WordPressEcdAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class RetrofitCallTest {
    private val mockWebServer = MockWebServer()
    private lateinit var client : OkHttpClient
    private lateinit var api: WordPressEcdAPI

    @Before
    fun setup(){
        mockWebServer.start(8000)
        client = OkHttpClient.Builder()
//            .connectTimeout(1, TimeUnit.SECONDS)
//            .readTimeout(1, TimeUnit.SECONDS)
//            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("localhost/"))
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
    fun testRetrofitFetch() {
        val response: MockResponse = MockResponse()
            .setResponseCode(200)
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setBody("wp-response.json")
        mockWebServer.enqueue(response)
        runBlocking {
            val actual =  api.getPosts("Jonathan")
            val compositeDisposable = CompositeDisposable()
            compositeDisposable.add(actual
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({response -> onResponse(response)}, {t -> onFailure(t) }))
//            val expected = listOf(
//                movieDTO(
//                    id = "464052",
//                    overview = "Wonder Woman comes into...",
//                    title = "Wonder Woman 1984",
//                    voteAverage = 7.2,
//                    posterPath = "/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg"
//                )
//            ).toPaginatedResponse()
            System.out.println(actual)

            //assertEquals(expected, actual)
        }
    }

    fun onResponse(response: List<PostJS>){
        System.out.println(response)
    }
    fun onFailure(t: Throwable){
        System.out.println("Retrofit Failed: "+ t.stackTraceToString() )
    }
}
