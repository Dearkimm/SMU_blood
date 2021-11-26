package org.smu.blood.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.smu.blood.R
import org.smu.blood.api.database.MainRequest
import org.smu.blood.databinding.FragmentMainBinding
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.adapter.MainRequestAdapter

class MyCardApplyFragment : BaseFragment<FragmentMainBinding>() {
    private val mainRequestAdapter = MainRequestAdapter()
    private val request = mutableListOf<MainRequest>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rcRequestList.layoutManager = LinearLayoutManager(activity)
        binding.rcRequestList.adapter = mainRequestAdapter

        //configureMainNavigation()
        addMainRequestInfo()
        //configureClickEvent()
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
                MainRequest(5, true, 1, "21.10.01", "21.10.15", 3, "하이"),
                MainRequest(32, false, 2, "21.10.23", "21.10.31", 0, "어쩔티비"),
                MainRequest(17, true, 3, "21.10.23", "21.10.31", 5, "저쩔냉장고"),
                MainRequest(54, true, 4, "21.10.01", "21.10.31", 0, "명성족발"),
            )
        )
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}