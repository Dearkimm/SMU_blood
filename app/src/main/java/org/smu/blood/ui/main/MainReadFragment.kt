package org.smu.blood.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.smu.blood.databinding.FragmentMainReadBinding
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.MainFragment.Companion.bloodType
import org.smu.blood.ui.main.MainFragment.Companion.content
import org.smu.blood.ui.main.MainFragment.Companion.count
import org.smu.blood.ui.main.MainFragment.Companion.endDate
import org.smu.blood.ui.main.MainFragment.Companion.hospitalId
import org.smu.blood.ui.main.MainFragment.Companion.rhType
import org.smu.blood.ui.main.MainFragment.Companion.startDate

class MainReadFragment : BaseFragment<FragmentMainReadBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainReadBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMainRead()
    }

    private fun initMainRead() {
        val rh = if (rhType) "+" else "-"
        var blood = ""
        var hospital = ""
        BloodType.values().forEach {
            if (bloodType == it.id) blood = it.bloodType

        }
        Hospital.values().forEach {
                if (hospitalId == it.id) hospital = it.hospitalName
            }
        binding.apply {
            atvType.text = "RH${rh} $blood"
            atvHos.text = hospital
            atvType2.text = "Rh${rh} ${blood}형 혈소판"
            atvTime.text = "요청 시작일 $startDate ~ 마감일 $endDate"
            atvNum.text = "신청 ${count}명"
            atvCon.text = content
        }
    }
}