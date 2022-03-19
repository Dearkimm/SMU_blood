package org.smu.blood.ui.my.Card

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.api.MyPageService
import org.smu.blood.api.database.Apply
import org.smu.blood.databinding.ActivityCardRequestBinding
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital
import org.smu.blood.ui.my.MyRequestFragment.Companion.myRequest
import org.smu.blood.util.shortToast

class CardRequestActivity : AppCompatActivity() {
    private var _binding: ActivityCardRequestBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        _binding = ActivityCardRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_card_request)

        Log.d("[REQUEST CARD", myRequest.toString())
        // 신청자 정보
        binding.tvTotal.text = myRequest.applicantNum.toString()
        // 신청자 수에 따라 TextView 늘어나도록 하기
        val timeList = findViewById<LinearLayout>(R.id.card_request_list)
        Log.d("[NOTICE]", "apply list of request: ${applylist}")
        if(applylist != null){
            Log.d("List_size: ", applylist!!.size.toString())
            for (apply in applylist!!) {
                val time = TextView(this)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )
                layoutParams.setMargins(0, 0, 0, 5)
                time.layoutParams = layoutParams
                time.text = apply.applyDate//신청일시
                time.setTextSize(Dimension.SP, 13.0f)
                time.typeface = resources.getFont(R.font.notosans_light)
                time.includeFontPadding = false
                time.setTextColor(Color.BLACK)
                timeList.addView(time)
                Log.d("check ", time.toString())
            }
        }

        // 나의 요청 정보
        val rh = if (myRequest.rhType!!) "-" else "+"
        val blood = BloodType.values().first { it.id == myRequest.bloodType }.bloodType
        val hospital = Hospital.values().first { it.id == myRequest.hospitalId }.hospitalName
        binding.tvUpDate.text = myRequest.registerTime
        binding.tvUpType1.text = "Rh$rh"
        binding.tvUpType2.text = blood
        binding.tvUpType3.text = myRequest.donationType
        binding.tvUpHos.text = hospital
        binding.tvUpHosroom.text = myRequest.wardNum.toString()
        binding.tvUpPatient.text = myRequest.patientName
        binding.tvUpPatientnumber.text = myRequest.patientNum.toString()
        binding.tvUpPnum.text = myRequest.protectorContact
        binding.tvUpStartdate.text = myRequest.startDate
        binding.tvUpEnddate.text = myRequest.endDate

        // 요청 마감 상태인(state=false) 경우 요청 마감 btn invisible, 요청 완료 tv visible
        Log.d("[REQUEST CARD]", "my request state: " + myRequest.state.toString())
        if(myRequest.state == true){
            binding.requestDelete.visibility = VISIBLE
            binding.requestEnd.visibility = INVISIBLE
        }else{
            binding.requestDelete.visibility = INVISIBLE
            binding.requestEnd.visibility = VISIBLE
        }

        // 요청 마감
        binding.requestDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("헌혈을 받으셨나요?")
            builder.setMessage("헌혈 요청 글이 삭제됩니다.")
            val listener = DialogInterface.OnClickListener { dialog, p1 ->
                when (p1) {
                    DialogInterface.BUTTON_POSITIVE -> {

                        //해당 요청 글 삭제 (state = false 설정)
                        MyPageService(this).requestEnd(myRequest.requestId!!) { response ->
                            when (response) {
                                200 -> {
                                    Log.d("[REQUEST END]", "receive response")
                                    val dialog = CardRequestEndCompleteAlert(this)
                                    dialog.callFunction()
                                    dialog.show()
                                    dialog.setOnDismissListener {
                                        myRequest.state = false
                                        finish()
                                        startActivity(intent)
                                    }
                                }
                                400 -> Log.d("[REQUEST END]", "invalid token or no request info")
                            }
                        }
                    }
                    DialogInterface.BUTTON_NEGATIVE -> shortToast("취소")
                }
            }
            builder.setPositiveButton("확인",listener)
            builder.setNegativeButton("취소",listener)
            builder.show()
        }
    }

    companion object{
        var applylist: List<Apply>?=null
    }
}