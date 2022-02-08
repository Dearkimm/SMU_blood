package org.smu.blood.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import org.smu.blood.R
import org.smu.blood.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var intent: Intent
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash)
        var sessionManager = SessionManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
            var token = sessionManager.fetchToken()
            if(token!=null){
                // 토큰 유효성 검증
                checkTokenValid(token){
                    // 토큰 유효한 경우
                    if(it==true) {
                        Log.d("[CHECK TOKEN VALID]", it.toString())
                        intent = Intent(this, NavigationActivity::class.java)
                    } else {
                        // 토큰 삭제
                        sessionManager.removeToken()
                        intent = Intent(this, LoginActivity::class.java)
                    }
                }
            } else { intent = Intent(this, LoginActivity::class.java) }
            startActivity(intent)
            finish()
        }, 1000)
    }
    private fun checkTokenValid(token: String, onResult: (Boolean?) -> Unit){
        var sessionManager = SessionManager(this)
        Log.d("[CHECK TOKEN VALID]", "CHECK TOKEN VALIDATION")
        val tokenAPI = ServiceCreator.bumService.tokenValid(token = "${sessionManager.fetchToken()}")
        tokenAPI.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                // 서버 응답 성공
                if(response.isSuccessful){
                    // 토큰 유효하지 않은 경우
                    if (response.body()==null) {
                        Log.d("[CHECK TOKEN VALID]", "FAILED")
                        onResult(false)
                    } else{ // 응답 반환 (토큰 유효성 여부)
                        Log.d("[CHECK TOKEN VALID]", response.body().toString())
                        onResult(response.body())
                    }
                }else{
                    // 서버 응답 실패
                    Log.d("[CHECK TOKEN VALID] RESPONSE FROM SERVER: ", response.message())
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("[TOKEN VALIDATION] CONNECTION TO SERVER", t.localizedMessage)
                onResult(false)
            }
        })
    }
}