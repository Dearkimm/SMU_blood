package org.smu.blood.ui.Notice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R
import org.smu.blood.api.MainService
import org.smu.blood.api.ServiceCreator
import org.smu.blood.api.SessionManager
import org.smu.blood.api.database.Notification
import org.smu.blood.api.database.Request
import org.smu.blood.databinding.FragmentNoticeBinding
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.ui.main.MainFragment
import org.smu.blood.ui.my.Card.CardRequestActivity
import org.smu.blood.ui.my.MyRequestFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeFragment : Fragment() {
    private var _binding: FragmentNoticeBinding? = null
    private val binding get() = _binding!!
    lateinit var rootView: View

    //알림 리사이클러뷰 어댑터
    lateinit var noticeAdapter: NoticeAdapter
    lateinit var recyclerview: RecyclerView
    var datas = mutableListOf<NoticeData>()

    lateinit var noticeRequestList: List<Request>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notice, container, false)

        setUpAdapter()
        initRecycler()
        configureBoardNavigation()

        //알림 사이클러뷰 어댑터 클릭 이벤트(헌혈 기록카드로 이동, 아이템 삭제)
        noticeAdapter.setOnItemClickListener(object: NoticeAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: NoticeData, pos: Int) {
                // 해당 알림의 notice state = false 로 설정
                updateState(data.noticeId){ result ->
                    if(result == true) Log.d("[NOTICE]", "notice state updated to false")
                    else Log.d("[NOTICE]", "notice state update failed")
                }

                //해당 헌혈 요청 기록카드로 이동
                Log.d("[NOTICE]", "move to request info")
                MyRequestFragment.myRequest = noticeRequestList.first { it.requestId == data.requestId }
                Log.d("[NOTICE]", "${MyRequestFragment.myRequest}")

                val intent = Intent(context, CardRequestActivity::class.java)
                startActivity(intent)
            }
            override fun onDeleteClick(v: View, data: NoticeData, pos: Int) { //댓글 삭제
                //알림 삭제
                noticeAdapter.removeItem(pos)
                noticeAdapter.notifyDataSetChanged()
            }
        })
        return rootView
    }

    private fun configureBoardNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback {
            (activity as NavigationActivity).showFinishToast()
        }
    }
    private fun setUpAdapter(){
        //알림 리사이클러뷰 어댑터
        noticeAdapter = NoticeAdapter(requireContext())
        recyclerview = rootView.findViewById<RecyclerView>(R.id.rc_alarm_lists)
        recyclerview.adapter = noticeAdapter
        initRecycler()
    }

    private fun initRecycler() {
        // get request list of notice
        getRequestList{ list ->
            if(list != null){
                noticeRequestList = list
            }
        }

        // DB에서 사용자 알림 가져와서 보여주기
        MainService(requireContext()).getNoticeList{ noticeList ->
            if(noticeList != null){
                datas.apply {
                    for(notice in noticeList){
                        Log.d("[SHOW NOTICE]","$notice")
                        add(NoticeData(noticeId = notice.noticeId!!, requestId = notice.requestId!!, userId= notice.userId!!, alert_time =notice.notTime!!, noticeState = notice.notState!! ))
                    }
                }
                noticeAdapter.datas = datas
                noticeAdapter.notifyDataSetChanged()
            }
        }
        /*
        datas.apply {
            Log.d("SHOW","Alerts")
            add(NoticeData(requestId = 1, userId= "1", alert_time = "2021/12/20 16:22:26"))
            add(NoticeData(requestId = 2, userId= "2", alert_time = "2021/12/21 08:25:46"))
            add(NoticeData(requestId = 3, userId= "3" ,alert_time = "2021/12/22 14:13:02"))
            add(NoticeData(requestId = 4, userId= "4", alert_time = "2021/12/22 14:13:02"))
            noticeAdapter.datas = datas
            noticeAdapter.notifyDataSetChanged()
        }

         */
    }

    private fun getRequestList(onResult: (List<Request>?)->Unit){
        ServiceCreator.bumService.requestlistOfNotice("${SessionManager(requireContext()).fetchToken()}")
            .enqueue(object : Callback<List<Request>> {
                override fun onResponse(call: Call<List<Request>>, response: Response<List<Request>>) {
                    if(response.isSuccessful){
                        Log.d("[REQUEST LIST OF NOTICE]", "${response.body()}")
                        onResult(response.body())
                    }
                    else{ // response error
                        Log.d("[REQUEST LIST OF NOTICE]", "${response.errorBody()}")
                        onResult(null)
                    }
                }

                override fun onFailure(call: Call<List<Request>>, t: Throwable) {
                    Log.d("[REQUEST LIST OF NOTICE]", t.localizedMessage)
                    onResult(null)
                }
            })
    }

    private fun updateState(noticeId: Int, onResult: (Boolean?)->Unit){
        ServiceCreator.bumService.updateNotState("${SessionManager(requireContext()).fetchToken()}", noticeId)
            .enqueue(object : Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        Log.d("[UPDATE NOTICE STATE]", response.body().toString())
                    }else{
                        Log.d("[UPDATE NOTICE STATE]", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("[UPDATE NOTICE STATE]", t.localizedMessage)
                }
            })
    }
}