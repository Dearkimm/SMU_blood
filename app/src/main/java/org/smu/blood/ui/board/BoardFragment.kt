package org.smu.blood.ui.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.activity.addCallback
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.R
import org.smu.blood.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {
    lateinit var boardAdapter: BoardAdapter
    lateinit var recyclerview:RecyclerView
    val datas = mutableListOf<BoardData>()

    private var _binding : FragmentBoardBinding? = null
    private val binding get() = _binding!!

    //글 삭제 다이얼로그 관련변수
    var deleteState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_board, container, false)

        //리사이클러뷰 어댑터
        boardAdapter = context?.let { BoardAdapter(it) }!!
        recyclerview = rootView.findViewById<RecyclerView>(R.id.rc_board_list)
        recyclerview.adapter = boardAdapter
        initRecycler()

        configureBoardNavigation()

        //리사이클러뷰 어댑터 클릭 이벤트
        boardAdapter.setOnItemClickListener(object: BoardAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: BoardData, position: Int) {
                val intent = Intent(context, BoardWritingActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("title", data.title)
                intent.putExtra("nickname", data.nickname)
                intent.putExtra("time", data.time)
                intent.putExtra("heartcount", data.heartcount)
                startActivity(intent)
            }
        })

        //리사이클러뷰 어댑터 꾹누르기 이벤트
        boardAdapter.setOnItemLongClickListener(object: BoardAdapter.OnItemLongClickListener{
            override fun onItemLongClick(v: View, data: BoardData, pos: Int) {
                val dlg = BoardDeleteAlert(requireContext())
                dlg.callFunction()
                dlg.show()
                dlg.setOnDismissListener {
                    deleteState = dlg.returnState()
                    if(deleteState){
                        boardAdapter.removeItem(pos)
                        boardAdapter.notifyDataSetChanged()//데이더 값 변경 알림
                    }
                    //글쓰기 데이터
                    Log.d("글삭제 데이터", deleteState.toString())
                }
                /*글 삭제할때 써야되나
                val intent = Intent(context, BoardWritingActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("title", data.title)
                intent.putExtra("nickname", data.nickname)
                intent.putExtra("time", data.time)
                intent.putExtra("heartcount", data.heartcount)
                startActivity(intent)*/
            }
        })


        //버튼
        var writeButton = rootView.findViewById<ImageButton>(R.id.btv_write)
        var myButton = rootView.findViewById<ImageButton>(R.id.btv_mypage)
        var myboardread = rootView.findViewById<CheckBox>(R.id.btv_cb)

        //내가 쓴 글 누르기 이벤트
        myboardread.setOnClickListener{
        }


        //글쓰기로 이동
        writeButton.setOnClickListener {
            val intent = Intent(context, BoardRegisterActivity()::class.java)
            startActivity(intent)
            //보드 레지스터에서 데이터 받아오기
            //_binding = FragmentBoardBinding.inflate(inflater,container,false)
            val title = arguments?.getString("title")
            val contents = arguments?.getString("contents")
            val time = arguments?.getString("time")
            if (title != null) {
                Log.d("데이터받아와졋나 확인",title)
            }
            else Log.d("데이터받아와졋냐?","아니")
           /* var datas = mutableListOf<BoardData>()
            datas.add(0, BoardData(title = title.toString() ,nickname = "장구벌레", time = time.toString(),heartcount = 0))
            boardAdapter.notifyItemInserted(0)*/
            /* val title = arguments?.getString("title")
            val contents = arguments?.getString("contents")
            val time = arguments?.getString("time")
            val title = intent.getStringExtra("title")
            val contents = ar.getStringExtra("contents")
            val time = intent.getStringExtra("time")
            Log.d("보드 레지스터에서 받아온거",title+contents+time)*/
        }

        //마이페이지로 이동
        myButton.setOnClickListener {
            (activity as NavigationActivity).navigateMainToMy()
        }
        return rootView
    }

    companion object {
         @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BoardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun configureBoardNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback {
            (activity as NavigationActivity).showFinishToast()
        }
    }

    private fun initRecycler() {
        datas.apply {
            //새로 쓰는 글
            //add(BoardData(title = "너무 급박했던 수술",nickname = "Snowflake",time = "1시간 전",heartcount = 2))
            add(BoardData(title = "너무 급박했던 수술",nickname = "Snowflake",time = "1시간 전",heartcount = 2))
            add(BoardData(title = "희귀혈액형 지정 헌혈 과정 공유",nickname = "시종설",time = "3시간 전",heartcount = 5))
            add(BoardData(title = "뿌듯한 첫 지정 헌혈 후기",nickname = "청파동",time = "1일 전",heartcount = 8))
            add(BoardData(title = "Rh-형 지정 헌혈하고 왔어요",nickname = "장구벌레",time = "4일 전",heartcount = 14))
            add(BoardData(title = "헌혈해주신 분들께 감사의 말씀",nickname = "yenomq34",time = "지난 주",heartcount = 25))
            add(BoardData(title = "성분 헌혈 과정 공유해봐요!",nickname = "눈송이",time = "지난 주",heartcount = 7))
            add(BoardData(title = "용산지역 혈액 수급",nickname = "yenomg34",time = "지난 주",heartcount = 12))
            boardAdapter.datas = datas
            boardAdapter.notifyDataSetChanged()
        }
    }
    /*private fun initRecycler2() {
        datas.apply {
            add(BoardData(title = "희귀혈액형 지정헌혈 후기",nickname = "장구벌레",time = "지난 주",heartcount = 25))
            boardAdapter.datas = datas
            boardAdapter.notifyDataSetChanged()

        }
    }*/
}