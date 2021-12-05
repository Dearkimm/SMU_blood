package org.smu.blood.ui.my

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.smu.blood.api.database.MainRequest
import org.smu.blood.databinding.FragmentMyRequestBinding
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.adapter.MainRequestAdapter
import org.smu.blood.ui.my.Card.CardApplyActivity
import org.smu.blood.ui.my.Card.CardRequestActivity
import org.smu.blood.util.dateTimeString
import java.time.LocalDateTime

class MyRequestFragment : BaseFragment<FragmentMyRequestBinding>() {
    //카드 기록 상태(0->요청, 1->등록)
    var cardState = 0
    lateinit var intent : Intent
    //어댑터
    private val myCardAdapter = MainRequestAdapter()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMyRequestBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.myCardList.layoutManager = LinearLayoutManager(activity)
        binding.myCardList.adapter = myCardAdapter

        //누르면 색 변하게
        binding.recRequest.setOnClickListener {
            binding.recRequest.setTextColor(Color.RED)
            binding.recApply.setTextColor(Color.BLACK)
            cardState = 0
        }

        binding.recApply.setOnClickListener {
            binding.recApply.setTextColor(Color.RED)
            binding.recRequest.setTextColor(Color.BLACK)
            cardState = 1
        }

        addMyCardInfo()
        configureClickEvent()
    }

    private fun addMyCardInfo() {
        // 서버 통신 코드
//        val call: Call<ResponseRequest> = ServiceCreator.bumService.getRequest(
//        call.enqueueUtil(
//            onSuccess = {
//                myCardAdapter.setItems()
//            }
//        )

        // 더미데이터
        myCardAdapter.setItems(
            listOf(
                MainRequest(5, true, 1, "21.10.01", "21.10.15", 3, "하이", LocalDateTime.now().dateTimeString),
                MainRequest(32, false, 2, "21.10.23", "21.10.31", 0, "어쩔티비", LocalDateTime.now().dateTimeString),
                MainRequest(17, true, 3, "21.10.23", "21.10.31", 5, "저쩔냉장고", LocalDateTime.now().dateTimeString),
                MainRequest(54, true, 4, "21.10.01", "21.10.31", 0, "명성족발", LocalDateTime.now().dateTimeString),
            )
        )
    }

    private fun configureClickEvent() {
        myCardAdapter.setItemClickListener(object : MainRequestAdapter.ItemClickListener {
            override fun onClick(requestInfo: MainRequest) {
                hospitalId = requestInfo.hospitalId
                rhType = requestInfo.rhType
                bloodType = requestInfo.bloodType
                startDate = requestInfo.startDate
                endDate = requestInfo.endDate
                count = requestInfo.count
                content = requestInfo.content
                updatedDate = requestInfo.updatedDate

                //(activity as NavigationActivity).navigateMainToRead()
                if(cardState == 0){ //요청 기록 카드
                    intent = Intent(context, CardRequestActivity::class.java)
                } else { //신청 기록 카드
                    intent = Intent(context, CardApplyActivity::class.java)
                }
                startActivity(intent)

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
        var updatedDate = ""
    }
}