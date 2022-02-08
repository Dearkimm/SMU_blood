package org.smu.blood.api

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

    // get user id
    @GET("myPage/myId")
    fun getMyId(@Header("token") token: String): Call<String>

    // get user info API
    @GET("myPage/info")
    fun getMyData(@Header("token") token: String): Call<User>

    // edit user info API
    @POST("myPage/edit")
    fun editMyData(@Header("token") token: String, @Body info: HashMap<String,String>): Call<Boolean>

    // withdraw API
    @GET("myPage/withdraw")
    fun withdrawUser(@Header("token") token: String): Call<Boolean>
}