package org.smu.blood.api

import android.content.Context
import android.util.Log
import org.smu.blood.api.database.User
import org.smu.blood.ui.my.MyFragment
import org.smu.blood.util.enqueueUtil
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
    public fun editData(map: HashMap<String,String>, onResult: (Boolean?) -> Unit){

        val myeditAPI = ServiceCreator.bumService.editMyData(token = "${sessionManager.fetchToken()}", map)
        Log.d("get token", sessionManager.fetchToken().toString())
        myeditAPI.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                // 서버 응답 성공
                if(response.isSuccessful){
                    // 토큰 유효하지 않은 경우
                    if (response.body()==false) {
                        Log.d("[EDIT INFO] TOKEN VALIDATION", "FAILED")
                        onResult(false)
                    } else{ // 토큰 유효한 경우
                        Log.d("[EDIT INFO] TOKEN VALIDATION", "SUCCESS")
                        onResult(response.body())
                    }
                }else{
                    // 서버 응답 실패
                    Log.d("[EDIT INFO] RESPONSE FROM SERVER: ", response.message())
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[EDIT INFO] CONNECTION TO SERVER", t.localizedMessage)
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
}