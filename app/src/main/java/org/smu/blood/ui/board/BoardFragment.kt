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
import android.widget.TextView
import androidx.activity.addCallback
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.R
import org.smu.blood.api.ReviewService
import org.smu.blood.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {
    lateinit var boardAdapter: BoardAdapter
    lateinit var recyclerview:RecyclerView
    var datas = mutableListOf<BoardData>()
    lateinit var rootView: View

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
        rootView = inflater.inflate(R.layout.fragment_board, container, false)

        setUpAdapter()
        initRecycler()
        configureBoardNavigation()

        var currentNickname = rootView.findViewById<TextView>(R.id.btv_nickname)
        var reviewService = ReviewService(requireContext())
        lateinit var nickname: String // 로그인 한 사용자 닉네임

        // 사용자 닉네임 가져오기
        reviewService.myNickname{
            if(it!=null){
                nickname = it.toString()
                Log.d("[MY NICKNAME]",nickname)
                currentNickname.text = nickname
            }else Log.d("[MY NICKNAME]","GET FAILURE")
        }

        //리사이클러뷰 어댑터 클릭 이벤트
        boardAdapter.setOnItemClickListener(object: BoardAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: BoardData, position: Int) {
                val intent = Intent(context, BoardWritingActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("title", data.title)
                intent.putExtra("nickname", data.nickname)
                intent.putExtra("time", data.time)
                intent.putExtra("heartcount", data.heartcount)
                intent.putExtra("commentcount",data.commentcount)
                intent.putExtra("boardtext",data.boardtext)
                intent.putExtra("boardId", data.boardId)
                intent.putExtra("userNickname", nickname)
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
                /* db에서 글 삭제할때사용
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
        myboardread.setOnCheckedChangeListener{ buttonview, isChecked ->
            if(isChecked) {//내가 쓴 글 필터링
                boardAdapter.filter.filter(nickname) //필터링 내 닉네임
                Log.d("내가쓴글", "체크선택")
            }
            else {
                Log.d("내가쓴글", "체크해제")
                boardAdapter.filter.filter("")
            }
        }

        //글쓰기로 이동
        writeButton.setOnClickListener {
            val intent = Intent(context, BoardRegisterActivity()::class.java)
            startActivity(intent)
            //보드 레지스터에서 데이터 받아오기 (DB 없이 테스트)
            //_binding = FragmentBoardBinding.inflate(inflater,container,false)
            val reviewId = arguments?.getInt("reviewId")
            val userId = arguments?.getString("userId")
            val userNickname = arguments?.getString("nickname")
            val title = arguments?.getString("title")
            val contents = arguments?.getString("contents")
            val time = arguments?.getString("time")
            val heartCount = arguments?.getInt("heartCount")
            if (title != null) {
                Log.d("데이터 확인",reviewId.toString()+" "+userId+" "+userNickname+" "+title+" "+contents+" "+time+" "+heartCount)
            }
            else Log.d("데이터","x")
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

    private fun setUpAdapter(){
        //리사이클러뷰 어댑터 세팅
        boardAdapter = BoardAdapter(requireContext())
        recyclerview = rootView.findViewById<RecyclerView>(R.id.rc_board_list)
        recyclerview.adapter = boardAdapter
    }

    private fun initRecycler() {
        // DB에서 전체 후기 가져와서 보여주기
        ReviewService(requireContext()).reviewList{
            if(it!=null){
                for(review in it) Log.d("[REVIEW LIST2]", "$review")
                Log.d("[REVIEW LIST2]", "get all reviews")
                datas.apply{
                    for(review in it){
                        Log.d("[REVIEW LIST2]","ADD ALL REVIEWS")
                        var boardData = BoardData(boardId=review.reviewId!!, title="${review.title}", nickname="${review.nickname}", time="${review.writeTime}",
                            heartcount=review.likeNum!!, boardtext="${review.contents}", commentcount=review.commentCount!!)
                        add(boardData)
                        Log.d("[REVIEW LIST2]", boardData.toString())
                    }
                    boardAdapter.unFilteredList = datas
                    boardAdapter.notifyDataSetChanged()
                }
                boardAdapter.filter.filter("")
            }else Log.d("[REVIEW LIST2]", "FAILURE")
        }

        /*
        datas.apply {
            Log.d("ADD","REVIEWS")
            add(BoardData(boardId = "게시글 id", title = "너무 급박했던 수술",nickname = "Snowflake",time = "1시간 전"
                ,heartcount = 2, commentcount = 3,boardtext = "안녕하세요, 얼마 전 Rh- O형의 헌혈이 시급하다는 소식을 듣고 Rh- 혈액은 워낙 구하기가" +
                        " 힘들다는 걸 알기에 지정 헌혈에 참여했습니다. 이번에는 헌혈자이지만 미래에 제가 혈액이 필요한 " +
                        "요청자가 될 수 있다는 걸 희귀 혈액형인 저는 너무나도 잘 압니다. 빠른 시일 내에 회복하시길 바라요. " +
                        "Rh- 환자분들 다들 힘내세요!"))
            add(BoardData(boardId = "게시글 id",title = "희귀혈액형 지정 헌혈 과정 공유",nickname = "시종설",time = "3시간 전"
                ,heartcount = 5, commentcount = 6,boardtext = "본문내용2"))
            add(BoardData(boardId = "게시글 id",title = "뿌듯한 첫 지정 헌혈 후기",nickname = "청파동",time = "1일 전"
                ,heartcount = 8, commentcount = 5,boardtext = "본문내용3"))
            add(BoardData(boardId = "게시글 id",title = "Rh-형 지정 헌혈하고 왔어요",nickname = "장구벌레",time = "4일 전"
                ,heartcount = 14, commentcount = 1,boardtext = "본문내용4"))
            add(BoardData(boardId = "게시글 id",title = "헌혈해주신 분들께 감사의 말씀",nickname = "yenomq34",time = "지난 주"
                ,heartcount = 25, commentcount = 0,boardtext = "본문내용5"))
            add(BoardData(boardId = "게시글 id",title = "성분 헌혈 과정 공유해봐요!",nickname = "눈송이",time = "지난 주"
                ,heartcount = 7, commentcount = 0,boardtext = "본문내용6"))
            add(BoardData(boardId = "게시글 id",title = "용산지역 혈액 수급",nickname = "yenomg34",time = "지난 주"
                ,heartcount = 12, commentcount = 0,boardtext = "본문내용7"))
        }

         */
    }
}