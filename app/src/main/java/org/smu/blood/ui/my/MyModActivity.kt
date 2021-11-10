package org.smu.blood.ui.my

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R

class MyModActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_mod)

        //버튼 등록
        var modButton = findViewById<Button>(R.id.btn_mod2)

        //수정 완료 후 화면 종료
        modButton.setOnClickListener {
            //수정 완료
            //종료
            finish()
        }
    }
}