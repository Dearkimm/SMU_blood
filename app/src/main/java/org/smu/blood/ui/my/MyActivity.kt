package org.smu.blood.ui.my

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R

class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        //버튼 등록
        var modButton = findViewById<Button>(R.id.btn_mod1)
        var logout = findViewById<TextView>(R.id.tv_r)
        var withdraw = findViewById<TextView>(R.id.tv_withdraw)

        //수정하러 가기
        modButton.setOnClickListener {
            val intent = Intent(this, MyModActivity()::class.java)
            startActivity(intent)
        }

        //버튼

        //로그아웃 팝업
        logout.setOnClickListener {
            val dlg = MyLogoutDialog(applicationContext)
            dlg.callFunction()
            dlg.show()

            dlg.setOnDismissListener {

            }
        }

        //회원탈퇴 팝업
        withdraw.setOnClickListener {
            val dlg = MyWithdraw(applicationContext)
            dlg.callFunction()
            dlg.show()

            dlg.setOnDismissListener {

            }
        }
    }
}