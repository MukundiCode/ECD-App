/**
 * @author Tinashe Mukundi Chitamba
 * This interface is used to make API calls to the web portal
 */

package com.example.ecd_app.retrofit
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Path

interface WordPressEcdAPI {

    /**
     * Gets the posts from web portal by username
     */
    @GET("/wp-json/ecd/v1/usersPosts/{Username}")
    fun getPosts(@Path("Username") Username: String): Observable<List<PostJS>>

    /**
     * Gets the list of users from web portal
     */
    @GET("/wp-json/wp/v2/users/1")
    fun getUsers(): Observable<User>

    /**
     * Authenticates this user by username
     */
    @GET("/wp-json/ecd/v1/userAuth/{Username}")
    fun authenticate(@Path("Username") Username: String): Observable<AuthResponse>
}