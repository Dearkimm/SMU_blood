package org.smu.blood.ui.main

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R

class MainRequestActivity : AppCompatActivity() {

    var bloodType: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_request)

        //버튼
        var registerButton = findViewById<Button>(R.id.btn_register)

        var typeA = findViewById<Button>(R.id.type2_a)
        var typeB = findViewById<Button>(R.id.type2_b)
        var typeO = findViewById<Button>(R.id.type2_o)
        var typeAB = findViewById<Button>(R.id.type2_ab)

        typeA.setOnClickListener {
            bloodType = 1
            typeA.setBackgroundResource(R.drawable.bg_btn_red_5dp)
            typeA.setTextColor(Color.WHITE)
            typeB.setTextColor(Color.BLACK)
            typeAB.setTextColor(Color.BLACK)
            typeO.setTextColor(Color.BLACK)
            typeB.setBackgroundResource(R.drawable.bg_btn_type)
            typeO.setBackgroundResource(R.drawable.bg_btn_type)
            typeAB.setBackgroundResource(R.drawable.bg_btn_type)
        }

        typeB.setOnClickListener {
            bloodType = 2
            typeB.setBackgroundResource(R.drawable.bg_btn_red_5dp)
            typeB.setTextColor(Color.WHITE)
            typeA.setTextColor(Color.BLACK)
            typeAB.setTextColor(Color.BLACK)
            typeO.setTextColor(Color.BLACK)
            typeA.setBackgroundResource(R.drawable.bg_btn_type)
            typeO.setBackgroundResource(R.drawable.bg_btn_type)
            typeAB.setBackgroundResource(R.drawable.bg_btn_type)

        }

        typeO.setOnClickListener {
            bloodType = 3
            typeO.setBackgroundResource(R.drawable.bg_btn_red_5dp)
            typeO.setTextColor(Color.WHITE)
            typeA.setTextColor(Color.BLACK)
            typeAB.setTextColor(Color.BLACK)
            typeB.setTextColor(Color.BLACK)
            typeA.setBackgroundResource(R.drawable.bg_btn_type)
            typeB.setBackgroundResource(R.drawable.bg_btn_type)
            typeAB.setBackgroundResource(R.drawable.bg_btn_type)

        }

        typeAB.setOnClickListener {
            bloodType = 4
            typeAB.setBackgroundResource(R.drawable.bg_btn_red_5dp)
            typeAB.setTextColor(Color.WHITE)
            typeB.setTextColor(Color.BLACK)
            typeO.setTextColor(Color.BLACK)
            typeA.setTextColor(Color.BLACK)
            typeA.setBackgroundResource(R.drawable.bg_btn_type)
            typeO.setBackgroundResource(R.drawable.bg_btn_type)
            typeB.setBackgroundResource(R.drawable.bg_btn_type)

        }

        //요청 등록 후 종료
        registerButton.setOnClickListener {
            //등록하고
            //종료
            finish()
        }

    }
}