package org.smu.blood.ui.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R

class MainRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_request)

        //버튼
        var registerButton = findViewById<Button>(R.id.btn_register)

        //요청 등록 후 종료
        registerButton.setOnClickListener {
            //등록하고
            //종료
            finish()
        }

    }
}