package org.smu.blood.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.databinding.ActivityLoginBinding
import org.smu.blood.util.shortToast
import android.view.View.OnFocusChangeListener

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null

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
        auth = FirebaseAuth.getInstance()

        var editId = findViewById<EditText>(R.id.let_id)
        var exId = findViewById<TextView>(R.id.id_type)

        editId.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if(hasFocus){exId.visibility = View.VISIBLE}
            else{exId.visibility = View.INVISIBLE}

        })

        configureNavigation()
    }

    private fun configureNavigation() {
        // 로그인 버튼 클릭 시
        binding.btnLog.setOnClickListener {
            if (binding.letId.text.isNotBlank() && binding.letPwd.text.isNotBlank()) {
                login(binding.letId.getText().toString(), binding.letPwd.getText().toString())
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
}