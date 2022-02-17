package org.smu.blood.ui.my

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.util.CollectionUtils.listOf
import org.smu.blood.api.database.MainRequest
import org.smu.blood.databinding.FragmentMyRequestBinding
import org.smu.blood.ui.NavigationActivity
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
        /*
        myCardAdapter.setItems(

            listOf(
                MainRequest(2, true, 2,"전혈",
                    "21.12.15", "21.12.20", 0,
                    "가족 중에 한 분이 혈액암으로 많이 안 좋아요.. B형이신 분 지정헌혈 부탁드려요. 코로나로 헌혈하시는 분들이 많이 줄어서 B형 혈소판이 부족합니다.", "2021.12.15(화) 오후 16:35"),

                MainRequest(5, true, 2,"전혈",
                    "21.12.09", "21.12.17", 2,
                    "친한 언니가 급성 심근염으로 중환자실에서 힘든 시간을 보내고 있습니다. Rh+ A형 혈소판을 구하고 있으니 가능하시다면 헌혈을 간곡하게 부탁드립니다.", "2021.12.11(토) 오전 10:31"),
            )


        )

         */

        //요청자 시점
        binding.recRequest.setOnClickListener {
            binding.recRequest.setTextColor(Color.RED)
            binding.recApply.setTextColor(Color.BLACK)
            cardState = 0
            /*
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

             */

        }
        //신청자 시점
        binding.recApply.setOnClickListener {
            binding.recApply.setTextColor(Color.RED)
            binding.recRequest.setTextColor(Color.BLACK)
            cardState = 1
            /*
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

             */
        }

        addMyCardInfo()
        configureMyRequestNavigation()
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

        /*
        // 더미데이터
        myCardAdapter.setItems(
            listOf(
                MainRequest(2, true, 2,"전혈",
                    "21.12.15", "21.12.20", 0,
                    "가족 중에 한 분이 혈액암으로 많이 안 좋아요.. B형이신 분 지정헌혈 부탁드려요. 코로나로 헌혈하시는 분들이 많이 줄어서 B형 혈소판이 부족합니다.", "2021.12.15(화) 오후 16:37"),

                MainRequest(5, true, 2,"전혈",
                    "21.12.09", "21.12.25", 2,
                    "친한 언니가 급성 심근염으로 중환자실에서 힘든 시간을 보내고 있습니다. Rh+ A형 혈소판을 구하고 있으니 가능하시다면 헌혈을 간곡하게 부탁드립니다.", "2021.12.11(토) 오전 10:31"),
            )
        )

         */
    }

    private fun configureMyRequestNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback {
            (activity as NavigationActivity).navigateMyToRequest()
        }
    }

    private fun configureClickEvent() {
        myCardAdapter.setItemClickListener(object : MainRequestAdapter.ItemClickListener {
            override fun onClick(requestInfo: MainRequest) {
                // 지정 헌혈 요청 내용 정보 넣기
                request = requestInfo

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
        lateinit var request: MainRequest
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