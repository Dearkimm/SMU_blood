package org.smu.blood.ui.my.Card

import android.os.Bundle
import android.os.TestLooperManager
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital
import org.smu.blood.ui.my.MyRequestFragment

class CardApplyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_card_apply)

        // 헌혈 요청자 정보
        val rh = if(MyRequestFragment.myRequest.rhType!!) "-" else "+"
        val blood = BloodType.values().first { it.id == MyRequestFragment.myRequest.bloodType }.bloodType
        val hospital = Hospital.values().first { it.id == MyRequestFragment.myRequest.hospitalId }.hospitalName
        findViewById<TextView>(R.id.tv_up_date).text = MyRequestFragment.myRequest.registerTime
        findViewById<TextView>(R.id.tv_up_type1).text = "Rh${rh}"
        findViewById<TextView>(R.id.tv_up_type2).text = blood
        findViewById<TextView>(R.id.tv_up_type3).text = MyRequestFragment.myRequest.donationType
        findViewById<TextView>(R.id.tv_up_hos).text = hospital
        findViewById<TextView>(R.id.tv_up_hosroom).text = MyRequestFragment.myRequest.wardNum.toString()
        findViewById<TextView>(R.id.tv_up_patient).text = MyRequestFragment.myRequest.patientName
        findViewById<TextView>(R.id.tv_up_patientnumber).text = MyRequestFragment.myRequest.patientNum.toString()
        findViewById<TextView>(R.id.tv_up_pnum).text = MyRequestFragment.myRequest.protectorContact
        findViewById<TextView>(R.id.tv_up_startdate).text = MyRequestFragment.myRequest.startDate
        findViewById<TextView>(R.id.tv_up_enddate).text = MyRequestFragment.myRequest.endDate

        // 나의 신청 정보
        val myApply = MyRequestFragment.applyList?.first{ it.requestId == MyRequestFragment.myRequest.requestId  }
        findViewById<TextView>(R.id.tv_total).text = myApply!!.applyDate
    }
}