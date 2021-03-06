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
import org.smu.blood.ui.LoginActivity.Companion.mGoogleSignInClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var intent: Intent
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        val sessionManager = SessionManager(this)

        MessagingService().getToken()

        Handler(Looper.getMainLooper()).postDelayed({
            val token = sessionManager.fetchToken()

            if(token!=null){
                // 토큰 유효성 검증
                checkTokenValid("$token"){
                    if(it==true) { // 토큰 유효한 경우

                        val task = mGoogleSignInClient?.silentSignIn()

                        if(task != null){
                            if(task.isSuccessful){
                                val account = task.result
                                Log.d("[GOOGLE LOGIN1]", account.toString())
                                if(account.isExpired){
                                    Log.d("[GOOGLE LOGIN1]", "GOOGLE ID TOKEN EXPIRED")
                                    goLogin()
                                }else{
                                    Log.d("[GOOGLE LOGIN1]", "id: ${account.id}, displayname: ${account.displayName}, givenname: ${account.givenName}, familyname: ${account.familyName}, email: ${account.email}")
                                    goMain()
                                }
                            }
                        }else{
                            Log.d("[CHECK TOKEN VALID]", it.toString())
                            goMain()
                        }
                    } else { // 토큰 유효하지 않은 경우
                        // 토큰 삭제
                        sessionManager.removeToken()
                        goLogin()
                    }
                }
            } else {
                Log.d("[SPLASH]", "NO JWT TOKEN")
                goLogin()
            }
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

    private fun goLogin(){
        finish()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun goMain(){

        // update fcm token
        LoginActivity().saveFCMToken { result ->
            if(result == 200) Log.d("[SAVE FCM TOKEN]", "success")
            else Log.d("[SAVE FCM TOKEN]", "failed")
        }
        finish()
        val intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)
    }
}