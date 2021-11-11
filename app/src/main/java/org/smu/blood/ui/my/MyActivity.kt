package org.smu.blood.ui.my

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R

class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        //버튼 등록
        var modButton = findViewById<Button>(R.id.btn_mod1)

        //수정하러 가기
        modButton.setOnClickListener {
            val intent = Intent(this, MyModActivity()::class.java)
            startActivity(intent)
        }
    }
}