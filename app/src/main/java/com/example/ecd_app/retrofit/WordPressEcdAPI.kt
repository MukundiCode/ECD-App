package com.example.ecd_app.retrofit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*
import io.reactivex.Observable
import retrofit2.http.Path

interface WordPressEcdAPI {

    //@GET("/top.json")
    //fun getTop(@Query("after") after: String, @Query("limit") limit: String): Call<PostJS>;
    @GET("/wp-json/ecd/v1/usersPosts/{Username}")
    fun getPosts(@Path("Username") Username: String): Observable<List<PostJS>>

    @GET("/wp-json/wp/v2/users/1")
    fun getUsers(): Observable<User>

    @GET("/wp-json/ecd/v1/userAuth/{Username}")
    fun authenticate(@Path("Username") Username: String): Observable<AuthResponse>
}