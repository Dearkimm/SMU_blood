package org.smu.blood.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.databinding.ActivityLoginBinding
import org.smu.blood.util.shortToast

class LoginActivity : AppCompatActivity() {

    var backKeyPressedTime : Long = 0
    private lateinit var binding: ActivityLoginBinding

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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureNavigation()
    }

    private fun configureNavigation() {
        // 로그인 버튼 클릭 시
        binding.btnLog.setOnClickListener {
            if (binding.letId.text.isNotBlank() && binding.letPwd.text.isNotBlank()) {
                login()
            } else {
                shortToast("빈 칸이 있습니다")
            }
        }

        // 회원가입 버튼 클릭 시
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, 111)
        }
    }

    private fun login() {
        navigateHome()
        // 서버 연결 코드
//        val requestLoginData = RequestLoginData(
//            id = binding.id.text.toString(), password = binding.password.text.toString()
//        )
//        val call: Call<ResponseLoginData> = ServiceCreator.soptService.postLogin(requestLoginData)
//        call.enqueue(object : Callback<ResponseLoginData> {
//            override fun onResponse(
//                call: Call<ResponseLoginData>,
//                response: Response<ResponseLoginData>
//            ) {
//                if (response.isSuccessful) {
//                    val data = response.body()?.data
//                    Toast.makeText(
//                        this@LoginActivity,
//                        "${data?.nickname}님, 반갑습니다.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    navigateHome()
//                } else {
//
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseLoginData>, t: Throwable) {
//                Log.d("NetworkTest", "error:$t")
//            }
//        })
    }

    private fun navigateHome() {
        val intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}