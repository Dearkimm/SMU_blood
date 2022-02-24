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
import org.smu.blood.api.MainService
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



        /* 서버 없이 테스트 시 사용
        //요청자 시점
        binding.recRequest.setOnClickListener {
            binding.recRequest.setTextColor(Color.RED)
            binding.recApply.setTextColor(Color.BLACK)
            cardState = 0

            myCardAdapter.setItems(
                listOf(
                    MainRequest(2, true, 2,"전혈",
                        "21.12.15", "21.12.20", 0,
                        "가족 중에 한 분이 혈액암으로 많이 안 좋아요.. B형이신 분 지정헌혈 부탁드려요. 코로나로 헌혈하시는 분들이 많이 줄어서 B형 혈소판이 부족합니다.", "2021.12.15(화) 오후 14:40"),

                    MainRequest(5, true, 4,"혈소판",
                        "21.12.13", "21.12.21", 2,
                        "친한 언니가 급성 심근염으로 중환자실에서 힘든 시간을 보내고 있습니다. Rh+ A형 혈소판을 구하고 있으니 가능하시다면 헌혈을 간곡하게 부탁드립니다.", "2021.12.11(토) 오전 10:31"),
                )
            )

        }
        //신청자 시점
        binding.recApply.setOnClickListener {
            binding.recApply.setTextColor(Color.RED)
            binding.recRequest.setTextColor(Color.BLACK)
            cardState = 1

            myCardAdapter.setItems(
                listOf(
                    //최근꺼에 신청 눌렀을 때,
                    MainRequest(29, true, 2,"혈소판",
                        "21.12.11", "21.12.23", 1,
                        "가족 중에 한 분이 혈액암으로 많이 안 좋아요.. B형이신 분 지정헌혈 부탁드려요. 코로나로 헌혈하시는 분들이 많이 줄어서 B형 혈소판이 부족합니다.", "2021.12.11(토) 오후 20:42"),
                    //오래된 신청
                    MainRequest(54, true, 2,"전혈",
                        "21.10.01", "21.10.15", 2,
                        "어쩌구저쩌구..ㅠ", "2021.10.01(금) 오전 09:20"),
                )
            )
        }
         */

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
            MainService(requireContext()).myApplyList { myApplyList ->
                // 리스트 초기화
                myCardAdapter.setItems(emptyList())

                if(myApplyList!=null){
                    Log.d("[MY APPLY LIST]", "GET LIST")
                    // 내 신청 정보 리스트에 넣기
                    applyList = myApplyList

                    // 서버에서 내가 신청한 요청은 가져오는데 mainRequest 리스트에 안 들어가서 신청 기록 안 보여줌 (수정 필요)
                    for(apply in myApplyList){
                        // get request of my apply
                        MainService(requireContext()).requestOfApply(apply.requestId!!){ request ->
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
        MainService(requireContext()).myRequestList { myRequestList ->
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
                    MainService(requireContext()).applylistOfRequest(request.requestId!!){
                        if(it != null){
                            Log.d("[APPLY LIST OF MY REQUEST]", "GET APPLY LIST")
                            // apply 리스트에 넣기
                            applyList = it

                            for(apply in applyList) Log.d("[APPLY LIST OF MY REQUEST]", "$apply")
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
        lateinit var applyList: List<Apply> // 신청 정보 리스트
        var requestList = mutableListOf<Request>() // 요청 기록 카드에 필요
        lateinit var myRequest: Request // 요청 기록 카드, 신청 기록 카드에 필요
    }
}