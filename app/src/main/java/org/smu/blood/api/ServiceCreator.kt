package org.smu.blood.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
//import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ServiceCreator {
    var bumService: BlringService

    // 요청 보내는 API 서버 (url은 /로 끝나야 함)
    private const val BASE_URL = "http://192.168.35.140" + ":8090/blood/"

    init{
        var gson = GsonBuilder()
            .setLenient()
            .create()

    /*
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()


     */
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            //.client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        bumService = retrofit.create(BlringService::class.java)

    }
}