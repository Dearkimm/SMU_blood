package org.smu.blood.api

import org.smu.blood.api.database.*
import retrofit2.Call
import retrofit2.http.*

interface BlringService {
    // create User API
    @POST("signUp")
    fun createUser(@Body user: User): Call<HashMap<String, Int>>

    // Login API
    @POST("signIn/general")
    fun loginUser(@Body info: HashMap<String,String>): Call<User>

    // token validation API
    @GET("signIn/tokenValid")
    fun tokenValid(@Header("token") token: String): Call<Boolean>

    // login (google) API
    @POST("signIn/google")
    fun gloginUser(@Body info: HashMap<String,String>): Call<User>

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

    // get my request info list
    @GET("myPage/myRequest/myRequestList")
    fun getMyRequestList(@Header("token") token: String): Call<List<Request>>

    // get apply list of my request
    @POST("myPage/myRequest/applyList")
    fun applyListOfRequest(@Header("token") token: String, @Body requestId: Int): Call<List<Apply>>

    // handling request end
    @POST("myPage/myRequest/end")
    fun requestEnd(@Header("token") token: String, @Body requestId: Int): Call<Int>

    // get my apply info list
    @GET("myPage/myApply/myApplyList")
    fun getMyApplyList(@Header("token") token: String): Call<List<Apply>>

    // get request of my apply
    @POST("myPage/myApply/request")
    fun requestOfApply(@Header("token") token: String, @Body requestId: Int): Call<Request>

    // get user nickname API
    @GET("review/user")
    fun getUser(@Header("token") token: String): Call<User>

    // save user writing API
    @POST("review/write")
    fun reviewWrite(@Header("token") token: String, @Body reviewInfo: Review): Call<Review>

    // get list of reviews API
    @GET("review/list")
    fun getReviewList(): Call<List<Review>>

    // edit review API
    @POST("review/edit")
    fun reviewEdit(@Header("token") token: String, @Body editInfo: HashMap<String,String>): Call<Boolean>

    // delete review API
    @POST("review/delete")
    fun reviewDelete(@Header("token") token: String, @Body reviewId: Int): Call<Boolean>

    // delete my review (check user auth)
    @POST("review/deleteAuth")
    fun reviewDeleteAuth(@Header("token") token: String, @Body reviewId: Int): Call<Boolean>

    // write comment API
    @POST("review/addComment")
    fun writeComment(@Header("token") token: String, @Body reviewInfo: HashMap<String,String>): Call<Boolean>

    // get all comments of review
    @POST("review/commentList")
    fun getCommentList(@Body reviewId: Int): Call<List<Comment>>

    // edit comment
    @POST("review/editComment")
    fun editComment(@Header("token") token: String, @Body editInfo: HashMap<String,String>): Call<Boolean>

    // delete comment
    @POST("review/deleteComment")
    fun deleteComment(@Header("token") token: String, @Body commentId: Int): Call<Boolean>

    // heart check event
    @POST("review/heart")
    fun checkHeart(@Header("token") token: String, @Body reviewInfo: HashMap<String, String>): Call<Boolean>

    @POST("review/getHeartState")
    fun getHeart(@Header("token") token: String, @Body reviewId: Int): Call<ReviewLike>

    // blood request in main page
    @POST("main/registerRequest")
    fun bloodRequest(@Header("token") token: String, @Body requestInfo: Request): Call<Boolean>

    // get list of blood donation request
    @GET("main/list")
    fun getRequestList(): Call<List<Request>>

    // register blood donation apply
    @POST("main/apply")
    fun bloodApply(@Header("token") token: String, @Body applyInfo: HashMap<String, String>): Call<Int>

    // request list order by endDate
    @GET("main/list/endDate")
    fun sortByDate(): Call<List<Request>>

    // request list order by applicantNum
    @GET("main/list/applicantNum")
    fun sortByApplicantNum(): Call<List<Request>>

    // check if user has notice
    @GET("main/checkNotification")
    fun checkNotification(@Header("token") token: String): Call<Boolean>

    // get all notice history
    @GET("notice/list")
    fun noticeList(@Header("token") token: String): Call<List<Notification>>

    // get request list of notice
    @GET("notice/requestlist")
    fun requestlistOfNotice(@Header("token") token: String): Call<List<Request>>

    // update notice state of notification
    @POST("notice/updateState")
    fun updateNotState(@Header("token") token: String, @Body noticeId: Int): Call<Boolean>

    // update delete state of notification
    @POST("notice/deleteState")
    fun setDeleteState(@Header("token") token: String, @Body noticeId: Int): Call<Boolean>

    // fetch fcm token
    @POST("notice/sendPush")
    fun sendPush(@Body requestId: Int): Call<String>

    // save FCM token
    @POST("fcm/saveToken")
    fun saveFCMToken(@Header("token") token: String, @Body fcmToken: String): Call<Int>

    // get request (for notification tab event)
    @POST("notice/getRequest")
    fun getRequest(@Body requestId: Int): Call<Request>
}