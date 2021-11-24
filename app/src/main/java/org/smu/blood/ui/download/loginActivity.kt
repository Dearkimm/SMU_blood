package org.smu.blood.ui.download

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.NavigationActivity
import org.smu.blood.R
import org.w3c.dom.Text

class loginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //버튼
        var logingButton = findViewById<Button>(R.id.btn_log)
        var signupButton = findViewById<TextView>(R.id.btn_Sign_up)

        //로그인 후 메인으로 이동
        logingButton.setOnClickListener {
            val intent = Intent(this, NavigationActivity()::class.java)
            startActivity(intent)
        }

        //회원가입 화면으로 이동
        signupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, 101)
        }
    }
}