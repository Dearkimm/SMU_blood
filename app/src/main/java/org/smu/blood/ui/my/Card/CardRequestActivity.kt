package org.smu.blood.ui.my.Card

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Dimension
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.databinding.ActivityCardRequestBinding
import org.smu.blood.databinding.ActivityLoginBinding
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital
import org.smu.blood.ui.my.MyRequestFragment
import org.smu.blood.util.shortToast
import org.w3c.dom.Text

class CardRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardRequestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityCardRequestBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_card_request)

        // 신청자 정보
        findViewById<TextView>(R.id.tv_total).text = MyRequestFragment.myRequest.applicantNum.toString()
        // 신청자 수에 따라 TextView 늘어나도록 하기 (신청일시 TextView를 3개로 제한하지 않고)
        val timeList = findViewById<LinearLayout>(R.id.card_request_list)
        for(i:Int in 0..MyRequestFragment.applyList.size){
            Log.d("List_size: ",MyRequestFragment.applyList.size.toString())
            val time = TextView(this@CardRequestActivity)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
            layoutParams.setMargins(0,0,0,5)
            time.layoutParams = layoutParams
            time.text = MyRequestFragment.applyList[i].applyDate//신청일시
            time.setTextSize(Dimension.SP,13.0f)
            time.typeface = resources.getFont(R.font.notosans_light)
            time.includeFontPadding = false
            time.setTextColor(Color.BLACK)
            timeList.addView(time)
            Log.d("check ",time.toString())
        }
        /*for(apply in MyRequestFragment.applyList){
            time.text = apply.applyDate //신청일시
        }*/
        val btn = findViewById<Button>(R.id.request_delete)
        btn.setOnClickListener{
            Log.d("sd","ss")
            shortToast("클릭")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("헌혈을 받으셨나요?")
            builder.setMessage("헌혈 요청 글이 삭제됩니다.")
            var listener = DialogInterface.OnClickListener { dialog, p1 ->
                when (p1) {
                    DialogInterface.BUTTON_POSITIVE ->
                        shortToast("요청을 삭제했습니다")
                    //해당 요청 글 삭제


                    DialogInterface.BUTTON_NEGATIVE ->
                        shortToast("취소")
                }
            }
            builder.setPositiveButton("확인",listener)
            builder.setNegativeButton("취소",listener)
            builder.show()
        }

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