package org.smu.blood.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.smu.blood.NavigationActivity
import org.smu.blood.api.database.MyCardItem
import org.smu.blood.databinding.FragmentCardApplyListBinding
import org.smu.blood.ui.base.BaseFragment

class MyCardApplyListFragment : BaseFragment<FragmentCardApplyListBinding>() {
    private val myCardListAdapter = MyCardAdapter()
    private val request = mutableListOf<MyCardItem>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentCardApplyListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rcRequestList.layoutManager = LinearLayoutManager(activity)
        binding.rcRequestList.adapter = myCardListAdapter

        addMainRequestInfo()
        configureClickEvent()
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
        myCardListAdapter.setItems(
            MyCardItem(5, true, 1, "21.10.01", "21.10.15", 3, "하이")
        )

    }

    private fun configureClickEvent() {
        myCardListAdapter.setItemClickListener(object : MyCardAdapter.ItemClickListener {
            override fun onClick(requestInfo: MyCardItem) {
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