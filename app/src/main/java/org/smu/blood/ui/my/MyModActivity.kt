package org.smu.blood.ui.my

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.smu.blood.R
import org.smu.blood.api.MyPageService

class MyModActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_mod)
        //val user = Firebase.auth.currentUser
        var map= HashMap<String,String>()
        //val newPassword = "SOME-SECURE-PASSWORD"

        //edittext 등록

        var currentPassword = findViewById<EditText>(R.id.met_pwd) // 현재 비밀번호
        var newPassword = findViewById<EditText>(R.id.met_pwd2) // 변경 비밀번호
        var confirmPassword = findViewById<EditText>(R.id.met_pwd3)
        var newNickname = findViewById<EditText>(R.id.met_name) // 변경 닉네임

        //text 등록
        var checkpwd = findViewById<TextView>(R.id.wrong_pwd)
        var checkbeforepwd = findViewById<TextView>(R.id.different_pwd)
        var current_id = findViewById<TextView>(R.id.mtv_id2)

        //버튼 등록
        var modButton = findViewById<Button>(R.id.btn_mod2)

        // 내 아이디 가져오기
        var myPageService = MyPageService(this)
        myPageService.myId(){
            if(it!=null){
                Log.d("[MY ID]",it)
                current_id.text = it
            }
        }

        //기존 비밀번호 일치 여부
        currentPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if(currentPassword.getText().toString() == "aaaaaa"){ //"aaaaaa"부분 바꾸기
                        checkbeforepwd.visibility = View.INVISIBLE
                } else
                    checkbeforepwd.visibility = View.VISIBLE
            }
        })

        //변경 비밀번호 일치 여부
        confirmPassword.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if(newPassword.text.equals(confirmPassword.text)){
                    checkpwd.visibility = View.INVISIBLE
                } else
                    checkpwd.visibility = View.VISIBLE
            }
        })

        //수정 완료 후 화면 종료
        modButton.setOnClickListener {
            // 기존 비밀번호 누락된 경우
            if(currentPassword.text.isNullOrEmpty()) {
                // 기존 비밀번호 입력하도록 TextView 메시지 띄우기
                Toast.makeText(baseContext, "정보 업데이트 실패", Toast.LENGTH_SHORT).show()
            }
            else{
                map.put("current_pw", currentPassword.text.toString())
                if(!newPassword.text.isNullOrEmpty()) map.put("new_pw", newPassword.text.toString())
                if(!newNickname.text.isNullOrEmpty()) map.put("new_nickname", newNickname.text.toString())
                map.forEach { (key, value) -> Log.d("INPUT DATA", "$key = $value") }
                var myPageService = MyPageService(this)
                myPageService.editData(map){
                    //수정 완료
                    if(it==false) Toast.makeText(baseContext, "정보 업데이트 실패", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(baseContext, "정보 업데이트 성공", Toast.LENGTH_SHORT).show()
                }
                //종료
                finish()
            }

        }
    }
}