package org.smu.blood.api

import android.content.Context
import android.util.Log
import org.smu.blood.api.database.Notification
import org.smu.blood.api.database.Request
import org.smu.blood.ui.main.MainFragment.Companion.request
import org.smu.blood.ui.my.MyRequestFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeService(context: Context) {
    val sessionManager = SessionManager(context)

    fun getNoticeList(onResult: (List<Notification>?)->Unit){
        ServiceCreator.bumService.noticeList("${sessionManager.fetchToken()}")
            .enqueue(object : Callback<List<Notification>>{
                override fun onResponse(call: Call<List<Notification>>, response: Response<List<Notification>>) {
                    if(response.isSuccessful){
                        Log.d("[NOTICE LIST]", "success:  ${response.body()}")
                        onResult(response.body())
                    }else{
                        Log.d("[NOTICE LIST]", "failed: ${response.errorBody()}")
                        onResult(null)
                    }
                }
                override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                    Log.d("[NOTICE LIST]", t.localizedMessage)
                    onResult(null)
                }
            })
    }

    fun getRequestList(onResult: (List<Request>?)->Unit){
        ServiceCreator.bumService.requestlistOfNotice("${sessionManager.fetchToken()}")
            .enqueue(object : Callback<List<Request>> {
                override fun onResponse(call: Call<List<Request>>, response: Response<List<Request>>) {
                    if(response.isSuccessful){
                        Log.d("[REQUEST LIST OF NOTICE]", "${response.body()}")
                        onResult(response.body())
                    }
                    else{ // response error
                        Log.d("[REQUEST LIST OF NOTICE]", "${response.errorBody()}")
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<List<Request>>, t: Throwable) {
                    Log.d("[REQUEST LIST OF NOTICE]", t.localizedMessage)
                    onResult(null)
                }
            })
    }

    fun updateState(noticeId: Int, onResult: (Boolean?)->Unit){
        ServiceCreator.bumService.updateNotState("${sessionManager.fetchToken()}", noticeId)
            .enqueue(object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        Log.d("[UPDATE NOTICE STATE]", response.body().toString())
                        onResult(response.body())
                    }else{
                        Log.d("[UPDATE NOTICE STATE]", response.errorBody().toString())
                        onResult(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("[UPDATE NOTICE STATE]", t.localizedMessage)
                    onResult(false)
                }
            })
    }

    fun setDeleteState(noticeId: Int, onResult: (Boolean?)->Unit){
        ServiceCreator.bumService.setDeleteState("${sessionManager.fetchToken()}", noticeId)
            .enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        Log.d("[SET DELETE STATE]", "${response.body()}")
                        onResult(response.body())
                    }else{
                        Log.d("[SET DELETE STATE]", "${response.errorBody()}")
                        onResult(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("[SET DELETE STATE]", t.localizedMessage)
                    onResult(false)
                }
            })
    }

    fun sendPushFromServer(requestId: Int, onResult: (String?) -> Unit){
        ServiceCreator.bumService.sendPush(requestId)
            .enqueue(object : Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){
                        Log.d("[SEND PUSH FROM SERVER]", response.body().toString())
                        onResult(response.body())
                    }else{
                        Log.d("[SEND PUSH FROM SERVER]", response.errorBody().toString())
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("[SEND PUSH FROM SERVER]", t.localizedMessage)
                    onResult(null)
                }
            })

    }

    // get request (for notice tab event)
    fun getRequest(requestId: Int, onResult: (Request?)->Unit){
        ServiceCreator.bumService.getRequest(requestId = requestId)
            .enqueue(object : Callback<Request>{
                override fun onResponse(call: Call<Request>, response: Response<Request>) {
                    if(response.isSuccessful){
                        Log.d("[NOTICE]", "response success: ${response.body()}")
                        onResult(response.body())
                    }else{
                        Log.d("[NOTICE]", "response failed: ${response.errorBody()}")
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<Request>, t: Throwable) {
                    Log.d("[NOTICE]", "get request failed: ${t.localizedMessage}")
                    onResult(null)
                }
            })
    }

    fun requestCardContent(context: Context, requestId: Int){
        // request 가져오기
        getRequest(requestId){ result ->
            if(result != null){
                Log.d("[NOTICE]", "get request success: $result")
                MyRequestFragment.myRequest = result
            }
        }
        // get apply list of my request
        MyPageService(context).applylistOfRequest(requestId){
            if(it != null){
                Log.d("[NOTICE]", "GET APPLY LIST OF MY REQUEST")
                // apply 리스트에 넣기
                MyRequestFragment.applyList = it

                for(apply in MyRequestFragment.applyList) Log.d("[NOTICE]", "$apply")
            }
        }
    }
}