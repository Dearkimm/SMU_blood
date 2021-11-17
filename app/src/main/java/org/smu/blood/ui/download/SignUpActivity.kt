package org.smu.blood.ui.download

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import android.widget.Toast

import org.smu.blood.MainActivity

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.OnSuccessListener

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.smu.blood.database.User


class SignUpActivity : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference
    lateinit var idText : String
    lateinit var nicknameText : String
    lateinit var passwordText : String
    lateinit var password2Text : String
    var bloodType: Int = 0


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        //데이터베이스
        mDatabase = FirebaseDatabase.getInstance().getReference();



        //버튼
        var joinButton = findViewById<Button>(R.id.btn_join)

        //edittext
        var editName = findViewById<EditText>(R.id.set_name)
        var editId = findViewById<EditText>(R.id.set_id)
        var editPassword = findViewById<EditText>(R.id.set_pwd)
        var editPassword2 = findViewById<EditText>(R.id.set_pwd2)

        //혈액형 버튼
        var typeA = findViewById<Button>(R.id.type_a)
        var typeB = findViewById<Button>(R.id.type_b)
        var typeO = findViewById<Button>(R.id.type_o)
        var typeAB = findViewById<Button>(R.id.type_ab)

        //textView
        var dname = findViewById<TextView>(R.id.stv_dname)

        //dname.setVisibility(VISIBLE)


        //혈액형: 1 -> a, 2 -> b, 3-> o, 4-> ab
        typeA.setOnClickListener {
            bloodType = 1
            typeA.setBackgroundResource(R.drawable.bg_btn_red_5dp)
            typeB.setBackgroundResource(R.drawable.bg_btn_type)
            typeO.setBackgroundResource(R.drawable.bg_btn_type)
            typeAB.setBackgroundResource(R.drawable.bg_btn_type)
        }

        typeB.setOnClickListener {
            bloodType = 2
            typeB.setBackgroundResource(R.drawable.bg_btn_red_5dp)

        }

        typeO.setOnClickListener {
            bloodType = 3
            typeO.setBackgroundResource(R.drawable.bg_btn_red_5dp)

        }

        typeAB.setOnClickListener {
            bloodType = 4
            typeAB.setBackgroundResource(R.drawable.bg_btn_red_5dp)

        }



        //계정 생성 이후 로그인으로 돌아가기
        joinButton.setOnClickListener {
            if (editName.text.isNullOrBlank() || editId.text.isNullOrBlank() || editPassword.text.isNullOrBlank()  || editPassword2.text.isNullOrBlank()) {
                Toast.makeText(applicationContext, "빈 칸이 있습니다", Toast.LENGTH_SHORT).show()
            } else {
                //입력값 저장
                idText = editId.text.toString()
                passwordText = editPassword.text.toString()
                password2Text = editPassword2.text.toString()
                nicknameText = editName.text.toString()
                //입력한 내용을 서버에 넣어주기
                //writeNewUser("test1234", "테스트", "test@aa.aa")



                // 3. intent에 보낼 데이터 담기
                /* val intent = Intent(this, SignUpActivity::class.java) //행동을 담음
                intent.putExtra("nickname", editName.text.toString()) //데이터를 담음
                intent.putExtra("pwd", editPassword.text.toString())
                intent.putExtra("id", editId.text.toString())
                // 4. ActivityResultLauncher에 해당 intent 전달
                // RESULT_OK : resultCode
                setResult(RESULT_OK, intent)*/
                finish()
            }
        }


    }
    //이게 서버에 정보 넘기는거
    /*fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)
        mDatabase.child("users").child(userId).setValue(user)
            .addOnSuccessListener(OnSuccessListener<Void?> { // Write was successful!
                Toast.makeText(this@SignUpActivity, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
                Lod.d("성공", "회원가입 성공")
            })
            .addOnFailureListener(OnFailureListener { // Write failed
                Toast.makeText(this@SignUpActivity, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show()
            })
    }*/

}