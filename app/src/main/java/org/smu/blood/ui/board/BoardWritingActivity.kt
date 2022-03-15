package org.smu.blood.ui.board

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R
import org.smu.blood.api.ReviewService
import org.smu.blood.api.SessionManager
import org.smu.blood.databinding.ActivityBoardReadBinding
import org.smu.blood.databinding.ActivityNavigationBinding
import org.smu.blood.util.replaceFragment
import org.smu.blood.util.shortToast
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


//게시판 글내용 읽기
class BoardWritingActivity : AppCompatActivity() {
    private var _binding: ActivityBoardReadBinding? = null
    private val binding get() = _binding!!
    var commentState = false
    lateinit var currentNickname: String // 현재 사용자 닉네임
    var boardId: Int = 0

    //댓글 리사이클러뷰 어댑터추가
    lateinit var boardreadAdapter: BoardReadAdapter
    lateinit var recyclerview: RecyclerView
    var datas = mutableListOf<CommentData>()

    //글 삭제 다이얼로그 관련변수
    var deleteState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        _binding = ActivityBoardReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 게시글 내용 세팅
        initBoardRead()

        //게시글 수정/삭제 클릭 시
        binding.boardChange.setOnClickListener{
            val popupMenu = PopupMenu(applicationContext,it)
            menuInflater.inflate(R.menu.menu,popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_menu1->{ //내가 쓴 글 수정
                        Toast.makeText(applicationContext,"수정 클릭",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,BoardEditActivity::class.java)
                        intent.putExtra("originTitle", _binding!!.writingTitle.text)
                        intent.putExtra("originContent",_binding!!.writingBody.text)
                        intent.putExtra("originTime", _binding!!.writingTime.text)
                        intent.putExtra("nickname", _binding!!.writingNickname.text)
                        intent.putExtra("reviewId", boardId.toString())
                        startActivity(intent)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.action_menu2-> { //내가 쓴 글 삭제
                        val dlg = BoardDeleteAlert(this)
                        dlg.callFunction()
                        dlg.show()
                        dlg.setOnDismissListener {
                            deleteState = dlg.returnState()
                            if(deleteState){
                                //DB 에서 게시글 삭제
                                ReviewService(this).reviewDelete(boardId){
                                    if(it==true){
                                        val dialog = BoardDeleteConfirmAlert(this)
                                        dialog.callFunction()
                                        dialog.show()
                                        dialog.setOnDismissListener {
                                            // 업데이트된 후기 게시판으로 가져오기
                                            //액티비티 새로고침
                                            val binding2: ActivityNavigationBinding = ActivityNavigationBinding.inflate(layoutInflater)
                                            setContentView(binding2.root)
                                            replaceFragment(binding2.fragmentContainer, BoardFragment::class.java, true)
                                        }
                                    }
                                }
                            }
                        }
                        return@setOnMenuItemClickListener true
                    }else-> return@setOnMenuItemClickListener false
                }
            }
        }
        //댓글 등록 클릭시
        binding.commentBt.setOnClickListener {
            if (binding.commentEt.text.isNotBlank()) { //댓글edittext 빈칸 아닐 경우
                //댓글 DB에 댓글 추가하기(id, nickname, time, comment)
                val requestInfo= HashMap<String,String>()

                requestInfo["comment"] = binding.commentEt.text.toString()
                requestInfo["commentNickname"] = currentNickname
                requestInfo["commentTime"] = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + " " +
                        LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                requestInfo["reviewId"] = boardId.toString()

                ReviewService(this).commentWrite(requestInfo){
                    if(it==true){
                        shortToast("댓글이 등록되었습니다")
                        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0) //키보드 내리기
                        binding.commentEt.text = null
                        //액티비티 새로고침
                        finish()
                        startActivity(intent)
                    }
                }
            } else {
                shortToast("빈 칸이 있습니다")
            }
        }
        //댓글 리사이클러뷰 어댑터
        boardreadAdapter = BoardReadAdapter(this)
        recyclerview = binding.root.findViewById<RecyclerView>(R.id.rc_comments_list)
        recyclerview.adapter = boardreadAdapter
        initRecycler()

        val reviewService = ReviewService(this)
        //댓글 리사이클러뷰 어댑터 클릭 이벤트 (댓글 수정 , 삭제)
        boardreadAdapter.setOnItemClickListener(object: BoardReadAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: CommentData, pos: Int) {
            }
            override fun onEditClick(v: View, data: CommentData, pos: Int) { //댓글 수정
                binding.commentEt.requestFocus()
                imm.showSoftInput(binding.commentEt,0)

                //기존 댓글 띄우기
                binding.commentEt.setText(data.comment)
                val commentNickname = currentNickname
                val writeTime = data.time

                // 댓글 수정 후 등록 버튼 누르면 (댓글 수정 버튼 listener 처리 부분) ---------------
                binding.commentBt.setOnClickListener {

                    // 수정하는 comment 내용 가져오기
                    val editComment = binding.commentEt.text.toString()
                    Log.d("[EDIT COMMENT]", editComment)
                    // 수정 날짜, 시간 가져오기
                    val time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                    val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                    val editTime = "$date $time"

                    val editInfo = HashMap<String,String>()
                    editInfo["commentId"] = data.commentId.toString()
                    editInfo["editComment"] = editComment
                    editInfo["editTime"] = editTime

                    reviewService.commentEdit(editInfo){
                        if(it==true){
                            Log.d("[EDIT COMMENT]", "edit success")
                            //액티비티 새로고침
                            finish()
                            startActivity(intent)
                        }else{
                            Log.d("[EDIT COMMENT]", "edit failed")
                        }
                    }
                }
                // --------------------------------------------

            }
            override fun onDeleteClick(v: View, data: CommentData, pos: Int) { //댓글 삭제
                if (binding.commentEt.toString().isNotBlank()) {
                    commentdelete(data)
                }else
                    shortToast("빈 칸이 있습니다")
            }
        })

        // 좋아요 이벤트
        binding.heartCheckbox.setOnCheckedChangeListener { buttonview, isChecked ->
            val reviewInfo = HashMap<String,String>()
            val reviewHeart: Int
            val heartState: Boolean = isChecked

            // 게시글에 대한 사용자의 좋아요 체크 여부 저장
            reviewInfo["boardId"] = boardId.toString()
            reviewInfo["heartState"] = heartState.toString()

            // DB에 업데이트된 글 정보, reviewLike 정보 보내기
            ReviewService(this).heartCheck(reviewInfo){
                if(it==true) {
                    // save heart checked state of review of log in user
                    Log.d("[HEART EVENT1] SAVE HEART STATE", "success")
                }
                else Log.d("[HEART EVENT1] SAVE HEART STATE", "failed")
            }
        }
    }
    private fun commentdelete(data: CommentData){
        //댓글 등록 전 팝업
        val dlg = CommentDeleteAlert(this)
        dlg.callFunction()
        dlg.show()
        dlg.setOnDismissListener {
            commentState = dlg.returnState()
            if (commentState) {
                //DB에서 댓글 삭제
                val deleteInfo = HashMap<String,String>()
                deleteInfo["commentId"] = data.commentId.toString()
                deleteInfo["commentNickname"] = data.nickname
                deleteInfo["commentTime"] = data.time
                ReviewService(this).commentDelete(deleteInfo){
                    if(it == true){
                        Log.d("[DELETE COMMENT]", "success")
                        shortToast("삭제되었습니다")
                        //액티비티 새로고침
                        finish()
                        startActivity(intent)
                    }else{
                        Log.d("[DELETE COMMENT]", "failed")
                    }
                }

            }
        }
    }

    private fun initBoardRead() {
        val intent = intent
        val title = intent.getStringExtra("title")
        val reviewNickname = intent.getStringExtra("nickname")
        val time = intent.getStringExtra("time")
        val heartcount = intent.getIntExtra("heartcount", 0)
        val commentcount = intent.getIntExtra("commentcount",0)
        val boardtext = intent.getStringExtra("boardtext")
        val userNickname = intent.getStringExtra("userNickname")
        boardId = intent.getIntExtra("boardId", 0)

        currentNickname = userNickname.toString()
        Log.d("[CURRENT USERNICKNAME", currentNickname)

        val reviewService = ReviewService(this)
        binding.apply {
            Log.d("SHOW","REVIEW")
            writingTitle.text = title
            writingNickname.text = reviewNickname
            writingTime.text = time
            heartCounts.text = heartcount.toString()
            commentsCounts.text = commentcount.toString()
            writingBody.text = boardtext

            // 게시글 수정/삭제 visibility 설정
            Log.d("[CHECK REVIEW NICKNAME]","currentNickname: $userNickname, reviewNickname: $reviewNickname")
            if(userNickname == reviewNickname) binding.boardChange.visibility = VISIBLE
            else Log.d("[CHECK REVIEW NICKNAME]", "NOT MY REVIEW OR INVALID")

            // 현재 사용자가 해당 게시물에 좋아요 눌렀으면 set Checked
            reviewService.getMyHeartState(boardId) { reviewLike ->
                if(reviewLike != null){
                    Log.d("[GET HEART STATE OF REVIEW]", reviewLike.toString())
                    Log.d("[HEART EVENT3] nickname $userNickname check state of reviewId $boardId", reviewLike.heartState.toString())

                    // heartState == true이면 체크 표시
                    heartCheckbox.isChecked = reviewLike.heartState!!
                } else heartCheckbox.isChecked = false
            }
        }
    }

    private fun initRecycler() {
        // DB에서 특정 후기글의 댓글 리스트 가져오기
        val reviewInfo = HashMap<String,String>()
        reviewInfo["reviewNickname"] = binding.writingNickname.text.toString()
        reviewInfo["reviewTime"] = binding.writingTime.text.toString()

        ReviewService(this).commentList(reviewInfo){
            if(it!=null){
                for(comment in it) Log.d("[COMMENT LIST]", "$comment")
                Log.d("[COMMENT LIST]", "get all comments of review")
                datas.apply{
                    for(comment in it){
                        Log.d("[COMMENT LIST]","ADD ALL REVIEWS")
                        val commentData = CommentData(commentId= comment.commentId!!, reviewId=comment.reviewId!!, nickname=comment.nickname!!, time=comment.time!!, comment=comment.comment!!)
                        add(commentData)
                        Log.d("[COMMENT LIST]", commentData.toString())
                    }
                    boardreadAdapter.datas = datas
                    boardreadAdapter.currentNickname = currentNickname
                    boardreadAdapter.notifyDataSetChanged()
                }
            }
        }
        /*
        datas.apply {
            Log.d("SHOW","COMMENTS")
            add(CommentData(reviewId = 1 ,nickname = "장구벌레",time = "2021/12/20", comment = "좋은 일 하셨어요!!"))
            add(CommentData(reviewId = 2 ,nickname = "짬뽕",time = "2022/01/03", comment = "대단해요"))
            add(CommentData(reviewId = 3 ,nickname = "가나다라",time = "2022/01/05", comment = "멋져요"))
            boardreadAdapter.datas = datas
            boardreadAdapter.notifyDataSetChanged()
        }
         */
    }
}