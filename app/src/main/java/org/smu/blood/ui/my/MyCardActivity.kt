package org.smu.blood.ui.my

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.smu.blood.R

class MyCardActivity : AppCompatActivity() {
    //프래그먼트 관련 변수
    private var fragmentManager: FragmentManager? = null
    //private var fragmentApply: MyCardApplyFragment? = null
    //private var fragmentRequest: MyCardRequestFragment? = null
    //private var fragmentRequestList: MyCardRequestListFragment? = null
    //private var fragmentApplyList: MyCardApplyListFragment? = null
    //private var transaction: FragmentTransaction? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_card)

        //네이게이션 버튼
        var request = findViewById<TextView>(R.id.rec_request)
        var apply = findViewById<TextView>(R.id.rec_apply)

        //프래그먼트 관련 초기화
        fragmentManager = supportFragmentManager
        //fragmentApply = MyCardApplyFragment()
        //fragmentRequest = MyCardRequestFragment()
        //fragmentApplyList = MyCardApplyListFragment()
        //fragmentRequestList = MyCardRequestListFragment()
        //transaction = fragmentManager?.beginTransaction()
        //transaction!!.replace(R.id.frame, fragmentApplyList!!).commitAllowingStateLoss()
        //요청카드 띄우기
        request.setOnClickListener {
            request.setTextColor(Color.RED)
            apply.setTextColor(Color.BLACK)
            //transaction = fragmentManager?.beginTransaction()
            //transaction!!.replace(R.id.frame, fragmentRequestList!!).commitAllowingStateLoss()
        }

        //신청 카드 띄우기
        apply.setOnClickListener {
            apply.setTextColor(Color.RED)
            request.setTextColor(Color.BLACK)
            //transaction = fragmentManager?.beginTransaction()
            //transaction!!.replace(R.id.frame, fragmentApplyList!!).commitAllowingStateLoss()
        }

    }
}