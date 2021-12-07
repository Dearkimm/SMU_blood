package org.smu.blood.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.databinding.FragmentMainSearchHospitalBinding
import org.smu.blood.model.Hospital
import org.smu.blood.ui.base.BaseFragment

class MainSearchHospitalFragment : BaseFragment<FragmentMainSearchHospitalBinding>(), View.OnClickListener {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainSearchHospitalBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHospitalButton()
    }

    private fun setHospitalButton() {
        // ㄱ
        binding.hosRBt1.setOnClickListener(this)
        binding.hosRBt2.setOnClickListener(this)
        binding.hosRBt3.setOnClickListener(this)
        binding.hosRBt4.setOnClickListener(this)
        binding.hosRBt5.setOnClickListener(this)
        binding.hosRBt6.setOnClickListener(this)
        binding.hosRBt7.setOnClickListener(this)
        binding.hosRBt8.setOnClickListener(this)
        binding.hosRBt9.setOnClickListener(this)
        binding.hosRBt10.setOnClickListener(this)
        binding.hosRBt11.setOnClickListener(this)
        binding.hosRBt12.setOnClickListener(this)
        binding.hosRBt13.setOnClickListener(this)
        binding.hosRBt14.setOnClickListener(this)
        binding.hosRBt15.setOnClickListener(this)
        binding.hosRBt16.setOnClickListener(this)
        binding.hosRBt17.setOnClickListener(this)
        binding.hosRBt18.setOnClickListener(this)
        binding.hosRBt19.setOnClickListener(this)
        binding.hosRBt20.setOnClickListener(this)
        binding.hosRBt21.setOnClickListener(this)

        // ㄴ
        binding.hosSBt1.setOnClickListener(this)

        // ㅁ
        binding.hosABt1.setOnClickListener(this)
        binding.hosABt2.setOnClickListener(this)

        // ㅂ
        binding.hosQBt1.setOnClickListener(this)
        binding.hosQBt2.setOnClickListener(this)
        binding.hosQBt3.setOnClickListener(this)

        // ㅅ
        binding.hosTBt1.setOnClickListener(this)
        binding.hosTBt2.setOnClickListener(this)
        binding.hosTBt3.setOnClickListener(this)
        binding.hosTBt4.setOnClickListener(this)
        binding.hosTBt5.setOnClickListener(this)
        binding.hosTBt6.setOnClickListener(this)
        binding.hosTBt7.setOnClickListener(this)
        binding.hosTBt8.setOnClickListener(this)
        binding.hosTBt9.setOnClickListener(this)
        binding.hosTBt10.setOnClickListener(this)

        // ㅇ
        binding.hosDBt1.setOnClickListener(this)
        binding.hosDBt2.setOnClickListener(this)
        binding.hosDBt3.setOnClickListener(this)
        binding.hosDBt4.setOnClickListener(this)
        binding.hosDBt5.setOnClickListener(this)
        binding.hosDBt6.setOnClickListener(this)
        binding.hosDBt7.setOnClickListener(this)
        binding.hosDBt8.setOnClickListener(this)
        binding.hosDBt9.setOnClickListener(this)
        binding.hosDBt10.setOnClickListener(this)
        binding.hosDBt11.setOnClickListener(this)
        binding.hosDBt12.setOnClickListener(this)
        binding.hosDBt13.setOnClickListener(this)
        binding.hosDBt14.setOnClickListener(this)

        // ㅈ
        binding.hosWBt1.setOnClickListener(this)

        // ㅊ
        binding.hosCBt1.setOnClickListener(this)
        binding.hosCBt2.setOnClickListener(this)
        binding.hosCBt3.setOnClickListener(this)

        // ㅎ
        binding.hosGBt1.setOnClickListener(this)
        binding.hosGBt2.setOnClickListener(this)
        binding.hosGBt3.setOnClickListener(this)
        binding.hosGBt4.setOnClickListener(this)
        binding.hosGBt5.setOnClickListener(this)
        binding.hosGBt6.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        Hospital.values().forEach {
            if (v?.tag == it.id.toString()) {
                hospitalName = it.hospitalName
                (activity as NavigationActivity).popMainSearchHospital()
            }
        }
    }

    companion object {
        var hospitalName = ""
    }
}