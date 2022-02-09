package org.smu.blood.api

import org.smu.blood.api.database.Review
import org.smu.blood.api.database.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BlringService {
    // create User API
    @POST("signUp")
    fun createUser(@Body user: User): Call<HashMap<String, Int>>

    // Login API
    @POST("signIn")
    fun loginUser(@Body info: HashMap<String,String>): Call<User>

    // token validation API
    @GET("tokenValid")
    fun tokenValid(@Header("token") token: String): Call<Boolean>

    // get user id API
    @GET("myPage/myId")
    fun getMyId(@Header("token") token: String): Call<String>

    // get user info API
    @GET("myPage/info")
    fun getMyData(@Header("token") token: String): Call<User>

    // edit user info API
    @POST("myPage/edit")
    fun editMyData(@Header("token") token: String, @Body info: HashMap<String,String>): Call<HashMap<String,Int>>

    // withdraw API
    @GET("myPage/withdraw")
    fun withdrawUser(@Header("token") token: String): Call<Boolean>

    // get user nickname API
    @GET("review/myNickname")
    fun getMyNickname(@Header("token") token: String): Call<String>

    // save user writing API
    @POST("review/write")
    fun reviewWrite(@Header("token") token: String, @Body reviewInfo: Review): Call<Review>

    // get list of reviews
    @GET("review/list")
    fun getReviewList(): Call<List<Review>>
}