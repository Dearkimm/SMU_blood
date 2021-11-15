package org.smu.blood.ui.download

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import android.widget.Toast

import org.smu.blood.MainActivity

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.OnSuccessListener

import User
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class SignUpActivity : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        mDatabase = FirebaseDatabase.getInstance().getReference();


        //버튼
        var joinButton = findViewById<Button>(R.id.btn_join)

        //계정 생성 이후 로그인으로 돌아가기
        joinButton.setOnClickListener {

            writeNewUser("test1234", "테스트", "test@aa.aa")

            finish()
        }

    }



    fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)
        Log.d("시작", "start")
        mDatabase.child("users").child(userId).setValue(user)
            .addOnSuccessListener(OnSuccessListener<Void?> { // Write was successful!
                Log.d("회원가입", "저장 성공")
                Toast.makeText(this@SignUpActivity, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
            })
            .addOnFailureListener(OnFailureListener { // Write failed
                Log.e("회원가입", "저장 실패")
                Toast.makeText(this@SignUpActivity, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show()
            })
    }

}