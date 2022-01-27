package org.smu.blood.api

import org.smu.blood.api.database.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BlringService {
    // login
    @POST("signUp")
    fun getSignUpResponse(@Body user: User): Call<String>
}