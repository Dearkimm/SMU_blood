package org.smu.blood.ui.main

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import org.smu.blood.databinding.FragmentMainReadBinding
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.MainFragment.Companion.bloodType
import org.smu.blood.ui.main.MainFragment.Companion.content
import org.smu.blood.ui.main.MainFragment.Companion.count
import org.smu.blood.ui.main.MainFragment.Companion.donationType
import org.smu.blood.ui.main.MainFragment.Companion.endDate
import org.smu.blood.ui.main.MainFragment.Companion.hospitalId
import org.smu.blood.ui.main.MainFragment.Companion.rhType
import org.smu.blood.ui.main.MainFragment.Companion.startDate
import org.smu.blood.ui.main.adapter.MainRequestAdapter
import org.smu.blood.ui.map.MapApplicationActivity
import org.smu.blood.ui.map.MapApplicationCompleteAlert
import org.smu.blood.ui.map.MapCheckConditionAlert
import org.smu.blood.ui.my.MyRequestFragment

class MainReadFragment : BaseFragment<FragmentMainReadBinding>() {
    private val mainRequestAdapter = MainRequestAdapter()
    var checkState = false

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainReadBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureMainReadNavigation()
        initMainRead()
        readDialog()
    }

    private fun configureMainReadNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback {
            (activity as NavigationActivity).popMainRead()
        }
    }

    private fun readDialog(){
        binding.mainreadButton.setOnClickListener {
            val dlg = MapCheckConditionAlert(requireContext())

            dlg.callFunction()
            dlg.show()
            dlg.setOnDismissListener {
                checkState = dlg.returnState()
                if(checkState){ // true일때 건너뛰기
                    val intent = Intent(context, MapApplicationActivity()::class.java)
                    activity?.startActivityForResult(intent, 101)
                }
                else{ //false 일때 전자문진
                    var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bloodinfo.net/emi2/login.do?_ga=2.29800319.1190218835.1637677364-178623010.1637677364"))
                    startActivity(intent)
                }
            }
        }
    }

    private fun initMainRead() {
        val rh = if (rhType) "+" else "-"
        var blood = ""
        var hospital = ""
        var donationtype = ""
        BloodType.values().forEach {
            if (bloodType == it.id) blood = it.bloodType

        }
        Hospital.values().forEach {
                if (hospitalId == it.id) hospital = it.hospitalName
            }
        binding.apply {
            //binding.atvType.text
            atvType.text = "RH${rh} $blood"
            atvHos.text = hospital
            atvType2.text = "Rh${rh} ${blood}형 ${donationType}"
            atvTime.text = "요청 시작일 $startDate ~ 마감일 $endDate"
            atvNum.text = "신청 ${count}명"
            atvCon.text = content
        }
    }
}
