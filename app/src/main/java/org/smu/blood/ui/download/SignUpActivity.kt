package org.smu.blood.ui.download

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //버튼
        var joinButton = findViewById<Button>(R.id.btn_join)

        //계정 생성 이후 로그인으로 돌아가기
        joinButton.setOnClickListener {
            finish()
        }
    }
}