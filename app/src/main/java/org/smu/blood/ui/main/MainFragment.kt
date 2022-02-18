package org.smu.blood.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.api.database.MainRequest
import org.smu.blood.databinding.FragmentMainBinding
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.adapter.MainRequestAdapter
import androidx.activity.addCallback
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.common.collect.Multisets.filter
import org.smu.blood.R
import org.smu.blood.api.MainService
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital
import org.smu.blood.util.*
import java.time.LocalDateTime
import java.util.Locale.filter


class MainFragment : BaseFragment<FragmentMainBinding>() {
    private val mainRequestAdapter = MainRequestAdapter()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rcRequestList.layoutManager = LinearLayoutManager(activity)
        binding.rcRequestList.adapter = mainRequestAdapter

        configureMainNavigation()
        initMain()
        addMainRequestInfo()
        configureClickEvent()
    }

    private fun initMain() {
        MainService(requireContext()).mainList {
            // DB에서 가져온 리스트 서버로부터 받기
            if(it!=null){
                Log.d("[BLOOD REQUEST LIST]","GET LIST")
                var requestList = mutableListOf<MainRequest>()
                for(request in it){
                    var mainRequest = MainRequest(request.hospitalId!!, request.rhType!!, request.bloodType!!, request.donationType!!, request.startDate!!, request.endDate!!, request.applicantNum!!, request.story!!, request.registerTime!!)
                    Log.d("[BLOOD REQUEST LIST]", mainRequest.toString())
                    // MainRequest 리스트에 넣기
                    requestList.add(mainRequest)
                }
                mainRequestAdapter.setItems(requestList)
                mainRequestAdapter.unFilteredList = requestList
                mainRequestAdapter.notifyDataSetChanged()
                for(request in requestList) Log.d("[BLOOD REQUEST LIST]", request.toString())
                mainRequestAdapter.filter.filter("")
            }
            else{
                Log.d("[BLOOD REQUEST LIST]","GET LIST FAILURE OR NO LIST")
            }
        }
    }

    private fun configureMainNavigation() {
        binding.btnRequest.setOnClickListener {
            (activity as NavigationActivity).navigateMainToRequest()
        }
        binding.btnMy.setOnClickListener {
            (activity as NavigationActivity).navigateMainToMy()
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            (activity as NavigationActivity).showFinishToast()
        }
    }

    // 서버에 requestInfo 보내기
    private fun addMainRequestInfo() {
        when (MainRequestFragment.requestState) {
            1 -> {
                Log.d(
                    "[REGISTER BLOOD REQUEST] SEND REQUEST INFO",
                    MainRequestFragment.requestInfo.toString()
                )
                MainService(requireContext()).mainRequest(MainRequestFragment.requestInfo) {
                    if (it == true) {
                        Log.d("[REGISTER BLOOD REQUEST]", "SUCCESS")
                    }else{
                        Log.d("[REGISTER BLOOD REQUEST]", "FAILURE")
                    }
                }
                MainRequestFragment.requestState = 0
            }
        }
    }



    private fun configureClickEvent() {
        mainRequestAdapter.setItemClickListener(object : MainRequestAdapter.ItemClickListener {
            override fun onClick(requestInfo: MainRequest) {
                hospitalId = requestInfo.hospitalId
                rhType = requestInfo.rhType
                bloodType = requestInfo.bloodType
                donationType = requestInfo.donationType
                startDate = requestInfo.startDate
                endDate = requestInfo.endDate
                count = requestInfo.count
                content = requestInfo.content
                updatedDate = requestInfo.updatedDate

                (activity as NavigationActivity).navigateMainToRead()
            }
        })

        //필터링
        binding.mainSwitch.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked){
                //필터사용
                mainRequestAdapter.filter.filter(bloodType.toString())
                Log.d("내혈액형만보기", "체크선택")
            }
            else{
                //필터 미사용
                Log.d("내혈액형만보기", "체크해제")
                mainRequestAdapter.filter.filter("")
            }

        }

    }

    companion object {
        var hospitalId = -1
        var rhType = false
        var bloodType = -1
        var donationType = ""
        var startDate = ""
        var endDate = ""
        var count = -1
        var content = ""
        var updatedDate = ""
    }
}