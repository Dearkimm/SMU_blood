package org.smu.blood.ui.my

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.ui.LoginActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import org.smu.blood.api.database.User
import com.google.firebase.database.DatabaseError
import org.smu.blood.databinding.FragmentMyBinding
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.ktx.getValue
import org.smu.blood.api.MyPageService
import org.smu.blood.api.SessionManager
import org.smu.blood.ui.base.BaseFragment


class MyFragment : BaseFragment<FragmentMyBinding>() {
    var logoutState = false
    lateinit var mDatabase: FirebaseDatabase //데이터베이스
    private lateinit var myRef: DatabaseReference //데이터베이스 리퍼런스
    private lateinit var auth: FirebaseAuth //파이어베이스 계정
    private lateinit var tempuid :String
    lateinit var bloodTypetext: String
    lateinit var rhTypetext: String

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMyBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
        }
        var myPageService = MyPageService(requireContext())

        /*
        auth = FirebaseAuth.getInstance()
        tempuid = auth.currentUser?.uid.toString()
        Log.d("온크리에이트 마이페이지 uid: ", auth.currentUser?.uid.toString())
        //binding = FragmentMyBinding.inflate(layoutInflater)

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
                    if(userInfo.bloodType.toString()=="1") bloodTypetext = "A"
                    if(userInfo.bloodType.toString()=="2") bloodTypetext = "B"
                    if(userInfo.bloodType.toString()=="3") bloodTypetext = "O"
                    if(userInfo.bloodType.toString()=="4") bloodTypetext = "AB"
                    if(userInfo.rhType.toString()=="true") rhTypetext = "Rh- "
                    if(userInfo.rhType.toString()=="false") rhTypetext = "Rh+ "
                    binding.userId.text = userInfo.id
                    binding.userName.text =userInfo.nickname.toString()
                    binding.userType.text = rhTypetext + bloodTypetext
                }
            } //onDataChange
            override fun onCancelled(error: DatabaseError) {
            } //onCancelled
        }) //addValueEventListener


         */
        // 사용자 정보 가져오기
        myPageService.myInfo(){
            if(it!=null){
                Log.d("MY INFO", it.toString())
                when(it.bloodType){
                    1-> bloodTypetext = "A"
                    2 -> bloodTypetext = "B"
                    3 -> bloodTypetext = "O"
                    4 -> bloodTypetext = "AB"
                }
                when(it.rhType){
                    true -> rhTypetext = "Rh- "
                    false -> rhTypetext = "Rh+ "
                }
                binding.userId.text = it.id
                binding.userName.text = it.nickname
                binding.userType.text = rhTypetext + bloodTypetext
            }
        }

        //버튼
        val logout = binding.tvR
        val withdraw = binding.tvWithdraw
        val card = binding.tvQ
        var hyperlink = binding.btnQuest
        val modButton = binding.btnMod1
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
                    // token 삭제
                    var sessionManager = SessionManager(requireContext())
                    sessionManager.removeToken()
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
                var withdrawState = dlg.returnState()
                if(withdrawState){ //탈퇴하기
                    Toast.makeText(context, "탈퇴 완료", Toast.LENGTH_SHORT).show()
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