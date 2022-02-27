package org.smu.blood.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.api.database.MainRequest
import org.smu.blood.databinding.FragmentMainBinding
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.adapter.MainRequestAdapter
import androidx.activity.addCallback
import com.google.android.gms.common.util.CollectionUtils.listOf
import org.smu.blood.R
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital
import org.smu.blood.ui.board.BoardEditActivity
import org.smu.blood.util.*
import java.time.LocalDateTime


class MainFragment : BaseFragment<FragmentMainBinding>() {
    private val mainRequestAdapter = MainRequestAdapter()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rcRequestList.layoutManager = LinearLayoutManager(activity)
        binding.rcRequestList.adapter = mainRequestAdapter

        configureMainNavigation()
        initMain()
        //addMainRequestInfo()
        configureClickEvent()
        binding.mainSort.setOnClickListener{
            var popupMenu = PopupMenu(context,it)
            popupMenu.menuInflater.inflate(R.menu.main_sort_option,popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.option_menu1->{ //최신순
                        Log.d("메뉴1","클릭")
                        return@setOnMenuItemClickListener true
                    }
                    R.id.option_menu2->{ //마감 임박 순
                        Log.d("메뉴2","클릭")
                        return@setOnMenuItemClickListener true
                    }
                    R.id.option_menu3->{ //신청자 적은 순
                        Log.d("메뉴3","클릭")
                        return@setOnMenuItemClickListener true
                    }else-> return@setOnMenuItemClickListener false
                }
            }
        }
    }

    private fun initMain() {
        when (MainRequestFragment.requestState) {
            1 -> mainRequestAdapter.setItems(
                listOf(
                    MainRequest(2, true, 2,"전혈",
                        "21.12.15", "21.12.20", 0,
                        "가족 중에 한 분이 혈액암으로 많이 안 좋아요.. B형이신 분 지정헌혈 부탁드려요. 코로나로 헌혈하시는 분들이 많이 줄어서 B형 혈소판이 부족합니다.", "2021.12.15(화) 오후 16:"),
                    MainRequest(29, true, 2,"혈소판",
                        "21.12.12", "21.12.23", 0,
                        "가족 중에 한 분이 혈액암으로 많이 안 좋아요.. B형이신 분 지정헌혈 부탁드려요. 코로나로 헌혈하시는 분들이 많이 줄어서 B형 혈소판이 부족합니다.", "2021.12.11(토) 오후 20:42"),
                    MainRequest(32, true, 4,"혈소판",
                        "21.12.11", "21.12.19", 2,
                        "친한 언니가 급성 심근염으로 중환자실에서 힘든 시간을 보내고 있습니다. Rh+ A형 혈소판을 구하고 있으니 가능하시다면 헌혈을 간곡하게 부탁드립니다.", "2021.12.11(토) 오전 10:31"),
                    MainRequest(17, false, 3,"전혈",
                        "21.12.08", "21.12.15", 1,
                        "제 동생이 어제 사고를 당해서 수술을 했는데 예상보다 많은 출혈이 있었습니다.. 그런데 Rh- O형이라 혈액 구하기가 너무 어려운 상태입니다. 혈액형이 Rh- O형이신 분은 가까운 헌혈의 집에 가셔서 지정 헌혈을 해주시면 감사하겠습니다. 헌혈해주신다면 혈액이 해당 병원으로 바로 수송되어 환자에게 투여됩니다. 꼭 좀 부탁드려요." , "2021.12.08(수) 오전 07:09"),
                    MainRequest(23, true, 1,"혈소판",
                        "21.12.07", "21.12.15", 3,
                        "결핵성 뇌수막염에 걸리신 어머니께서 항상 혈소판 수치가 낮게 나와 매일매일 수혈을 받으시면서 결핵 치료를 겨우 이어나가고 있었는데 피가 많이 부족해서 오늘 처음으로 수혈을 받지 못하셨습니다. 부디 좋은 분들이 나타나주셔서 수혈을 받으면서 결핵 치료를 받을 수 있게 도와주셨으면 감사하겠습니다. 부탁드립니다.. Rh+ AB형 혈소판 지정헌혈로 꼭 부탁드립니다." ,"2021.12.07(화) 오전 11:15"),
                    MainRequest(54, true, 2,"전혈",
                        "21.12.04", "21.12.13", 2,
                        "할아버지께서 급하게 수혈이 필요한 상황인데 저는 타지에서 근무 중이라 직접 지정 헌혈을 할 수 없게 되어 간절한 마음을 담아 지정 헌혈 요청을 해봅니다.", "2021.12.04(토) 오후 21:48"),

                    )
            )

            else -> mainRequestAdapter.setItems(
                listOf(
                   MainRequest(29, true, 2,"혈소판",
                        "21.12.12", "21.12.23", 0,
                        "가족 중에 한 분이 혈액암으로 많이 안 좋아요.. B형이신 분 지정헌혈 부탁드려요. 코로나로 헌혈하시는 분들이 많이 줄어서 B형 혈소판이 부족합니다.", "2021.12.11(토) 오후 20:42"),
                    MainRequest(32, true, 4,"혈소판",
                        "21.12.11", "21.12.19", 2,
                        "친한 언니가 급성 심근염으로 중환자실에서 힘든 시간을 보내고 있습니다. Rh+ A형 혈소판을 구하고 있으니 가능하시다면 헌혈을 간곡하게 부탁드립니다.", "2021.12.11(토) 오전 10:31"),
                    MainRequest(17, false, 3,"전혈",
                        "21.12.08", "21.12.15", 1,
                        "제 동생이 어제 사고를 당해서 수술을 했는데 예상보다 많은 출혈이 있었습니다.. 그런데 Rh- O형이라 혈액 구하기가 너무 어려운 상태입니다. 혈액형이 Rh- O형이신 분은 가까운 헌혈의 집에 가셔서 지정 헌혈을 해주시면 감사하겠습니다. 헌혈해주신다면 혈액이 해당 병원으로 바로 수송되어 환자에게 투여됩니다. 꼭 좀 부탁드려요." , "2021.12.08(수) 오전 07:09"),
                    MainRequest(23, true, 1,"혈소판",
                        "21.12.07", "21.12.15", 3,
                        "결핵성 뇌수막염에 걸리신 어머니께서 항상 혈소판 수치가 낮게 나와 매일매일 수혈을 받으시면서 결핵 치료를 겨우 이어나가고 있었는데 피가 많이 부족해서 오늘 처음으로 수혈을 받지 못하셨습니다. 부디 좋은 분들이 나타나주셔서 수혈을 받으면서 결핵 치료를 받을 수 있게 도와주셨으면 감사하겠습니다. 부탁드립니다.. Rh+ AB형 혈소판 지정헌혈로 꼭 부탁드립니다." ,"2021.12.07(화) 오전 11:15"),
                    MainRequest(54, true, 2,"전혈",
                        "21.12.04", "21.12.13", 2,
                        "할아버지께서 급하게 수혈이 필요한 상황인데 저는 타지에서 근무 중이라 직접 지정 헌혈을 할 수 없게 되어 간절한 마음을 담아 지정 헌혈 요청을 해봅니다.", "2021.12.04(토) 오후 21:48"),

                    )
            )

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

                //신청하고 나서 count: 1로 바꾸야 함
               MainRequest(29, true, 2,"혈소판",
                    "21.12.12", "21.12.23", 0,
                    "가족 중에 한 분이 혈액암으로 많이 안 좋아요.. B형이신 분 지정헌혈 부탁드려요. 코로나로 헌혈하시는 분들이 많이 줄어서 B형 혈소판이 부족합니다.", "2021.12.11(토) 오후 20:42"),
                MainRequest(32, true, 4,"혈소판",
                    "21.12.11", "21.12.19", 2,
                    "친한 언니가 급성 심근염으로 중환자실에서 힘든 시간을 보내고 있습니다. Rh+ A형 혈소판을 구하고 있으니 가능하시다면 헌혈을 간곡하게 부탁드립니다.", "2021.12.11(토) 오전 10:31"),
                MainRequest(17, false, 3,"전혈",
                    "21.12.08", "21.12.15", 1,
                    "제 동생이 어제 사고를 당해서 수술을 했는데 예상보다 많은 출혈이 있었습니다.. 그런데 Rh- O형이라 혈액 구하기가 너무 어려운 상태입니다. 혈액형이 Rh- O형이신 분은 가까운 헌혈의 집에 가셔서 지정 헌혈을 해주시면 감사하겠습니다. 헌혈해주신다면 혈액이 해당 병원으로 바로 수송되어 환자에게 투여됩니다. 꼭 좀 부탁드려요." , "2021.12.08(수) 오전 07:09"),
                MainRequest(23, true, 1,"혈소판",
                    "21.12.07", "21.12.15", 3,
                    "결핵성 뇌수막염에 걸리신 어머니께서 항상 혈소판 수치가 낮게 나와 매일매일 수혈을 받으시면서 결핵 치료를 겨우 이어나가고 있었는데 피가 많이 부족해서 오늘 처음으로 수혈을 받지 못하셨습니다. 부디 좋은 분들이 나타나주셔서 수혈을 받으면서 결핵 치료를 받을 수 있게 도와주셨으면 감사하겠습니다. 부탁드립니다.. Rh+ AB형 혈소판 지정헌혈로 꼭 부탁드립니다." ,"2021.12.07(화) 오전 11:15"),
                MainRequest(54, true, 2,"전혈",
                    "21.12.04", "21.12.13", 2,
                    "할아버지께서 급하게 수혈이 필요한 상황인데 저는 타지에서 근무 중이라 직접 지정 헌혈을 할 수 없게 되어 간절한 마음을 담아 지정 헌혈 요청을 해봅니다.", "2021.12.04(토) 오후 21:48"),

                )
        )
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