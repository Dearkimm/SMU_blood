package org.smu.blood.ui.Notice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R
import org.smu.blood.api.ReviewService
import org.smu.blood.databinding.ActivityNoticeBinding
import org.smu.blood.ui.board.BoardReadAdapter
import org.smu.blood.ui.board.BoardRegisterActivity
import org.smu.blood.ui.my.Card.CardRequestActivity
import org.smu.blood.util.shortToast


class NoticeActivity : AppCompatActivity() {
    private var _binding: ActivityNoticeBinding? = null
    private val binding get() = _binding!!

    //알림 리사이클러뷰 어댑터추가
    lateinit var noticeAdapter: NoticeAdapter
    lateinit var recyclerview: RecyclerView
    var datas = mutableListOf<NoticeData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        _binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //알림 리사이클러뷰 어댑터
        noticeAdapter = NoticeAdapter(this)
        recyclerview = binding.root.findViewById<RecyclerView>(R.id.rc_alarm_lists)
        recyclerview.adapter = noticeAdapter
        initRecycler()

        //알림 사이클러뷰 어댑터 클릭 이벤트(헌혈 기록카드로 이동, 아이템 삭제)
        noticeAdapter.setOnItemClickListener(object: NoticeAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: NoticeData, pos: Int) {
                //해당 헌혈 요청 기록카드로 이동
            }
            override fun onDeleteClick(v: View, data: NoticeData, pos: Int) { //댓글 삭제
                //알림 삭제
            }
        })

    }
    private fun initRecycler() {
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