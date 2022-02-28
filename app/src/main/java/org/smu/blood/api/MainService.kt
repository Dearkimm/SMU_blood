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
    fun registerApply(applyInfo: HashMap<String, String>, onResult: (Int?) -> Unit){
        val bloodApplyAPI = ServiceCreator.bumService.bloodApply("${sessionManager.fetchToken()}", applyInfo)
        bloodApplyAPI.enqueue(object : Callback<Int>{
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if(response.isSuccessful){
                    Log.d("[REGISTER BLOOD APPLY]", response.body().toString())
                    onResult(response.body())
                }else{
                    Log.d("[REGISTER BLOOD APPLY] GET RESPONSE", "FAILURE")
                    onResult(201)
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d("[REGISTER BLOOD APPLY]", t.localizedMessage)
                onResult(201)
            }
        })
    }
}