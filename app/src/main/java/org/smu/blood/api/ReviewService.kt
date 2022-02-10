package org.smu.blood.api

import android.content.Context
import android.util.Log
import org.smu.blood.api.database.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
// Review request to Server & get response from server
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
    fun reviewWrite(review: Review, onResult: (Review?) -> Unit){
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

    // 내가 쓴 후기글인지 확인
    fun checkReviewNickname( reviewNickname: String, onResult: (Boolean?) -> Unit){
        val checkNicknameAPI = ServiceCreator.bumService.checkNickname(token="${sessionManager.fetchToken()}", reviewNickname)
        checkNicknameAPI.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Log.d("[CHECK REVIEW NICKNAME]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[CHECK REVIEW NICKNAME]", "RESPONSE FAILURE")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[CHECK REVIEW NICKNAME] CONNECTION TO SERVER", t.localizedMessage)
                onResult(false)
            }
        })
    }

    // 내가 쓴 후기글 수정
    fun reviewEdit(editInfo: HashMap<String,String>, onResult: (Boolean?) -> Unit){
        val myEditAPI = ServiceCreator.bumService.reviewEdit(token="${sessionManager.fetchToken()}", editInfo)
        myEditAPI.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Log.d("[REVIEW EDIT]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[REVIEW EDIT]", "RESPONSE FAILURE")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[REVIEW EDIT] CONNECTION TO SERVER", t.localizedMessage)
                onResult(false)
            }
        })
    }

    // 내가 쓴 후기글 삭제
    fun reviewDelete(deleteInfo: HashMap<String,String>, onResult: (Boolean?) -> Unit){
        val myDeleteAPI = ServiceCreator.bumService.reviewDelete(token="${sessionManager.fetchToken()}", deleteInfo)
        myDeleteAPI.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Log.d("[REVIEW DELETE]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[REVIEW DELETE]", "RESPONSE FAILURE")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[REVIEW DELETE] CONNECTION TO SERVER", t.localizedMessage)
                onResult(false)
            }
        })
    }

    // get my review list
    fun getMyReviewList(nickname: String, onResult: (List<Review>?) -> Unit){
        val myReviewListAPI = ServiceCreator.bumService.getMyReviewList(nickname)
        myReviewListAPI.enqueue(object : Callback<List<Review>>{
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