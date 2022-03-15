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
                // 내가 쓴 글인지 확인
                ReviewService(requireContext()).reviewDeleteAuth(data.boardId){
                    if(it == true){ // 글 삭제
                        Log.d("[CHECK AUTH BEFORE DELETE]", "my review: true")
                        val dlg = BoardDeleteAlert(requireContext())
                        dlg.callFunction()
                        dlg.show()
                        dlg.setOnDismissListener {
                            deleteState = dlg.returnState()
                            if(deleteState){

                                //DB 에서 게시글 삭제
                                ReviewService(requireContext()).reviewDelete(data.boardId){
                                    if(it==true){ // 삭제 완료 후 dialog 띄우기
                                        Log.d("[REVIEW DELETE]", "$it")
                                        val dialog = BoardDeleteConfirmAlert(requireContext())
                                        dialog.callFunction()
                                        dialog.show()
                                        dialog.setOnDismissListener {
                                            boardAdapter.removeItem(pos)
                                            boardAdapter.notifyDataSetChanged()//데이더 값 변경 알림
                                        }
                                    }
                                }
                            }
                        }
                    }else{ // 삭제 불가 dialog 띄우기
                        Log.d("[CHECK AUTH BEFORE DELETE]", "my review: false")
                        val dialog = BoardDeleteRejectAlert(requireContext())
                        dialog.callFunction()
                        dialog.show()
                    }
                }
            }
        })
        //버튼
        val writeButton = rootView.findViewById<ImageButton>(R.id.btv_write)
        val myButton = rootView.findViewById<ImageButton>(R.id.btv_mypage)
        val myboardread = rootView.findViewById<CheckBox>(R.id.btv_cb)

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
        ReviewService(requireContext()).reviewList{ reviewList ->
            if(reviewList!=null){
                for(review in reviewList) Log.d("[REVIEW LIST2]", "$review")
                Log.d("[REVIEW LIST2]", "get all reviews")
                datas.apply{
                    for(review in reviewList){
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
    }
}