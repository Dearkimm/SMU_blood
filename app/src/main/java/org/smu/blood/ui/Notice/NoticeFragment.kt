package org.smu.blood.ui.Notice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R
import org.smu.blood.api.ReviewService
import org.smu.blood.databinding.ActivityNoticeBinding
import org.smu.blood.databinding.FragmentBoardBinding
import org.smu.blood.databinding.FragmentNoticeBinding
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.ui.board.*

class NoticeFragment : Fragment() {
    private var _binding: FragmentNoticeBinding? = null
    private val binding get() = _binding!!
    lateinit var rootView: View

    //알림 리사이클러뷰 어댑터
    lateinit var noticeAdapter: NoticeAdapter
    lateinit var recyclerview: RecyclerView
    var datas = mutableListOf<NoticeData>()

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
                //해당 헌혈 요청 기록카드로 이동
            }
            override fun onDeleteClick(v: View, data: NoticeData, pos: Int) { //댓글 삭제
                //알림 삭제
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
        // DB에서 전체 후기 가져와서 보여주기
        datas.apply {
            Log.d("SHOW","Alerts")
            add(NoticeData(userId= 1,alert_date = "2021/12/20",alert_time = "16:22:26"))
            add(NoticeData(userId= 2, alert_date = "2021/12/21",alert_time = "08:25:46"))
            add(NoticeData(userId= 3,alert_date = "2021/12/22",alert_time = "14:13:02"))
            add(NoticeData(userId= 4,alert_date = "2021/12/22",alert_time = "14:13:02"))
            noticeAdapter.datas = datas
            noticeAdapter.notifyDataSetChanged()
        }
    }
}