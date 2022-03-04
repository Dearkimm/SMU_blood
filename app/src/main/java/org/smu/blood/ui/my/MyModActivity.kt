package org.smu.blood.ui.my

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.smu.blood.R
import org.smu.blood.api.MyPageService
import org.smu.blood.api.database.User
import org.smu.blood.databinding.ActivityNavigationBinding
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.ui.main.MainReadFragment
import org.smu.blood.util.popFragment
import org.smu.blood.util.replaceFragment

class MyModActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_mod)
        //val user = Firebase.auth.currentUser
        var map= HashMap<String,String>()

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

        /*
        //기존 비밀번호 일치 여부 -> 서버에서 확인하므로 사용 X
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

         */

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
            var binding: ActivityNavigationBinding = ActivityNavigationBinding.inflate(layoutInflater)
            // 기존 비밀번호 누락된 경우
            if(currentPassword.text.isNullOrEmpty()) {
                // 기존 비밀번호 입력하도록 TextView 메시지 띄우기
                Toast.makeText(baseContext, "정보 업데이트 실패", Toast.LENGTH_SHORT).show()
            }
            else{
                map.put("current_pw", currentPassword.text.toString())
                // 패스워드 변경하는 경우
                if(!newPassword.text.isNullOrEmpty()) map.put("new_pw", newPassword.text.toString())
                // 닉네임 변경하는 경우
                if(!newNickname.text.isNullOrEmpty()) map.put("new_nickname", newNickname.text.toString())
                map.forEach { (key, value) -> Log.d("INPUT DATA", "$key = $value") }
                var myPageService = MyPageService(this)
                myPageService.editData(map){
                    if(it!=null){
                        it.forEach { (key, value) -> Log.d("RESPONSE DATA", "$key = $value") }
                        // 입력한 기존 비밀번호가 사용자 비밀번호와 일치하지 않는 경우
                        if(it["wrong_pw"]==1) checkbeforepwd.visibility = View.VISIBLE
                        // 토큰이 유효하지 않는 경우
                        if(it["invalid_token"]==1) Toast.makeText(baseContext, "정보 업데이트 실패", Toast.LENGTH_SHORT).show()
                        //수정 완료
                        if(!it.containsKey("wrong_pw")&&!it.containsKey("invalid_token")) {
                            Toast.makeText(baseContext, "정보 업데이트 성공", Toast.LENGTH_SHORT).show()
                            //마이페이지로 이동
                            // 수정 이후에도 업데이트 된 내 정보를 보여주도록 마이페이지 가져오기
                            // NavigationActivity의 navigateMainToMy() 호출할 수 있으면 해당 코드로 바꾸기
                            setContentView(binding.root)
                            replaceFragment(binding.fragmentContainer, MyFragment::class.java, true)
                        }
                    }else{
                        Toast.makeText(baseContext, "정보 업데이트 실패", Toast.LENGTH_SHORT).show()
                    }
                }
                //finish()
            }

        }
    }
}