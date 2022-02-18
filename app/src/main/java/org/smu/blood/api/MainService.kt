package org.smu.blood.api

import android.content.Context
import android.util.Log
import org.smu.blood.api.database.Apply
import org.smu.blood.api.database.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainService(context: Context) {
    val sessionManager = SessionManager(context)

    // register blood donation request
    fun mainRequest(request: Request,onResult: (Boolean?)->Unit){
        val mainRequestAPI = ServiceCreator.bumService.bloodRequest("${sessionManager.fetchToken()}", request)
        mainRequestAPI.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Log.d("[REGISTER BLOOD REQUEST]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[REGISTER BLOOD REQUEST] GET RESPONSE", "FAILURE")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[REGISTER BLOOD REQUEST]", t.localizedMessage)
                onResult(false)
            }
        })
    }

    // get list of blood donation request
    fun mainList(onResult: (List<Request>?) -> Unit){
        val mainListAPI = ServiceCreator.bumService.getRequestList()
        mainListAPI.enqueue(object : Callback<List<Request>>{
            override fun onResponse(call: Call<List<Request>>, response: Response<List<Request>>) {
                if(response.isSuccessful){
                    Log.d("[BLOOD REQUEST LIST]", response.body().toString())
                    onResult(response.body())
                }
                Log.d("[BLOOD REQUEST LIST]", "RESPONSE FAILURE")
                onResult(null)
            }

            override fun onFailure(call: Call<List<Request>>, t: Throwable) {
                Log.d("[BLOOD REQUEST LIST]", t.localizedMessage)
                onResult(null)
            }
        })
    }

    // register blood donation apply
    fun registerApply(applyInfo: HashMap<String, String>, onResult: (Boolean?) -> Unit){
        val bloodApplyAPI = ServiceCreator.bumService.bloodApply("${sessionManager.fetchToken()}", applyInfo)
        bloodApplyAPI.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    Log.d("[REGISTER BLOOD APPLY]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[REGISTER BLOOD APPLY] GET RESPONSE", "FAILURE")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[REGISTER BLOOD APPLY]", t.localizedMessage)
                onResult(false)
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
        val applyListOfRequestAPI = ServiceCreator.bumService.applyListOfRequest(requestId)
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
        val requestInfoAPI = ServiceCreator.bumService.requestOfApply(requestId)
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
}