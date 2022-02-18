package org.smu.blood.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import org.smu.blood.databinding.FragmentMainReadBinding
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.MainFragment.Companion.request
import org.smu.blood.ui.main.adapter.MainRequestAdapter
import org.smu.blood.ui.map.MapApplicationActivity
import org.smu.blood.ui.map.MapCheckConditionAlert


class MainReadFragment : BaseFragment<FragmentMainReadBinding>() {
    private val mainRequestAdapter = MainRequestAdapter()
    var checkState = false

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainReadBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureMainReadNavigation()
        readDialog()
    }

    private fun configureMainReadNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback {
            (activity as NavigationActivity).popMainRead()
        }
    }

    private fun readDialog(){
        // show request info
        val rh = if (request.rhType) "-" else "+"
        val blood = BloodType.values().first { it.id == request.bloodType }.bloodType
        val hospital = Hospital.values().first { it.id == request.hospitalId }.hospitalName
        binding.atvType.text = "RH${rh} ${blood}"
        binding.atvHos.text = hospital
        binding.atvType2.text = "Rh${rh}"
        binding.atvType3.text = "${blood}형"
        binding.atvType4.text = request.donationType
        binding.atvStartdate.text = request.startDate
        binding.atvEnddate.text = request.endDate
        binding.atvNum.text = request.count.toString()
        binding.atvCon.text = request.content

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
}
