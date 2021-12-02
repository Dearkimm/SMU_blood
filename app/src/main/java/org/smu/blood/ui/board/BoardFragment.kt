package org.smu.blood.ui.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.R

class BoardFragment : Fragment() {
    lateinit var boardAdapter: BoardAdapter
    lateinit var recyclerview:RecyclerView
    val datas = mutableListOf<BoardData>()

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

        //글쓰기로 이동
        writeButton.setOnClickListener {
            val intent = Intent(context, BoardRegisterActivity()::class.java)
            startActivity(intent)
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

    private fun initRecycler() {

        datas.apply {
            add(BoardData(title = "너무 급박했던 수혈과정",nickname = "kim202",time = "30분 전",heartcount = 1982))
            add(BoardData(title = "희귀혈액형 수혈과정 공유",nickname = "Eun304",time = "1시간 전",heartcount = 234))
            add(BoardData(title = "용산지역 혈액 수급",nickname = "yenomg34",time = "3시간 전",heartcount = 1002))
            add(BoardData(title = "Rh-의 헌혈자 구하기",nickname = "yeziyezi",time = "4일 전",heartcount = 67))
            add(BoardData(title = "희귀혈액형 지정헌혈 후기",nickname = "장구벌레",time = "지난 주",heartcount = 25))
            add(BoardData(title = "Rh-의 헌혈자 구하기",nickname = "yeziyezi",time = "4일 전",heartcount = 67))
            add(BoardData(title = "용산지역 혈액 수급",nickname = "yenomg34",time = "3시간 전",heartcount = 1002))

            boardAdapter.datas = datas
            boardAdapter.notifyDataSetChanged()

        }
    }
}