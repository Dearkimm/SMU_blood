package org.smu.blood.ui.my

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import org.smu.blood.api.MyPageService
import org.smu.blood.api.database.Apply
import org.smu.blood.api.database.MainRequest
import org.smu.blood.api.database.Request
import org.smu.blood.databinding.FragmentMyRequestBinding
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.adapter.MainRequestAdapter
import org.smu.blood.ui.my.Card.CardApplyActivity
import org.smu.blood.ui.my.Card.CardRequestActivity

class MyRequestFragment : BaseFragment<FragmentMyRequestBinding>() {
    //카드 기록 상태(0->요청, 1->등록)
    var cardState = 0

    //어댑터
    private val myCardAdapter = MainRequestAdapter()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMyRequestBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.myCardList.layoutManager = LinearLayoutManager(activity)
        binding.myCardList.adapter = myCardAdapter

        getMyRequest() // 헌혈 기록 카드 진입 시 요청 리스트 보여주기
        addMyCardInfo()
        configureMyRequestNavigation()
        configureClickEvent()
    }

    private fun addMyCardInfo() {
        //요청자 시점
        binding.recRequest.setOnClickListener {
            binding.recRequest.setTextColor(Color.RED)
            binding.recApply.setTextColor(Color.BLACK)
            cardState = 0

            getMyRequest()
        }

        //신청자 시점
        binding.recApply.setOnClickListener {
            binding.recApply.setTextColor(Color.RED)
            binding.recRequest.setTextColor(Color.BLACK)
            cardState = 1

            myCardAdapter.setItems(emptyList())
            // DB에서 내 신청 리스트 가져오기
            MyPageService(requireContext()).myApplyList { myApplyList ->
                // 리스트 초기화
                myCardAdapter.setItems(emptyList())

                if(myApplyList!=null){
                    Log.d("[MY APPLY LIST]", "GET LIST")
                    // 내 신청 정보 리스트에 넣기
                    applyList = myApplyList

                    // 서버에서 내가 신청한 요청은 가져오는데 mainRequest 리스트에 안 들어가서 신청 기록 안 보여줌 (수정 필요)
                    for(apply in myApplyList){
                        // get request of my apply
                        MyPageService(requireContext()).requestOfApply(apply.requestId!!){ request ->
                            if(request!=null){
                                // Request 객체를 requestList에 넣기
                                requestList.add(request)
                                Log.d("[REQUEST OF MY APPLY] GET REQUEST", "$request")

                                // 내가 신청한 지정 헌혈 요청 정보 MainRequest 리스트에 넣기
                                val mainRequest = MainRequest(request.hospitalId!!, request.requestId!!, request.rhType!!, request.bloodType!!, request.donationType!!, request.startDate!!, request.endDate!!, request.applicantNum!!, request.story!!, request.registerTime!!)
                                Log.d("[MY REQUEST LIST]", mainRequest.toString())

                                // 각 request 정보 MainRequest 리스트에 넣기 - setItems로 계속 안 돼서 addItems 사용
                                myCardAdapter.addItems(mutableListOf(mainRequest))
                            }
                        }
                    }
                }else{
                    Log.d("[REQUEST OF MY APPLY]", "GET APPLY LIST FAILURE")
                }
            }

        }
    }

    private fun configureMyRequestNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback {
            (activity as NavigationActivity).navigateMyToRequest()
        }
    }

    private fun configureClickEvent() {
        myCardAdapter.setItemClickListener(object : MainRequestAdapter.ItemClickListener {
            override fun onClick(requestInfo: MainRequest) {
                // Request 객체 전체 내용 필요하기 때문에 Request 객체 가져옴
                myRequest  = requestList.first { it.requestId ==  requestInfo.requestId }

                //(activity as NavigationActivity).navigateMainToRead()
                val intent: Intent
                if(cardState == 0) intent = Intent(context, CardRequestActivity::class.java)
                else intent = Intent(context, CardApplyActivity::class.java)

                startActivity(intent)

            }
        })
    }

    private fun getMyRequest(){
        // DB에서 내 요청 리스트 가져오기
        MyPageService(requireContext()).myRequestList { myRequestList ->
            if(myRequestList!=null){
                Log.d("[MY REQUEST LIST]","GET LIST")
                val mainRequestList = mutableListOf<MainRequest>()
                myRequestList.forEach { requestList.add(it) }

                for(request in myRequestList){
                    val mainRequest = MainRequest(request.hospitalId!!, request.requestId!!, request.rhType!!, request.bloodType!!, request.donationType!!, request.startDate!!, request.endDate!!, request.applicantNum!!, request.story!!, request.registerTime!!)
                    Log.d("[MY REQUEST LIST]", mainRequest.toString())
                    // 각 request 정보 MainRequest 리스트에 넣기
                    mainRequestList.add(mainRequest)

                    // get apply list of my request
                    MyPageService(requireContext()).applylistOfRequest(request.requestId!!){
                        if(it != null){
                            Log.d("[APPLY LIST OF MY REQUEST]", "GET APPLY LIST")
                            // apply 리스트에 넣기
                            CardRequestActivity.applylist = it

                            for(apply in CardRequestActivity.applylist!!) Log.d("[APPLY LIST OF MY REQUEST]", "$apply")
                        }
                    }
                }
                myCardAdapter.setItems(mainRequestList)
            }else{
                Log.d("[MY REQUEST LIST]", "GET REQUEST LIST FAILURE")
            }
        }
    }


    companion object {
        var applyList: List<Apply>? = null // 신청 정보 리스트
        var requestList = mutableListOf<Request>() // 요청 기록 카드에 필요
        lateinit var myRequest: Request // 요청 기록 카드, 신청 기록 카드에 필요
    }
}