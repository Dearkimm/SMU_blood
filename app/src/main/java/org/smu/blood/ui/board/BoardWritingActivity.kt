package org.smu.blood.ui.board

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import org.smu.blood.R
import org.smu.blood.api.database.User
import org.smu.blood.databinding.ActivityBoardReadBinding
import org.smu.blood.ui.LoginActivity
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.util.shortToast
import java.lang.reflect.Member

class BoardWritingActivity : AppCompatActivity() { //게시판 글내용 읽기
    private var _binding: ActivityBoardReadBinding? = null
    private val binding get() = _binding!!
    var commentState = false

    //댓글 리사이클러뷰 어댑터추가
    lateinit var boardreadAdapter: BoardReadAdapter
    lateinit var recyclerview: RecyclerView
    val datas = mutableListOf<CommentData>()

    //글 삭제 다이얼로그 관련변수
    var deleteState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        _binding = ActivityBoardReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoardRead()

        //게시글 닉네임 = 내 닉네임 일때만 수정삭제 메뉴버튼 보이게
        //----------------
        binding.boardChange.visibility = View.VISIBLE
        //----------------

        //게시글 수정/삭제 클릭 시
        binding.boardChange.setOnClickListener{
            var popupMenu = PopupMenu(applicationContext,it)
            menuInflater?.inflate(R.menu.menu,popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_menu1->{ //내가 쓴 글 수정
                        Toast.makeText(applicationContext,"수정 클릭",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,BoardEditActivity::class.java)
                        intent.putExtra("제목", _binding!!.writingTitle.text)
                        intent.putExtra("내용",_binding!!.writingBody.text)
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
                                Toast.makeText(applicationContext, "삭제 완료", Toast.LENGTH_SHORT).show()
                                finish()
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
                shortToast("댓글이 등록되었습니다")
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0) //키보드 내리기
                binding.commentEt.text = null
                //액티비티 새로고침
                finish()
                val intent = intent
                startActivity(intent)
            } else {
                shortToast("빈 칸이 있습니다")
            }
        }
        //댓글 리사이클러뷰 어댑터
        boardreadAdapter = BoardReadAdapter(this)
        recyclerview = binding.root.findViewById<RecyclerView>(R.id.rc_comments_list)
        recyclerview.adapter = boardreadAdapter
        initRecycler()

        //댓글 리사이클러뷰 어댑터 클릭 이벤트 (댓글 수정 , 삭제)
        boardreadAdapter.setOnItemClickListener(object: BoardReadAdapter.OnItemClickListener{
            override fun onItemClick(v: View, data: CommentData, position: Int) {

            }
            override fun onEditClick(v: View, data: CommentData, position: Int) { //댓글 수정
                binding.commentEt.requestFocus()
                imm.showSoftInput(binding.commentEt,0)
                //기존 댓글 띄우기
                //--------
                //--------
                binding.commentEt.setText("기존 댓글넣기")
            }
            override fun onDeleteClick(v: View, data: CommentData, position: Int) { //댓글 삭제
                if (binding.commentEt.toString().isNotBlank()) {
                    commentdelete()
                }else
                    shortToast("빈 칸이 있습니다")
            }
        })
    }
    private fun commentdelete(){
        //댓글 등록 전 팝업
        val dlg = CommentDeleteAlert(this)
        dlg.callFunction()
        dlg.show()
        dlg.setOnDismissListener {
            commentState = dlg.returnState()
            if (commentState) {
                //DB에서 댓글 삭제
                shortToast("삭제되었습니다")
                //액티비티 새로고침
                finish()
                val intent = intent
                startActivity(intent)
            }
        }
    }

    private fun initBoardRead() {
        val intent = intent
        var title = intent.getStringExtra("title")
        var nickname = intent.getStringExtra("nickname")
        var time = intent.getStringExtra("time")
        var heartcount = intent.getIntExtra("heartcount", 0)
        var commentcount = intent.getIntExtra("commentcount",0)
        var boardtext = intent.getStringExtra("boardtext")

        val apply = binding.apply {
            Log.d("SHOW","REVIEWS")
            writingTitle.text = title
            writingNickname.text = nickname
            writingTime.text = time
            heartCounts.text = heartcount.toString()
            commentsCounts.text = commentcount.toString()
            writingBody.text = boardtext
        }
    }
    private fun initRecycler() {
        datas.apply {
            Log.d("SHOW","COMMENTS")
            add(CommentData(id = "게시판 id" ,nickname = "장구벌레",time = "2021/12/20", comment = "좋은 일 하셨어요!!"))
            add(CommentData(id = "게시판 id" ,nickname = "짬뽕",time = "2022/01/03", comment = "대단해요"))
            add(CommentData(id = "게시판 id" ,nickname = "가나다라",time = "2022/01/05", comment = "멋져요"))
            boardreadAdapter.datas = datas
            boardreadAdapter.notifyDataSetChanged()
        }
        //내가 쓴 댓글( 댓글 닉네임 = 내 닉네임 일경우) 댓글 수정,삭제버튼 visibility true 로 바꾸기
    }
}