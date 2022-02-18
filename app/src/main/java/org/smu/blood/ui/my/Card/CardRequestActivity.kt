package org.smu.blood.ui.my.Card

import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital
import org.smu.blood.ui.my.MyRequestFragment
import org.w3c.dom.Text

class CardRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_card_request)

        // 신청자 정보
        findViewById<TextView>(R.id.tv_total).text = MyRequestFragment.myRequest.applicantNum.toString()
        // 신청자 수에 따라 TextView 늘어나도록 하기 (신청일시 TextView를 3개로 제한하지 않고)
        /*
        for(apply in MyRequestFragment.applyList)
            findViewById<TextView>(R.id.tv_date1).text = apply.applyDate
         */

        // 나의 요청 정보
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
    }
}