package org.smu.blood.api

import org.smu.blood.api.database.*
import retrofit2.Call
import retrofit2.http.*

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

    // get list of reviews API
    @GET("review/list")
    fun getReviewList(): Call<List<Review>>

    // check if this review == my review
    @POST("review/checkReviewNickname")
    fun checkNickname(@Header("token") token: String, @Body nickname: String): Call<Boolean>

    // edit review API
    @POST("review/edit")
    fun reviewEdit(@Header("token") token: String, @Body editInfo: HashMap<String,String>): Call<Boolean>

    // delete review API
    @POST("review/delete")
    fun reviewDelete(@Header("token") token: String, @Body deleteInfo: HashMap<String,String>): Call<Boolean>

    // get my review list
    @GET("review/myList")
    fun getMyReviewList(@Header("nickname") nickname: String): Call<List<Review>>


    // write comment API
    @POST("review/addComment")
    fun writeComment(@Header("token") token: String, @Body reviewInfo: HashMap<String,String>): Call<Boolean>

    // get all comments of review
    @POST("review/commentList")
    fun getCommentList(@Body reviewInfo: HashMap<String,String>): Call<List<Comment>>

    // edit comment
    @POST("review/editComment")
    fun editComment(@Header("token") token: String, @Body editInfo: HashMap<String,String>): Call<Boolean>

    // delete comment
    @POST("review/deleteComment")
    fun deleteComment(@Header("token") token: String, @Body deleteInfo: HashMap<String, String>): Call<Boolean>

    // heart check event
    @POST("review/heart")
    fun checkHeart(@Header("token") token: String, @Body reviewInfo: HashMap<String, String>): Call<Boolean>

    // blood request in main page
    @POST("main/registerRequest")
    fun bloodRequest(@Header("token") token: String, @Body requestInfo: Request): Call<Boolean>

    // get list of blood donation request
    @GET("main/list")
    fun getRequestList(): Call<List<Request>>

    // register blood donation apply
    @POST("main/apply")
    fun bloodApply(@Header("token") token: String, @Body applyInfo: HashMap<String, String>): Call<Boolean>
}