package org.smu.blood.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.databinding.ActivityLoginBinding
import org.smu.blood.util.shortToast
import android.view.View.OnFocusChangeListener
import android.view.WindowManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.smu.blood.api.ServiceCreator
import org.smu.blood.api.SessionManager
import org.smu.blood.api.database.MainRequest
import org.smu.blood.api.database.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    var backKeyPressedTime : Long = 0
    private lateinit var binding: ActivityLoginBinding
    private lateinit var myRef: DatabaseReference //데이터베이스 리퍼런스
    lateinit var mDatabase: FirebaseDatabase //데이터베이스
    private lateinit var tempuid :String
    var idText: String = ""
    lateinit var token : String
    //var mGoogleSignInClient: GoogleSignInClient? = null

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        tempuid = auth!!.currentUser?.uid.toString()
        /*auth!!.addAuthStateListener {
            val user = auth!!.currentUser
            user?.let {
                tempuid = user.uid
            }
        }*/

        var editId = findViewById<EditText>(R.id.let_id)
        var exId = findViewById<TextView>(R.id.id_type)

        editId.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if(hasFocus){exId.visibility = View.VISIBLE}
            else{exId.visibility = View.INVISIBLE}
        })
        configureNavigation()
    }

    private fun configureNavigation() {
        var autoLogin=false
        lateinit var context: Context
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
                        var sessionManager = SessionManager(this)
                        sessionManager.saveToken(token)
                        navigateHome(it)
                    }else{
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
                /*
                login(binding.letId.getText().toString(), binding.letPwd.getText().toString())
                //파이어베이스데이터읽어오기
                Log.d("로그인 uid: ",tempuid)
                mDatabase = FirebaseDatabase.getInstance()
                myRef = mDatabase.reference.child("Users").child(tempuid)
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userInfo = snapshot.getValue<User>()
                        if (userInfo != null) {
                            Log.d("로그인후읽어오기 ",userInfo.id.toString())
                        }
                    } //onDataChange
                    override fun onCancelled(error: DatabaseError) {
                    } //onCancelled
                }) //addValueEventListener
                */
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
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    private fun googleLogin(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) // request user id, profile info
            .requestEmail() // request user mail
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // 구글 통해 앱 로그인 여부 확인
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account!=null){ // 로그인 했으면 토큰 저장
            val loginInfo = HashMap<String,String>()
            loginInfo["id"] = account.email!!
            loginInfo["AutoLogin"] = true.toString()
            gCreateToken(loginInfo){
                if(it != null){
                    Log.d("[GOOGLE LOGIN]", "user: $it, token: $token")
                    Toast.makeText(baseContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                    // 사용자 토큰 저장
                    var sessionManager = SessionManager(this)
                    sessionManager.saveToken(token)
                    navigateHome(it)
                }else{
                    Log.d("[GOOGLE LOGIN]", "after login google email: ${account.email}")
                    finish()
                    val gIntent = Intent(this, SignUpActivity::class.java)
                    gIntent.putExtra("email", account.email)
                    startActivity(gIntent)
                }
            }

        }else{ // 구글 로그인 연동 진행
            Log.d("[GOOGLE LOGIN]", "login google")
            val signInIntent = mGoogleSignInClient!!.signInIntent
            startActivity(signInIntent)

            val account = GoogleSignIn.getLastSignedInAccount(this)
            if(account != null){ // 회원가입 페이지로 이동
                Log.d("[GOOGLE LOGIN]", "after login google email: ${account.email}")
                finish()
                val gIntent = Intent(this, SignUpActivity::class.java)
                gIntent.putExtra("email", account.email)
                startActivity(gIntent)
            }else{
                Log.d("[GOOGLE LOGIN]", "no login info")
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
    /*
    private fun login(email: String, password: String) {
        auth?.signInWithEmailAndPassword(binding.letId.text.toString(), binding.letPwd.text.toString())
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "로그인에 성공하였습니다", Toast.LENGTH_SHORT).show()
                    navigateHome(auth?.currentUser)
                } else {
                    Toast.makeText(baseContext, "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun navigateHome(user: FirebaseUser?) {
        if( user!= null){
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

     */

    companion object {
        var mGoogleSignInClient: GoogleSignInClient? = null
    }


}