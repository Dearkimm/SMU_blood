package org.smu.blood.ui.main

import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.smu.blood.NavigationActivity
import org.smu.blood.api.ServiceCreator
import org.smu.blood.api.database.MainRequest
import org.smu.blood.databinding.FragmentMainBinding
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.adapter.MainRequestAdapter

class MainFragment : BaseFragment<FragmentMainBinding>() {
    private val mainRequestAdapter = MainRequestAdapter()
    private val request = mutableListOf<MainRequest>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rcRequestList.layoutManager = LinearLayoutManager(activity)
        binding.rcRequestList.adapter = mainRequestAdapter

        configureMainNavigation()
        addMainRequestInfo()
        configureClickEvent()
    }

    private fun configureMainNavigation() {
        binding.btnRequest.setOnClickListener {
            (activity as NavigationActivity).navigateMainToRequest()
        }
    }

    private fun addMainRequestInfo() {
        // 서버 통신 코드
//        val call: Call<ResponseRequest> = ServiceCreator.bumService.getRequest(
//        call.enqueueUtil(
//            onSuccess = {
//                mainRequestAdapter.setItems()
//            }
//        )

        // 더미데이터
        mainRequestAdapter.setItems(
            listOf(
                MainRequest(1, true, 1, "21.10.01", "21.10.15", 3, "하이"),
                MainRequest(2, false, 2, "21.10.23", "21.10.31", 0, "어쩔티비"),
                MainRequest(3, true, 3, "21.10.23", "21.10.31", 5, "저쩔냉장고"),
                MainRequest(4, true, 4, "21.10.01", "21.10.31", 0, "명성족발"),
            )
        )
    }

    private fun configureClickEvent() {
        mainRequestAdapter.setItemClickListener(object : MainRequestAdapter.ItemClickListener {
            override fun onClick(requestInfo: MainRequest) {
                hospitalId = requestInfo.hospitalId
                rhType = requestInfo.rhType
                bloodType = requestInfo.bloodType
                startDate = requestInfo.startDate
                endDate = requestInfo.endDate
                count = requestInfo.count
                content = requestInfo.content

                (activity as NavigationActivity).navigateMainToRead()
            }
        })
    }

    companion object {
        var hospitalId = -1
        var rhType = false
        var bloodType = -1
        var startDate = ""
        var endDate = ""
        var count = -1
        var content = ""
    }
}