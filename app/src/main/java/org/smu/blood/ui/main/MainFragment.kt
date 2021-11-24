package org.smu.blood.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.smu.blood.NavigationActivity
import org.smu.blood.databinding.FragmentMainBinding
import org.smu.blood.ui.base.BaseFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureMainNavigation()
    }

    private fun configureMainNavigation() {
        binding.btnRequest.setOnClickListener {
            (activity as NavigationActivity).navigateMainToRequest()
        }
    }
}