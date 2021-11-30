package org.smu.blood.ui.my

import android.content.Intent
import android.net.Uri
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
        var card = findViewById<TextView>(R.id.tv_q)
        var hyperlink = findViewById<Button>(R.id.btn_quest)

        //전자문진하러 가기
        hyperlink.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bloodinfo.net/emi2/login.do?_ga=2.29800319.1190218835.1637677364-178623010.1637677364"))
            startActivity(intent)
        }

        //수정하러 가기
        modButton.setOnClickListener {
            val intent = Intent(this, MyModActivity()::class.java)
            startActivity(intent)
        }

        //카드 기록 보기
        card.setOnClickListener {
            val intent = Intent(this, MyCardActivity::class.java)
            startActivity(intent)
        }


        //로그아웃 팝업
        logout.setOnClickListener {
            val dlg = MyLogoutDialog(this)
            dlg.callFunction()
            dlg.show()

            dlg.setOnDismissListener {

            }
        }

        //회원탈퇴 팝업
        withdraw.setOnClickListener {
            val dlg = MyWithdraw(this)
            dlg.callFunction()
            dlg.show()

            dlg.setOnDismissListener {

            }
        }
    }
}