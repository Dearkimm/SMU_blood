package org.smu.blood.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.smu.blood.databinding.FragmentMainSearchHospitalBinding
import org.smu.blood.ui.base.BaseFragment

class MainSearchHospitalFragment : BaseFragment<FragmentMainSearchHospitalBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainSearchHospitalBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}