package org.smu.blood.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var intent: Intent
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        var sessionManager = SessionManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
            var token = sessionManager.fetchToken()
            if(token!=null){
                // 토큰 유효성 검증
                checkTokenValid("$token"){
                    if(it==true) { // 토큰 유효한 경우
                        Log.d("[CHECK TOKEN VALID]", it.toString())
                        finish()
                        val intent1 = Intent(this, NavigationActivity::class.java)
                        startActivity(intent1)
                    } else { // 토큰 유효하지 않은 경우
                        // 토큰 삭제
                        sessionManager.removeToken()
                        finish()
                        val intent2 = Intent(this, LoginActivity::class.java)
                        startActivity(intent2)
                    }
                }
            } else {
                finish()
                val intent3 = Intent(this, LoginActivity::class.java)
                startActivity(intent3)
            }
            //네비게이션 액티비티
            //startActivity(intent)
        }, 1000)
    }
    private fun checkTokenValid(token: String, onResult: (Boolean?) -> Unit){
        Log.d("[CHECK TOKEN VALID]", "CHECK TOKEN VALIDATION")
        val tokenAPI = ServiceCreator.bumService.tokenValid(token = token)
        tokenAPI.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                // 서버 응답 성공
                if(response.isSuccessful){
                    // 응답 반환 (토큰 유효성 여부)
                    Log.d("[CHECK TOKEN VALID]", response.body().toString())
                    onResult(response.body())
                }else{
                    // 서버 응답 실패
                    Log.d("[CHECK TOKEN VALID]", response.message())
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