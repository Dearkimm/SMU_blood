package org.smu.blood.ui.map

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import org.smu.blood.NavigationActivity
import org.smu.blood.R
import org.smu.blood.ui.board.BoardRegisterAlert

class MapApplicationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_notice)

        //확인 버튼
        var okay = findViewById<Button>(R.id.notice_okay)

        //체크박스
        var check = findViewById<CheckBox>(R.id.n_checkbox)
        var check2 = findViewById<CheckBox>(R.id.n_checkbox2)

        //체크박스 옆 텍스트뷰
        var text = findViewById<TextView>(R.id.notice_tv)
        var text2 = findViewById<TextView>(R.id.notice_tv2)

        //체크박스 누르면 텍스트뷰 컬러
        check.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) { text.setTextColor(Color.RED) }
            else { text.setTextColor(Color.BLACK) }
        }

        check2.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) { text2.setTextColor(Color.RED) }
            else { text2.setTextColor(Color.BLACK) }
        }

        //확인 버튼 누르면 dialog 뜨게


    }}