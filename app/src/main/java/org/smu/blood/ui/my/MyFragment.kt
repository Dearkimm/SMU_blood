package org.smu.blood.ui.my

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.R
import org.smu.blood.ui.LoginActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import org.smu.blood.api.database.User
import com.google.firebase.database.DatabaseError
import org.smu.blood.databinding.FragmentMyBinding
import androidx.annotation.NonNull

import android.R.string.no
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.UserInfo
import com.google.firebase.database.ktx.getValue
import org.smu.blood.databinding.ActivityBoardRegisterBinding


class MyFragment : Fragment() {
    var logoutState = false
    var withdrawState = false
    lateinit var mDatabase: FirebaseDatabase //데이터베이스
    private lateinit var myRef: DatabaseReference //데이터베이스 리퍼런스
    private lateinit var auth: FirebaseAuth //파이어베이스 계정
    private lateinit var binding: FragmentMyBinding
    var tempuid :String =""
    var idText: String = ""
    var nicknameText: String = ""
    var passwordText: String = ""
    var bloodType: String = ""
    var rhType: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        auth = FirebaseAuth.getInstance()
        tempuid = auth.currentUser?.uid.toString()
        Log.d("온크리에이트 마이페이지 uid: ", auth.currentUser?.uid.toString())
        binding = FragmentMyBinding.inflate(layoutInflater)
        //파이어베이스데이터읽어오기
        Log.d("마이페이지 uid: ",tempuid)
        mDatabase = FirebaseDatabase.getInstance()
        myRef = mDatabase.reference.child("Users").child(tempuid)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue<User>()
                if (userInfo != null) {
                    Log.d("마이페이지 아이디 읽어오기 ",userInfo.id.toString())
                    Log.d("비번 읽어오기 ",userInfo.password.toString())
                    Log.d("닉넴 읽어오기 ",userInfo.nickname.toString())
                    idText = userInfo.id.toString()
                    nicknameText = userInfo.nickname.toString()
                    if(userInfo.bloodType==1) bloodType = "A"
                    if(userInfo.bloodType==2) bloodType = "B"
                    if(userInfo.bloodType==3) bloodType = "O"
                    if(userInfo.bloodType==4) bloodType = "AB"
                    if(userInfo.rhType==true) rhType = "-"
                    if(userInfo.rhType==false) rhType = "+"
                    /*binding.userId.text = userInfo.id.toString()
                    binding.userName.text =userInfo.nickname.toString()
                    binding.userType.text = userInfo.bloodType.toString()+userInfo.rhType.toString()*/
                }
                /*
                    if(userInfo.bloodType==1) bloodType = "A"
                    if(userInfo.bloodType==2) bloodType = "B"
                    if(userInfo.bloodType==3) bloodType = "O"
                    if(userInfo.bloodType==4) bloodType = "AB"
                    if(userInfo.rhType==true) rhType = "-"
                    if(userInfo.rhType==false) rhType = "+"
                }*/
            } //onDataChange
            override fun onCancelled(error: DatabaseError) {
            } //onCancelled
        }) //addValueEventListener

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_my, container, false)

        //버튼
        val logout = rootView.findViewById<TextView>(R.id.tv_r)
        val withdraw = rootView.findViewById<TextView>(R.id.tv_withdraw)
        val card = rootView.findViewById<TextView>(R.id.tv_q)
        var hyperlink = rootView.findViewById<Button>(R.id.btn_quest)
        val modButton = rootView.findViewById<Button>(R.id.btn_mod1)

        //text
       /* val userId = rootView.findViewById<TextView>(R.id.user_id)
        val userName = rootView.findViewById<TextView>(R.id.user_name)
        val userType = rootView.findViewById<TextView>(R.id.user_type)*/
        binding.userId.text = idText
        binding.userName.text =nicknameText
        binding.userType.text = bloodType

        configureMyNavigation()

        //수정하러 가기
        modButton.setOnClickListener {
            activity?.let{
                val intent = Intent(context, MyModActivity::class.java)
                startActivity(intent)
            }
        }

        //전자문진하러 가기
        hyperlink.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bloodinfo.net/emi2/login.do?_ga=2.29800319.1190218835.1637677364-178623010.1637677364"))
            startActivity(intent)
        }

        //로그아웃 팝업
        logout.setOnClickListener {
            val dlg = MyLogoutDialog(requireContext())
            dlg.callFunction()
            dlg.show()

            dlg.setOnDismissListener {
                logoutState = dlg.returnState()
                if(logoutState){ //로그아웃
                    //로그인화면으로 이동
                    val intent2 = Intent(context, LoginActivity()::class.java)
                    //네비게이션 액티비티
                    (activity as NavigationActivity).logoutAndfinish()
                    startActivity(intent2)
                }
            }
        }

        //회원탈퇴 팝업
        withdraw.setOnClickListener {
            val dlg = MyWithdraw(requireContext())
            dlg.callFunction()
            dlg.show()

            dlg.setOnDismissListener {
                withdrawState = dlg.returnState()
                if(withdrawState){ //탈퇴하기
                    //로그인화면으로 이동
                    val intent2 = Intent(context, LoginActivity()::class.java)
                    //네비게이션 액티비티
                    (activity as NavigationActivity).logoutAndfinish()
                    startActivity(intent2)
                }
            }
        }

        //카드 기록 보기
        card.setOnClickListener {
            (activity as NavigationActivity).navigateMyToRequest()
        }

        return rootView
    }

    private fun configureMyNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback {
            (activity as NavigationActivity).popMy()
        }
    }

    companion object {
       @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}