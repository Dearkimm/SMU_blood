package org.smu.blood.api

import android.content.Context
import android.util.Log
import org.smu.blood.api.database.Comment
import org.smu.blood.api.database.Review
import org.smu.blood.api.database.ReviewLike
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
                        Log.d("[MY NICKNAME]", response.body().toString())
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
    fun reviewDelete(reviewId: Int, onResult: (Boolean?) -> Unit){
        val myDeleteAPI = ServiceCreator.bumService.reviewDelete("${sessionManager.fetchToken()}", reviewId)
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

    // delete my review (user auth)
    fun reviewDeleteAuth(reviewId: Int, onResult: (Boolean?) -> Unit){
        val myDeleteAPI = ServiceCreator.bumService.reviewDeleteAuth("${sessionManager.fetchToken()}", reviewId)
        myDeleteAPI.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Log.d("[CHECK AUTH BEFORE DELETE]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[CHECK AUTH BEFORE DELETE]", "RESPONSE FAILURE")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[CHECK AUTH BEFORE DELETE]", "CONNECTION TO SERVER: ${t.localizedMessage}")
                onResult(false)
            }
        })
    }

    // add comment
    fun commentWrite(info: HashMap<String,String>, onResult: (Boolean?) -> Unit){
        val addCommentAPI = ServiceCreator.bumService.writeComment("${sessionManager.fetchToken()}", info)
        addCommentAPI.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Log.d("[ADD COMMENT]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[ADD COMMENT]", "RESPONSE FAILURE")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[ADD COMMENT] CONNECTION TO SERVER", t.localizedMessage)
                onResult(false)
            }
        })
    }

    // get comment list of review
    fun commentList(reviewInfo: HashMap<String,String>, onResult: (List<Comment>?) -> Unit){
        val commentListAPI = ServiceCreator.bumService.getCommentList(reviewInfo)
        commentListAPI.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if(response.isSuccessful){
                    Log.d("[COMMENT LIST]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[COMMENT LIST]", "RESPONSE FAILURE")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d("[COMMENT LIST] CONNECTION TO SERVER", t.localizedMessage)
                onResult(null)
            }
        })
    }

    // edit comment
    fun commentEdit(editInfo: HashMap<String,String>, onResult: (Boolean?) -> Unit){
        val editCommentAPI = ServiceCreator.bumService.editComment("${sessionManager.fetchToken()}", editInfo)
        editCommentAPI.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Log.d("[EDIT COMMENT]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[EDIT COMMENT]", "RESPONSE FAILURE")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[EDIT COMMENT] CONNECTION TO SERVER", t.localizedMessage)
                onResult(false)
            }
        })
    }

    // delete comment
    fun commentDelete(deleteInfo: HashMap<String, String>, onResult: (Boolean?) -> Unit){
        val deleteCommentAPI = ServiceCreator.bumService.deleteComment("${sessionManager.fetchToken()}", deleteInfo)
        deleteCommentAPI.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Log.d("[DELETE COMMENT]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[DELETE COMMENT]", "RESPONSE FAILURE")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[DELETE COMMENT] CONNECTION TO SERVER", t.localizedMessage)
                onResult(false)
            }
        })
    }

    // heart check event
    fun heartCheck(reviewInfo: HashMap<String, String>, onResult: (Boolean?) -> Unit){
        val checkHeartAPI = ServiceCreator.bumService.checkHeart("${sessionManager.fetchToken()}", reviewInfo)
        checkHeartAPI.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Log.d("[HEART EVENT]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[HEART EVENT]", "RESPONSE FAILURE")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[HEART EVENT] CONNECTION TO SERVER", t.localizedMessage)
                onResult(false)
            }
        })
    }

    // get my heart state of review
    fun getMyHeartState(reviewId: Int, onResult: (ReviewLike?) -> Unit){
        ServiceCreator.bumService.getHeart("${sessionManager.fetchToken()}", reviewId)
            .enqueue(object : Callback<ReviewLike>{
                override fun onResponse(call: Call<ReviewLike>, response: Response<ReviewLike>) {
                    if(response.isSuccessful){
                        Log.d("[GET HEART STATE OF REVIEW]", response.body().toString())
                        onResult(response.body())
                    }else{
                        Log.d("[GET HEART STATE OF REVIEW]", "RESPONSE FAILURE")
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<ReviewLike>, t: Throwable) {
                    Log.d("[GET HEART STATE OF REVIEW] CONNECTION TO SERVER", t.localizedMessage)
                    onResult(null)
                }
            })
    }
}