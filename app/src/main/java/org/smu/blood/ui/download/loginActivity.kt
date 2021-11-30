package org.smu.blood.ui.download

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.NavigationActivity
import org.smu.blood.R
import org.w3c.dom.Text

class loginActivity : AppCompatActivity() {
    lateinit var idText: String
    lateinit var passwordText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //버튼
        var loginButton = findViewById<Button>(R.id.btn_log)
        var signupButton = findViewById<TextView>(R.id.btn_Sign_up)

        //edittext들
        var editId = findViewById<EditText>(R.id.let_id)
        var editPassword = findViewById<EditText>(R.id.let_pwd)

        //로그인 후 메인으로 이동
        loginButton.setOnClickListener {
            if (editId.text.isNullOrBlank() || editPassword.text.isNullOrBlank()) {
                Toast.makeText(applicationContext, "빈 칸이 있습니다", Toast.LENGTH_SHORT).show()
            } else {
                idText = editId.text.toString()
                passwordText = editPassword.text.toString()
                val intent = Intent(this, NavigationActivity()::class.java)
                startActivity(intent)
            }
        }

        //회원가입 화면으로 이동
        signupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, 101)
        }

    }
}