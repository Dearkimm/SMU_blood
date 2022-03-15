package org.smu.blood.ui.my

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import com.google.firebase.messaging.FirebaseMessaging
import org.smu.blood.api.MyPageService
import org.smu.blood.api.SessionManager
import org.smu.blood.databinding.FragmentMyBinding
import org.smu.blood.ui.LoginActivity
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.ui.base.BaseFragment


class MyFragment : BaseFragment<FragmentMyBinding>() {
    var logoutState = false
    lateinit var bloodTypetext: String
    lateinit var rhTypetext: String

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMyBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
        }
        val myPageService = MyPageService(requireContext())

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
                    SessionManager(requireContext()).removeToken()

                    // fcm token 삭제
                    FirebaseMessaging.getInstance().deleteToken()

                    // 앱에 연결된 google 계정 삭제
                    if(LoginActivity.mGoogleSignInClient != null){
                        Log.d("[GOOGLE LOGIN]", "signout")
                        LoginActivity.mGoogleSignInClient?.signOut()
                    }

                    //로그인화면으로 이동
                    val intent2 = Intent(context, LoginActivity()::class.java)
                    //네비게이션 액티비티
                    startActivity(intent2)
                    (activity as NavigationActivity).logoutAndfinish()
                }
            }
        }

        //회원탈퇴 팝업
        withdraw.setOnClickListener {
            val dlg = MyWithdraw(requireContext())
            dlg.callFunction()
            dlg.show()

            dlg.setOnDismissListener {
                val withdrawState = dlg.returnState()
                if(withdrawState){ //탈퇴하기

                    Toast.makeText(context, "탈퇴 완료", Toast.LENGTH_SHORT).show()
                    (activity as NavigationActivity).logoutAndfinish()
                    //로그인화면으로 이동
                    val intent3 = Intent(context, LoginActivity()::class.java)
                    //네비게이션 액티비티
                    startActivity(intent3)
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