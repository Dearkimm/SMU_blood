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
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.R
import org.smu.blood.ui.LoginActivity
import org.smu.blood.ui.main.MainFragment
import org.smu.blood.ui.map.MapApplicationActivity

class MyFragment : Fragment() {
    var logoutState = false
    var withdrawState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
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
                    val intent = Intent(context, LoginActivity()::class.java)
                    startActivity(intent)
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
                    val intent = Intent(context, LoginActivity()::class.java)
                    startActivity(intent)
                }
            }
        }

        //카드 기록 보기
        card.setOnClickListener {
            (activity as NavigationActivity).navigateMyToRequest()
        }

        return rootView
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