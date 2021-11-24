package org.smu.blood.ui.my

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.smu.blood.R

class MyFragment : Fragment() {

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
        var logout = rootView.findViewById<TextView>(R.id.tv_r)
        var withdraw = rootView.findViewById<TextView>(R.id.tv_withdraw)

        //로그아웃 팝업
        logout.setOnClickListener {
            val dlg = MyLogoutDialog(requireContext())
            dlg.callFunction()
            dlg.show()

            dlg.setOnDismissListener {

            }
        }

        //회원탈퇴 팝업
        withdraw.setOnClickListener {
            val dlg = MyWithdraw(requireContext())
            dlg.callFunction()
            dlg.show()

            dlg.setOnDismissListener {

            }
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