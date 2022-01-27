package org.smu.blood.api

import org.smu.blood.api.database.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BlringService {
    // create User
    @POST("signUp")
    fun createUser(@Body user: User): Call<HashMap<String, Int>>
    @POST("signIn")
    fun loginUser(@Body id: String, @Body password: String): Call<User>
}