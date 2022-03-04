package org.smu.blood.ui.main

import android.graphics.Color
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.R
import org.smu.blood.api.database.Request
import org.smu.blood.databinding.FragmentMainRequestBinding
import org.smu.blood.model.Hospital
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.MainSearchHospitalFragment.Companion.hospitalName
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainRequestFragment : BaseFragment<FragmentMainRequestBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainRequestBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMainRequest()
        configureClickEvent()
        configureRequestNavigation()
    }

    private fun initMainRequest() {
        when (hospitalName) {
            "" -> binding.imgbHos.text = "병원 찾기"
            else -> binding.imgbHos.text = hospitalName
        }
    }

    private fun configureRequestNavigation() {
        binding.btnRegister.setOnClickListener {
            if (binding.metHnum.text.isNullOrBlank() || binding.metGnum.text.isNullOrBlank() || binding.metPname.text.isNullOrBlank()
                || binding.metPnum.text.isNullOrBlank() || binding.metStart.text.isNullOrBlank() || binding.metEnd.text.isNullOrBlank()) {

            Toast.makeText(activity, "필수 항목을 채워주세요", Toast.LENGTH_SHORT).show()}
            else {
                val dlg = MainRequestAlert(requireContext())
                dlg.callFunction()
                dlg.show()
                requestState = 1


                // request info
                requestInfo.hospitalId = Hospital.values().first { it.hospitalName == hospitalName }.id
                Log.d("[REGISTER BLOOD REQUEST]", "$hospitalName : ${requestInfo.hospitalId}")
                requestInfo.wardNum = binding.metHnum.text.toString().toInt()
                requestInfo.patientName = binding.metPname.text.toString()
                requestInfo.patientNum = binding.metPnum.text.toString().toInt()
                requestInfo.protectorContact = binding.metGnum.text.toString()
                requestInfo.startDate = binding.metStart.text.toString()
                requestInfo.endDate = binding.metEnd.text.toString()
                requestInfo.registerTime = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + " " +
                        LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"))
                requestInfo.story = binding.metStory.text.toString()

                (activity as NavigationActivity).popMainRequest()

            }
        }

        binding.imgbHos.setOnClickListener {
            (activity as NavigationActivity).navigateRequestToSearchHospital()
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            (activity as NavigationActivity).popMainRequest()
        }
    }

    private fun configureClickEvent() {
        //혈액형 눌렀을때 혈액형: 1 -> A, 2 -> B, 3-> O, 4-> AB
        binding.type2A.setOnClickListener {
            requestInfo.bloodType = 1
            binding.type2A.setBackgroundResource(R.drawable.bg_btn_red_5dp)
            binding.type2A.setTextColor(Color.WHITE)
            binding.type2B.setTextColor(Color.BLACK)
            binding.type2Ab.setTextColor(Color.BLACK)
            binding.type2O.setTextColor(Color.BLACK)
            binding.type2B.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2O.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2Ab.setBackgroundResource(R.drawable.bg_btn_type)
        }
        binding.type2B.setOnClickListener {
            requestInfo.bloodType = 2
            binding.type2B.setBackgroundResource(R.drawable.bg_btn_red_5dp)
            binding.type2B.setTextColor(Color.WHITE)
            binding.type2A.setTextColor(Color.BLACK)
            binding.type2Ab.setTextColor(Color.BLACK)
            binding.type2O.setTextColor(Color.BLACK)
            binding.type2A.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2O.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2Ab.setBackgroundResource(R.drawable.bg_btn_type)
        }
        binding.type2O.setOnClickListener{
            requestInfo.bloodType = 3
            binding.type2O.setBackgroundResource(R.drawable.bg_btn_red_5dp)
            binding.type2O.setTextColor(Color.WHITE)
            binding.type2B.setTextColor(Color.BLACK)
            binding.type2Ab.setTextColor(Color.BLACK)
            binding.type2A.setTextColor(Color.BLACK)
            binding.type2B.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2A.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2Ab.setBackgroundResource(R.drawable.bg_btn_type)
        }
        binding.type2Ab.setOnClickListener {
            requestInfo.bloodType = 4
            binding.type2Ab.setBackgroundResource(R.drawable.bg_btn_red_5dp)
            binding.type2Ab.setTextColor(Color.WHITE)
            binding.type2B.setTextColor(Color.BLACK)
            binding.type2A.setTextColor(Color.BLACK)
            binding.type2O.setTextColor(Color.BLACK)
            binding.type2B.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2O.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2A.setBackgroundResource(R.drawable.bg_btn_type)
        }
        // Rh- 여부
        binding.mCheckbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                requestInfo.rhType = true
                binding.mcheckboxTv.setTextColor(Color.RED)
            }
            else {
                requestInfo.rhType = false
                binding.mcheckboxTv.setTextColor(Color.BLACK)
            }
        }

        //라디오버튼 텍스트 컬러 (헌혈 종류)
        binding.radio1.setOnClickListener {
            requestInfo.donationType = binding.radio1.text.toString()
            binding.radio1.setTextColor(Color.RED)
            binding.radio2.setTextColor(Color.BLACK)
            binding.radio3.setTextColor(Color.BLACK)
            binding.radio4.setTextColor(Color.BLACK)
            binding.radio5.setTextColor(Color.BLACK)
        }
        binding.radio2.setOnClickListener {
            requestInfo.donationType = binding.radio2.text.toString()
            binding.radio2.setTextColor(Color.RED)
            binding.radio1.setTextColor(Color.BLACK)
            binding.radio3.setTextColor(Color.BLACK)
            binding.radio4.setTextColor(Color.BLACK)
            binding.radio5.setTextColor(Color.BLACK)
        }
        binding.radio3.setOnClickListener {
            requestInfo.donationType = binding.radio3.text.toString()
            binding.radio3.setTextColor(Color.RED)
            binding.radio2.setTextColor(Color.BLACK)
            binding.radio1.setTextColor(Color.BLACK)
            binding.radio4.setTextColor(Color.BLACK)
            binding.radio5.setTextColor(Color.BLACK)
        }
        binding.radio4.setOnClickListener {
            requestInfo.donationType = binding.radio4.text.toString()
            binding.radio4.setTextColor(Color.RED)
            binding.radio2.setTextColor(Color.BLACK)
            binding.radio3.setTextColor(Color.BLACK)
            binding.radio1.setTextColor(Color.BLACK)
            binding.radio5.setTextColor(Color.BLACK)
        }
        binding.radio5.setOnClickListener {
            requestInfo.donationType = binding.radio5.text.toString()
            binding.radio5.setTextColor(Color.RED)
            binding.radio2.setTextColor(Color.BLACK)
            binding.radio3.setTextColor(Color.BLACK)
            binding.radio4.setTextColor(Color.BLACK)
            binding.radio1.setTextColor(Color.BLACK)
        }
        binding.metGnum.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }

    companion object {
        var requestState = 0
        var requestInfo= Request()
    }
}