package org.smu.blood.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.smu.blood.api.ServiceCreator
import org.smu.blood.api.SessionManager
import org.smu.blood.api.database.User
import org.smu.blood.databinding.ActivityLoginBinding
import org.smu.blood.util.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    var backKeyPressedTime : Long = 0
    private lateinit var binding: ActivityLoginBinding
    lateinit var token : String


    override fun onBackPressed(){
        if (System.currentTimeMillis()> backKeyPressedTime + 2500){
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(applicationContext, "한 번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT).show()
            return
        }
        else {finishAffinity()}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editId = binding.letId
        val exId = binding.idType

        editId.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if(hasFocus){exId.visibility = View.VISIBLE} else{exId.visibility = View.INVISIBLE}
        }
        configureNavigation()
    }

    private fun configureNavigation() {
        var autoLogin=false

        // 로그인 유지 체크
        binding.ltvCb.setOnCheckedChangeListener { _, isChecked ->
            autoLogin = isChecked
        }
        // 로그인 버튼 클릭 시
        binding.btnLog.setOnClickListener {
            if (binding.letId.text.isNotBlank() && binding.letPwd.text.isNotBlank()) {
                val loginInfo = HashMap<String,String>()
                loginInfo["id"] = binding.letId.text.toString()
                loginInfo["password"] = binding.letPwd.text.toString()
                loginInfo["AutoLogin"] = autoLogin.toString()
                signIn(loginInfo){
                    if(it != null){

                        Log.d("LOGIN USER", "user: $it, token: $token")
                        Toast.makeText(baseContext, "로그인 성공", Toast.LENGTH_SHORT).show()

                        // 사용자 토큰 저장
                        SessionManager(this).saveToken(token)

                        navigateHome(it)
                    }else{
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                shortToast("빈 칸이 있습니다")
            }
        }

        // 회원가입 버튼 클릭 시
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, 111)
        }

        // google 로그인 버튼 클릭 시
        binding.signInButton.setOnClickListener {
            googleLogin()
        }
    }
    private fun signIn(userInfo: HashMap<String,String>, onResult: (User?)->Unit){
        val apiService = ServiceCreator.bumService.loginUser(userInfo)
        apiService.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                // 서버 응답 성공
                if(response.isSuccessful){
                    // 해당 사용자가 없는 경우
                    if (response.body() == null) {
                        Log.d("LOGIN", "FAILED")
                        onResult(null)
                    } else{ // 사용자 있는 경우
                        Log.d("LOGIN", "SUCCESS")
                        token = response.headers()["jwt-token"].toString()
                        onResult(response.body())
                    }
                }else{
                    // 서버 응답 오류
                    Log.d("RESPONSE FROM SERVER: ", "FAILURE")
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                // 서버 연결 오류
                Log.d("CONNECTION TO SERVER FAILURE: ", t.localizedMessage)
            }
        })
    }

    private fun navigateHome(user: User?) {
        if( user!= null){

            // FCM token 저장
            saveFCMToken{ result ->
                if(result == 200) Log.d("[SAVE FCM TOKEN]", "success")
                else Log.d("[SAVE FCM TOKEN]", "failed")
            }

            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    private fun googleLogin(){
        // configure google sign in
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) // request user id, profile info
            .requestEmail() // request user mail
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // 구글 통해 앱 로그인 여부 확인
        mGoogleSignInClient!!.silentSignIn().addOnCompleteListener { task->
            if(task.isSuccessful){
                val account = task.result
                afterGoogleLogin(account)
            }else{
                Log.d("[GOOGLE LOGIN2]", "login google")
                val signInIntent = mGoogleSignInClient!!.signInIntent
                startActivityForResult(signInIntent, 9001)
            }
        }
    }

    private fun gCreateToken(loginInfo: HashMap<String,String>, onResult: (User?) -> Unit){
        ServiceCreator.bumService.gloginUser(loginInfo).enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    Log.d("[GOOGLE LOGIN]", response.body().toString())
                    token = response.headers()["jwt-token"].toString()
                    onResult(response.body())
                }else{
                    Log.d("[GOOGLE LOGIN]", response.errorBody().toString())
                    onResult(null)
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("[GOOGLE LOGIN]", t.localizedMessage)
                onResult(null)
            }
        })
    }

    // 구글 로그인 처리 후
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // result returned from launching signInIntent
        if(requestCode == 9001){

            GoogleSignIn.getSignedInAccountFromIntent(data).addOnCompleteListener { task->
                Log.d("[GOOGLE LOGIN3]", "task: $task")
                if(task.isSuccessful){
                    val account = task.result
                    // 로그인 후 처이 (계정 존재 여부 확인 후 로그인 진행)
                    afterGoogleLogin(account)
                }else{
                    Log.d("[GOOGLE LOGIN3]", "no login info")
                }
            }
        }
    }

    private fun afterGoogleLogin(account: GoogleSignInAccount) {
        Log.d("[GOOGLE LOGIN2]", account.toString())

        val loginInfo = HashMap<String,String>()
        loginInfo["id"] = account.email!!
        loginInfo["AutoLogin"] = true.toString()
        gCreateToken(loginInfo){
            if(it != null){ // 회원 정보 있는 경우
                Log.d("[GOOGLE LOGIN2]", "user: $it, token: $token")
                Toast.makeText(baseContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                // 사용자 토큰 저장
                SessionManager(this).saveToken(token)

                navigateHome(it)
            }else{ // 회원 정보 없으면 회원가입
                Log.d("[GOOGLE LOGIN2]", "after login google email: ${account.email}")
                finish()
                val gIntent = Intent(this, SignUpActivity::class.java)
                gIntent.putExtra("email", account.email)
                startActivity(gIntent)
            }
        }
    }

    // send fcm token in server
    fun saveFCMToken(onResult: (Int?) -> Unit){
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(!task.isSuccessful){
                Log.e("[SAVE FCM TOKEN]", task.exception.toString())
                return@addOnCompleteListener
            }
            val token = task.result
            val sessionManager = SessionManager(this)
            Log.d("[SAVE FCM TOKEN]", "token: $token")
            ServiceCreator.bumService.saveFCMToken("${sessionManager.fetchToken()}", token)
                .enqueue(object : Callback<Int> {
                    override fun onResponse(call: Call<Int>, response: Response<Int>) {
                        if(response.isSuccessful){
                            Log.d("[SAVE FCM TOKEN]", "${response.body()}")
                            onResult(response.body())
                        }else{
                            Log.d("[SAVE FCM TOKEN]", "${response.errorBody()}")
                            onResult(401)
                        }
                    }

                    override fun onFailure(call: Call<Int>, t: Throwable) {
                        Log.e("[SAVE FCM TOKEN]", t.localizedMessage)
                        onResult(401)
                    }
                })
        }
    }

    companion object {
        var mGoogleSignInClient: GoogleSignInClient? = null
    }


}