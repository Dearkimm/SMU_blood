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
import org.smu.blood.api.NoticeService
import org.smu.blood.api.ServiceCreator
import org.smu.blood.api.SessionManager
import org.smu.blood.api.database.Request
import org.smu.blood.databinding.FragmentNoticeBinding
import org.smu.blood.ui.NavigationActivity
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
        noticeAdapter.setItemClickListener(object: NoticeAdapter.ItemClickListener{
            // 헌혈 기록카드로 이동
            override fun onItemClick(v: View, data: NoticeData) {
                Log.d("[NOTICE]", "item clicked")

                //해당 헌혈 요청 기록카드로 이동
                Log.d("[NOTICE]", "move to request info")
                MyRequestFragment.myRequest = noticeRequestList.first { it.requestId == data.requestId }
                Log.d("[NOTICE]", "${MyRequestFragment.myRequest}")

                // 해당 알림의 notice state = false 로 설정
                NoticeService(requireContext()).updateState(data.noticeId){ result ->
                    if(result == true) Log.d("[NOTICE]", "notice state updated")
                    else Log.d("[NOTICE]", "notice state update failed")
                }

                val intent = Intent(context, CardRequestActivity::class.java)
                startActivity(intent)
            }
            // 알림 삭제
            override fun onDeleteClick(v: View, data: NoticeData, pos: Int) {
                //알림 삭제
                Log.d("[NOTICE]", "delete notice")
                noticeAdapter.removeItem(pos)
                noticeAdapter.notifyDataSetChanged()

                // update delete state to true
                NoticeService(requireContext()).setDeleteState(data.noticeId){ result->
                    if(result == true){
                        Log.d("[NOTICE]", "delete notice success")
                    }
                }
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
        NoticeService(requireContext()).getRequestList{ list ->
            if(list != null){
                Log.d("[NOTICE]", "get request list success")
                noticeRequestList = list
            }
        }

        // DB에서 사용자 알림 가져와서 보여주기
        NoticeService(requireContext()).getNoticeList { noticeList ->
            if(noticeList != null){
                val list = mutableListOf<NoticeData>()
                for(notice in noticeList){
                    Log.d("[SHOW NOTICE]","$notice")
                    list.add(NoticeData(noticeId = notice.noticeId!!, requestId = notice.requestId!!, userId= notice.userId!!, alert_time =notice.notTime!!, noticeState = notice.notState!! ))
                }
                noticeAdapter.setItems(list)
            }else{
                Log.d("[SHOW NOTICE]","failed")
            }
        }


        /*
        datas.apply {
            Log.d("SHOW","Alerts")
            add(NoticeData(noticeId = 1, requestId = 1, userId= "1", alert_time = "2021/12/20 16:22:26", true))
            add(NoticeData(noticeId = 2, requestId = 2, userId= "2", alert_time = "2021/12/21 08:25:46", false))
            add(NoticeData(noticeId = 3, requestId = 3, userId= "3" ,alert_time = "2021/12/22 14:13:02", false))
            add(NoticeData(noticeId = 4, requestId = 4, userId= "4", alert_time = "2021/12/22 14:13:02", true))
            noticeAdapter.datas = datas
            noticeAdapter.notifyDataSetChanged()
        }

         */


    }


}