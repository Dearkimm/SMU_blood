package org.smu.blood.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.VISIBLE
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.api.database.MainRequest
import org.smu.blood.databinding.FragmentMainBinding
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.adapter.MainRequestAdapter
import androidx.activity.addCallback
import androidx.annotation.VisibleForTesting
import org.smu.blood.R
import org.smu.blood.api.MainService
import org.smu.blood.api.MyPageService
import org.smu.blood.ui.my.MyRequestFragment


class MainFragment : BaseFragment<FragmentMainBinding>() {
    private val mainRequestAdapter = MainRequestAdapter()
    var bloodType: Int = 0

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rcRequestList.layoutManager = LinearLayoutManager(activity)
        binding.rcRequestList.adapter = mainRequestAdapter

        configureMainNavigation()
        initMain()
        addMainRequestInfo()
        configureClickEvent()
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_sort_option, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.option_menu1->
                Log.d("메뉴1","클릭2")
            R.id.option_menu2->
                Log.d("메뉴2","클릭2")
            R.id.option_menu3->
                Log.d("메뉴3","클릭2")
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initMain() {
        // 알림 받은 상태 체크
        MainService(requireContext()).checkNotState{ request ->
            if(request != null){
                Log.d("[CHECK NOTIFICATION STATE]", request.toString())
                binding.notiAlert.visibility = VISIBLE
                MyRequestFragment.myRequest = request
            }else{
                Log.d("[CHECK NOTIFICATION STATE]", "no noti info")
            }
        }

        // DB에서 로그인한 사용자 bloodType 가져오기
        MyPageService(requireContext()).myInfo { user ->
            if(user != null){
                bloodType = user.bloodType!!
                Log.d("[GET MY BLOOD TYPE]", bloodType.toString())
            }
        }

        MainService(requireContext()).mainList {
            // DB에서 가져온 리스트 서버로부터 받기
            if(it!=null){
                Log.d("[BLOOD REQUEST LIST]","GET LIST")
                val requestList = mutableListOf<MainRequest>()
                rList = requestList
                for(request in it){
                    val mainRequest = MainRequest(request.hospitalId!!, request.requestId!!, request.rhType!!, request.bloodType!!, request.donationType!!, request.startDate!!, request.endDate!!, request.applicantNum!!, request.story!!, request.registerTime!!)
                    Log.d("[BLOOD REQUEST LIST]", mainRequest.toString())
                    // MainRequest 리스트에 넣기
                    requestList.add(mainRequest)
                }
                for(request in requestList) Log.d("[BLOOD REQUEST LIST]", request.toString())
                mainRequestAdapter.setItems(requestList)

                mainRequestAdapter.unFilteredList = requestList
                mainRequestAdapter.notifyDataSetChanged()
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
                //unFilteredList = requestInfo
                request = requestInfo

                Log.d("[SHOW REQUEST INFO]",request.toString())

                (activity as NavigationActivity).navigateMainToRead()
            }
        })

        //필터링
        binding.mainSwitch.setOnCheckedChangeListener{buttonView, isChecked ->
            if (isChecked){
                //필터사용
                //mainRequestAdapter.filter.filter(unFilteredList.bloodType.toString())
                mainRequestAdapter.filter.filter(bloodType.toString())
                Log.d("내혈액형만보기", "체크선택")
            }
            else{
                //필터 미사용
                Log.d("내혈액형만보기", "체크해제")
                mainRequestAdapter.filter.filter("")
            }
        }

        // sorting option click
        binding.mainSort.setOnClickListener{
            var popupMenu = PopupMenu(context,it)
            popupMenu.menuInflater.inflate(R.menu.main_sort_option,popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.option_menu1->{ //최신순
                        Log.d("메뉴1","클릭")
                        initMain()
                        return@setOnMenuItemClickListener true
                    }
                    R.id.option_menu2->{ //마감 임박 순
                        Log.d("메뉴2","클릭")
                        MainService(requireContext()).sortByDate { response ->
                            if(response != null){
                                val requestList = mutableListOf<MainRequest>()
                                Log.d("[GET REUQEST LIST ORDER BY DATE]", "success")
                                response.forEach {
                                    Log.d("[GET REUQEST LIST ORDER BY DATE]", it.toString())
                                    val mainRequest = MainRequest(it.hospitalId!!, it.requestId!!, it.rhType!!, it.bloodType!!, it.donationType!!, it.startDate!!, it.endDate!!,it.applicantNum!!, it.story!!, it.registerTime!!)
                                    requestList.add(mainRequest)
                                }
                                mainRequestAdapter.setItems(requestList)

                                mainRequestAdapter.unFilteredList = requestList
                                mainRequestAdapter.notifyDataSetChanged()
                                mainRequestAdapter.filter.filter("")
                            }else{
                                Log.d("[GET REUQEST LIST ORDER BY DATE]", "failed")
                            }
                        }
                        return@setOnMenuItemClickListener true
                    }
                    R.id.option_menu3->{ //신청자 적은 순
                        Log.d("메뉴3","클릭")
                        MainService(requireContext()).sortByApplicant{ response ->
                            if(response != null){
                                val requestList = mutableListOf<MainRequest>()
                                Log.d("[REUQEST LIST ORDER BY APPLICANTNUM]", "success")
                                response.forEach {
                                    Log.d("[REUQEST LIST ORDER BY APPLICANTNUM]", it.toString())
                                    val mainRequest = MainRequest(it.hospitalId!!, it.requestId!!, it.rhType!!, it.bloodType!!, it.donationType!!, it.startDate!!, it.endDate!!,it.applicantNum!!, it.story!!, it.registerTime!!)
                                    requestList.add(mainRequest)
                                }
                                mainRequestAdapter.setItems(requestList)

                                mainRequestAdapter.unFilteredList = requestList
                                mainRequestAdapter.notifyDataSetChanged()
                                mainRequestAdapter.filter.filter("")
                            }else{
                                Log.d("[REUQEST LIST ORDER BY APPLICANTNUM]", "failed")
                            }

                        }
                        return@setOnMenuItemClickListener true
                    }else-> return@setOnMenuItemClickListener false
                }
            }
        }
    }

    companion object {
        lateinit var request: MainRequest
        lateinit var rList: List<MainRequest>
    }
}