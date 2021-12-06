package org.smu.blood.ui.my

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.smu.blood.R
import org.w3c.dom.Text

class MyModActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_mod)
        val user = Firebase.auth.currentUser
        //val newPassword = "SOME-SECURE-PASSWORD"

        //edittext 등록
        var editPassword = findViewById<EditText>(R.id.met_pwd2)
        var editPassword2 = findViewById<EditText>(R.id.met_pwd3)

        //text 등록
        var checkpwd = findViewById<TextView>(R.id.wrong_pwd)

        //비밀번호 일치 여부
        editPassword2.addTextChangedListener(object: TextWatcher {
            // EditText에 문자 입력 전
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            // EditText에 변화가 있을 경우
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            // EditText 입력이 끝난 후
            override fun afterTextChanged(p0: Editable?) {
                if(editPassword.getText().toString().equals(editPassword2.getText().toString())){
                    checkpwd.visibility = View.INVISIBLE
                }
                else
                    checkpwd.visibility = View.VISIBLE
            }
        })

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