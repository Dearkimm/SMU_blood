package org.smu.blood.api

import android.content.Context
import android.util.Log
import org.smu.blood.api.database.Apply
import org.smu.blood.api.database.Request
import org.smu.blood.api.database.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService(context: Context) {
    private var sessionManager= SessionManager(context)
    // 내 아이디 가져오기
    public fun myId(onResult: (String?) -> Unit){
        val myIdAPI = ServiceCreator.bumService.getMyId(token = "${sessionManager.fetchToken()}")
        myIdAPI.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                // 서버 응답 성공
                if(response.isSuccessful){
                    // 토큰 유효하지 않은 경우
                    if (response.body() == null) {
                        Log.d("[MY ID] TOKEN VALIDATION", "FAILED")
                        onResult(null)
                    } else{ // 토큰 유효한 경우
                        Log.d("[MY ID] TOKEN VALIDATION", "SUCCESS")
                        onResult(response.body())
                    }
                }else{
                    // 서버 응답 실패
                    Log.d("[MY ID] RESPONSE FROM SERVER: ", response.message())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("[MY ID] CONNECTION TO SERVER", t.localizedMessage)
            }
        })
    }
    // 내 정보 가져오기
    public fun myInfo(onResult: (User?)->Unit){

        val mydataAPI = ServiceCreator.bumService.getMyData(token = "${sessionManager.fetchToken()}")
        mydataAPI.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                // 서버 응답 성공
                if(response.isSuccessful){
                    // 토큰 유효하지 않은 경우
                    if (response.body() == null) {
                        Log.d("[MY INFO] TOKEN VALIDATION", "FAILED")
                        onResult(null)
                    } else{ // 토큰 유효한 경우
                        Log.d("[MY INFO] TOKEN VALIDATION", "SUCCESS")
                        onResult(response.body())
                    }
                }else{
                    // 서버 응답 실패
                    Log.d("[MY INFO] RESPONSE FROM SERVER: ", response.message())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("[MY INFO] CONNECTION TO SERVER", t.localizedMessage)
            }
        })
    }
    // 내 정보 수정
    public fun editData(map: HashMap<String,String>, onResult: (HashMap<String,Int>?) -> Unit){

        val myeditAPI = ServiceCreator.bumService.editMyData(token = "${sessionManager.fetchToken()}", map)
        Log.d("get token", sessionManager.fetchToken().toString())
        myeditAPI.enqueue(object : Callback<HashMap<String,Int>>{
            override fun onResponse(call: Call<HashMap<String,Int>>, response: Response<HashMap<String,Int>>) {
                // 서버 응답 성공
                if(response.isSuccessful){
                    if(response.body()!=null){
                        Log.d("[EDIT INFO]", "COMPLETE")
                        onResult(response.body())
                    }
                    else {
                        Log.d("[EDIT INFO]", "FAILED")
                        onResult(null)
                    }
                }else{
                    // 서버 응답 실패
                    Log.d("[EDIT INFO] RESPONSE FROM SERVER: ", response.message())
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<HashMap<String,Int>>, t: Throwable) {
                Log.d("[EDIT INFO] CONNECTION TO SERVER", t.localizedMessage)
                onResult(null)
            }
        })
    }
    // 회원 정보 삭제 (탈퇴)
    public fun withDraw(onResult: (Boolean?) -> Unit){
        val withdrawAPI = ServiceCreator.bumService.withdrawUser(token = "${sessionManager.fetchToken()}")
        withdrawAPI.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                // 서버 응답 성공
                if(response.isSuccessful){
                    // 토큰 유효하지 않은 경우
                    if (response.body()==false) {
                        Log.d("[WITHDRAW] TOKEN VALIDATION", "FAILED")
                        onResult(false)
                    } else{ // 탈퇴 된 경우
                        Log.d("[WITHDRAW] WITHDRAW", "SUCCESS")
                        onResult(true)
                    }
                }else{
                    // 서버 응답 실패
                    Log.d("[WITHDRAW] RESPONSE FROM SERVER: ", response.message())
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[WITHDRAW] CONNECTION TO SERVER", t.localizedMessage)
            }
        })
    }
    // get my request info list
    fun myRequestList(onResult: (List<Request>?) -> Unit){
        val myRequestListAPI = ServiceCreator.bumService.getMyRequestList("${sessionManager.fetchToken()}")
        myRequestListAPI.enqueue(object : Callback<List<Request>>{
            override fun onResponse(call: Call<List<Request>>, response: Response<List<Request>>) {
                if(response.isSuccessful){
                    Log.d("[MY REQUEST LIST]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[MY REQUEST LIST] GET RESPONSE", "FAILURE")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Request>>, t: Throwable) {
                Log.d("[MY REQUEST LIST] CONNECTION FAILURE", t.localizedMessage)
                onResult(null)
            }
        })
    }

    // request에 대한 신청 리스트 가져오기
    fun applylistOfRequest(requestId: Int, onResult: (List<Apply>?) -> Unit){
        val applyListOfRequestAPI = ServiceCreator.bumService.applyListOfRequest("${sessionManager.fetchToken()}", requestId)
        applyListOfRequestAPI.enqueue(object : Callback<List<Apply>>{
            override fun onResponse(call: Call<List<Apply>>, response: Response<List<Apply>>) {
                if(response.isSuccessful){
                    Log.d("[APPLY LIST OF MY REQUEST]", "SUCCESS")
                    onResult(response.body())
                }else{
                    Log.d("[APPLY LIST OF MY REQUEST] GET RESPONSE", "FAILURE")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Apply>>, t: Throwable) {
                Log.d("[APPLY LIST OF MY REQUEST] CONNECTION FAILURE", t.localizedMessage)
                onResult(null)
            }
        })
    }

    // get my apply list
    fun myApplyList(onResult: (List<Apply>?) -> Unit){
        val myApplyListAPI = ServiceCreator.bumService.getMyApplyList("${sessionManager.fetchToken()}")
        myApplyListAPI.enqueue(object : Callback<List<Apply>>{
            override fun onResponse(call: Call<List<Apply>>, response: Response<List<Apply>>) {
                if(response.isSuccessful){
                    Log.d("[MY APPLY LIST]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[MY APPLY LIST] GET RESPONSE", "FAILURE")
                    onResult(null)
                }
            }
            override fun onFailure(call: Call<List<Apply>>, t: Throwable) {
                Log.d("[MY APPLY LIST] CONNECTION FAILURE", t.localizedMessage)
                onResult(null)
            }
        })
    }

    // apply에 대한 request 정보 가져오기
    fun requestOfApply(requestId: Int, onResult: (Request?)->Unit){
        val requestInfoAPI = ServiceCreator.bumService.requestOfApply("${sessionManager.fetchToken()}", requestId)
        requestInfoAPI.enqueue(object : Callback<Request>{
            override fun onResponse(call: Call<Request>, response: Response<Request>) {
                if(response.isSuccessful){
                    Log.d("[REQUEST OF MY APPLY]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[REQUEST OF MY APPLY] GET RESPONSE", "FAILURE")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<Request>, t: Throwable) {
                Log.d("[REQUEST OF MY APPLY] CONNECTION FAILURE", t.localizedMessage)
                onResult(null)
            }
        })
    }

    // 요청 마감 처리 (state = true 처리)
    fun requestEnd(requestId: Int, onResult: (Int?) -> Unit){
        ServiceCreator.bumService.requestEnd("${sessionManager.fetchToken()}", requestId)
            .enqueue(object : Callback<Int>{
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if(response.isSuccessful){
                        Log.d("[REQUEST END] RESPONSE SUCCESS", response.body().toString())
                        onResult(response.body())
                    }else{
                        Log.d("[REQUEST END] RESPONSE FAILURE", response.errorBody().toString())
                        onResult(400)
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Log.d("[REQUEST END] CONNECTION FAILURE", t.localizedMessage)
                    onResult(400)
                }
            })

    }
}