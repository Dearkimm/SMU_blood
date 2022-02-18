package org.smu.blood.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    var bumService: BlringService

    // 요청 보내는 API 서버 (url은 /로 끝나야 함)
    private const val BASE_URL = "http://192.168.35.229" + ":8090/blood/"

    init{
        var gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        bumService = retrofit.create(BlringService::class.java)

    }
}