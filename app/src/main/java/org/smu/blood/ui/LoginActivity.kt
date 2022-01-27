package org.smu.blood.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.databinding.ActivityLoginBinding
import org.smu.blood.util.shortToast
import android.view.View.OnFocusChangeListener
import android.view.WindowManager

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import org.smu.blood.api.ServiceCreator
import org.smu.blood.api.database.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    var backKeyPressedTime : Long = 0
    private lateinit var binding: ActivityLoginBinding
    private lateinit var myRef: DatabaseReference //데이터베이스 리퍼런스
    lateinit var mDatabase: FirebaseDatabase //데이터베이스
    private lateinit var tempuid :String
    var idText: String = ""


    override fun onBackPressed(){
        if (System.currentTimeMillis()> backKeyPressedTime + 2500){
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(applicationContext, "한 번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT).show()
            return
        }
        else {finishAffinity()}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        tempuid = auth!!.currentUser?.uid.toString()
        /*auth!!.addAuthStateListener {
            val user = auth!!.currentUser
            user?.let {
                tempuid = user.uid
            }
        }*/

        var editId = findViewById<EditText>(R.id.let_id)
        var exId = findViewById<TextView>(R.id.id_type)

        editId.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if(hasFocus){exId.visibility = View.VISIBLE}
            else{exId.visibility = View.INVISIBLE}
        })
        configureNavigation()
    }

    private fun configureNavigation() {
        var user: User
        // 로그인 버튼 클릭 시
        binding.btnLog.setOnClickListener {
            if (binding.letId.text.isNotBlank() && binding.letPwd.text.isNotBlank()) {
                user = signIn(binding.letId.getText().toString(), binding.letPwd.getText().toString())
                if(user != null){
                    Toast.makeText(baseContext, "로그인에 성공하였습니다", Toast.LENGTH_SHORT).show()
                    navigateHome(user)
                }else{
                    Toast.makeText(baseContext, "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
                /*
                login(binding.letId.getText().toString(), binding.letPwd.getText().toString())
                //파이어베이스데이터읽어오기
                Log.d("로그인 uid: ",tempuid)
                mDatabase = FirebaseDatabase.getInstance()
                myRef = mDatabase.reference.child("Users").child(tempuid)
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userInfo = snapshot.getValue<User>()
                        if (userInfo != null) {
                            Log.d("로그인후읽어오기 ",userInfo.id.toString())
                        }
                    } //onDataChange
                    override fun onCancelled(error: DatabaseError) {
                    } //onCancelled
                }) //addValueEventListener
                */
            } else {
                shortToast("빈 칸이 있습니다")
            }
        }

        // 회원가입 버튼 클릭 시
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, 111)
        }
    }
    private fun signIn(id: String, password: String): User{
        var user: User? = null
        val loginUser = ServiceCreator.bumService.loginUser(id, password)
        loginUser.enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    if (response.body() == null) { // 해당 사용자가 없는 경우
                        Log.d("LOGIN", "FAILED")
                    } else{ // 사용자 있는 경우
                        Log.d("LOGIN", "SUCCESS")
                        user = response.body()
                    }
                }else{
                    Log.d("RESPONSE FROM SERVER: ", "FAILURE")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("CONNECTION TO SERVER FAILURE: ", t.localizedMessage)
            }

        })
        Log.d("LOGIN USER", user.toString())
        return user!!
    }

    private fun navigateHome(user: User?) {
        if( user!= null){
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
    /*
    private fun login(email: String, password: String) {
        auth?.signInWithEmailAndPassword(binding.letId.text.toString(), binding.letPwd.text.toString())
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "로그인에 성공하였습니다", Toast.LENGTH_SHORT).show()
                    navigateHome(auth?.currentUser)
                } else {
                    Toast.makeText(baseContext, "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun navigateHome(user: FirebaseUser?) {
        if( user!= null){
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

     */

}