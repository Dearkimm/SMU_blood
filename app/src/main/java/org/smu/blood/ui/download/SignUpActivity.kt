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

<<<<<<< HEAD
import User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUpActivity : AppCompatActivity() {
=======
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.smu.blood.database.User


class SignUpActivity : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference

>>>>>>> master

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

<<<<<<< HEAD
        var mDatabase: DatabaseReference// ...
=======


>>>>>>> master
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //버튼
        var joinButton = findViewById<Button>(R.id.btn_join)

        //계정 생성 이후 로그인으로 돌아가기
        joinButton.setOnClickListener {

<<<<<<< HEAD

=======
            //writeNewUser("test1234", "테스트", "test@aa.aa")
>>>>>>> master

            finish()
        }

<<<<<<< HEAD
        fun writeNewUser(userId: String, name: String, email: String) {
            val user = User(name, email)
            mDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(OnSuccessListener<Void?> { // Write was successful!
                    Toast.makeText(this@SignUpActivity, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
                })
                .addOnFailureListener(OnFailureListener { // Write failed
                    Toast.makeText(this@SignUpActivity, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show()
                })
        }
    }

=======

    }
    /*fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)
        mDatabase.child("users").child(userId).setValue(user)
            .addOnSuccessListener(OnSuccessListener<Void?> { // Write was successful!
                Toast.makeText(this@SignUpActivity, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
            })
            .addOnFailureListener(OnFailureListener { // Write failed
                Toast.makeText(this@SignUpActivity, "저장을 실패했습니다.", Toast.LENGTH_SHORT).show()
            })
    }*/
>>>>>>> master

}