package org.smu.blood.ui

import org.smu.blood.api.database.User
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.api.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {
    lateinit var idText: String
    lateinit var nicknameText: String
    lateinit var passwordText: String
    var bloodType: Int = 0
    var rhType: Boolean = false
    var userInfo = User()
    lateinit var cid: TextView
    lateinit var dname: TextView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up)

        //체크박스
        val checkbox = findViewById<CheckBox>(R.id.checkbox)
        //체크박스 옆 텍스트뷰
        val text = findViewById<TextView>(R.id.signup_tv)
        val pwdtext = findViewById<TextView>(R.id.pwd_type)

        //버튼
        val joinButton = findViewById<Button>(R.id.btn_join)

        //edittext
        val editName = findViewById<EditText>(R.id.set_name)
        val editId = findViewById<EditText>(R.id.set_id)
        val editPassword = findViewById<EditText>(R.id.set_pwd)
        val editPassword2 = findViewById<EditText>(R.id.set_pwd2)


        //혈액형 버튼
        val typeA = findViewById<Button>(R.id.type_a)
        val typeB = findViewById<Button>(R.id.type_b)
        val typeO = findViewById<Button>(R.id.type_o)
        val typeAB = findViewById<Button>(R.id.type_ab)

        //textView
        val checkpwd= findViewById<TextView>(R.id.stv_npwd)
        cid = findViewById<TextView>(R.id.stv_cid)
        dname = findViewById<TextView>(R.id.stv_dname)

        // fill in userid edittext when google login
        val email = intent.getStringExtra("email")
        if(email != null){
            Log.d("[GOOGLE LOGIN]", email)
            editId.setText(email)
            // 수정 못하도록 block 처리
            editId.isEnabled = false
        }

        //체크박스
        checkbox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                text.setTextColor(Color.RED)
                rhType = true }
            else { text.setTextColor(Color.BLACK) }
        }

        //혈액형: 1 -> a, 2 -> b, 3-> o, 4-> ab
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

        editPassword.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                pwdtext.visibility = VISIBLE
            } else {
                pwdtext.visibility = INVISIBLE
            }

        })
        //비밀번호 일치 여부
        editPassword2.addTextChangedListener(object:TextWatcher{
            // EditText에 문자 입력 전
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            // EditText에 변화가 있을 경우
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            // EditText 입력이 끝난 후
            override fun afterTextChanged(p0: Editable?) {
                if(editPassword.getText().toString().equals(editPassword2.getText().toString())){
                    checkpwd.visibility = INVISIBLE
                }
                else
                    checkpwd.visibility = VISIBLE
            }
        })

        //계정 생성 이후 로그인으로 돌아가기
        joinButton.setOnClickListener {
            if (editName.text.isNullOrBlank() || editId.text.isNullOrBlank() || editPassword.text.isNullOrBlank() || editPassword2.text.isNullOrBlank()) {
                Toast.makeText(applicationContext, "빈 칸이 있습니다", Toast.LENGTH_SHORT).show()
            } else {
                //입력값 저장
                idText = editId.text.toString()
                passwordText = editPassword.text.toString()
                nicknameText = editName.text.toString()

                // user 객체에 입력 내용 넣기
                userInfo.id = idText
                userInfo.password = passwordText
                userInfo.nickname = nicknameText
                userInfo.bloodType = bloodType
                userInfo.rhType = rhType
                Log.d("USERINFO: ", userInfo.toString())

                //입력한 내용을 서버에 넣어주기
                signUp(userInfo){
                    if(it == true){
                        // 회원가입 성공한 경우
                        Toast.makeText(applicationContext, "회원가입 완료", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else{
                        // 회원가입 실패한 경우
                        Toast.makeText(applicationContext, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

    }

    // 회원가입 정보 Spring 서버로 전송
    private fun signUp(user: User, onResult: (Boolean?)->Unit) {
        val signUpAPIService = ServiceCreator.bumService.createUser(user)

        signUpAPIService.enqueue(object : Callback<HashMap<String, Int>> {
            override fun onResponse(call: Call<HashMap<String, Int>>, response: Response<HashMap<String, Int>>) {
                if(response.isSuccessful){ // 통신 성공 and 응답 성공
                    response.body()!!.forEach { (key, value) -> Log.d("RESPONSE: ", "$key = $value") }
                    if(response.body()!!["create"]!! == 1) {
                        Log.d("RESPONSE", "USER CREATION SUCCESS")
                        onResult(true)
                    }
                    if(response.body()!!["sameId"]!! == 1){ // id 중복
                        Log.d("RESPONSE", "SAME ID EXISTS")
                        cid.visibility = VISIBLE
                        onResult(false)
                    }
                    if(response.body()!!["sameNickname"]!! == 1){ // nickname 중복
                        Log.d("RESPONSE", "SAME NICKNAME EXISTS")
                        dname.visibility = VISIBLE
                        dname.setText(R.string.su_tv12)
                        onResult(false)
                    }
                }else{ // 통신 성공 but 응답 실패
                    Log.d("RESPONSE FROM SERVER", "FAILURE")
                    onResult(false)
                }
            }

            override fun onFailure(call: Call<HashMap<String, Int>>, t: Throwable) {
                Log.d("CONNECTION TO SERVER FAILURE", t.localizedMessage)
                onResult(false)
            }
        })
    }
    /*
    //파이어베이스에서 계정 생성
    private fun createUser(email: String, password: String) {
        //Log.d("변수", email+", "+password)
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("회원가입", "성공")
                } else {
                    Log.d("회원가입", "실패")
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
            }
        Log.d("뒤에서 uid", auth.currentUser.toString())
    }
    */
}





