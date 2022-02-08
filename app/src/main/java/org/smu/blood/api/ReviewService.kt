package org.smu.blood.api

import android.content.Context
import android.util.Log
import org.smu.blood.api.database.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewService(context: Context) {
    private var sessionManager= SessionManager(context)
    // 닉네임 가져오기
    fun myNickname(onResult: (String?)->Unit){
        val myNicknameAPI = ServiceCreator.bumService.getMyNickname(token = "${sessionManager.fetchToken()}")
        myNicknameAPI.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                // 서버 응답 성공
                if(response.isSuccessful){
                    // 토큰 유효하지 않은 경우
                    if (response.body() == null) {
                        Log.d("[MY NICKNAME] TOKEN VALIDATION", "FAILED")
                        onResult(null)
                    } else{ // 토큰 유효, 응답 성공
                        Log.d("[MY NICKNAME] TOKEN VALIDATION", "SUCCESS")
                        onResult(response.body())
                    }
                }else{
                    // 서버 응답 실패
                    Log.d("[MY NICKNAME] RESPONSE FROM SERVER", response.message())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("[MY NICKNAME] CONNECTION TO SERVER", t.localizedMessage)
            }
        })
    }
    // 글쓰기
    fun myWrite(review: Review, onResult: (Review?) -> Unit){
        val myWriteAPI = ServiceCreator.bumService.reviewWrite(token="${sessionManager.fetchToken()}", review)
        myWriteAPI.enqueue(object : Callback<Review>{
            override fun onResponse(call: Call<Review>, response: Response<Review>) {
                // 서버 응답 성공
                if(response.isSuccessful){
                    // 토큰 유효하지 않은 경우
                    if (response.body() == null) {
                        Log.d("[MY WRITE] TOKEN VALIDATION", "FAILED")
                        onResult(null)
                    } else{ // 토큰 유효, 응답 성공
                        Log.d("[MY WRITE] TOKEN VALIDATION", response.message())
                        onResult(response.body())
                    }
                }else{
                    // 서버 응답 실패
                    Log.d("[MY WRITE] RESPONSE FROM SERVER", response.message())
                }

            }
            override fun onFailure(call: Call<Review>, t: Throwable) {
                Log.d("[MY WRITE] CONNECTION TO SERVER", t.localizedMessage)
            }
        })
    }
    // 전체 후기 가져오기
    fun reviewList(onResult: (List<Review>?) -> Unit){
        val reviewListAPI = ServiceCreator.bumService.getReviewList()
        reviewListAPI.enqueue(object : Callback<List<Review>>{
            override fun onResponse(call: Call<List<Review>>, response: Response<List<Review>>) {
                // 서버 응답 성공
                if(response.isSuccessful){
                    // 토큰 유효하지 않은 경우
                    if (response.body() == null) {
                        Log.d("[REVIEW LIST1]", "FAILED")
                        onResult(null)
                    } else{ // 토큰 유효, 응답 성공
                        Log.d("[REVIEW LIST1]", "SUCCESS")
                        for(review: Review in response.body()!!) Log.d("[REVIEW LIST1]", review.toString())
                        onResult(response.body())
                    }
                }else{
                    // 서버 응답 실패
                    Log.d("[REVIEW LIST1] RESPONSE FROM SERVER", response.message())
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Review>>, t: Throwable) {
                Log.d("[REVIEW LIST1] CONNECTION TO SERVER", t.localizedMessage)
                onResult(null)
            }
        })
    }
}