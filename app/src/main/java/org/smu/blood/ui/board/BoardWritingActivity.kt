package org.smu.blood.ui.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
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
import org.smu.blood.util.shortToast

class BoardWritingActivity : AppCompatActivity() { //게시판 글 읽기
    private var _binding: ActivityBoardReadBinding? = null
    private val binding get() = _binding!!

    //댓글 리사이클러뷰 어댑터추가
    lateinit var boardreadAdapter: BoardReadAdapter
    lateinit var recyclerview: RecyclerView
    val datas = mutableListOf<CommentData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        _binding = ActivityBoardReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoardRead()
        //게시글 수정/삭제 클릭 시
        binding.boardChange.setOnClickListener{
            var popupMenu = PopupMenu(applicationContext,it)
            menuInflater?.inflate(R.menu.menu,popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_menu1->{
                        Toast.makeText(applicationContext,"수정 클릭",Toast.LENGTH_SHORT).show()
                        return@setOnMenuItemClickListener true
                    }
                    R.id.action_menu2-> {
                        Toast.makeText(applicationContext, "삭제 클릭", Toast.LENGTH_SHORT).show()
                        return@setOnMenuItemClickListener true
                    }else-> return@setOnMenuItemClickListener false
                }
            }
        }

        //댓글 등록 클릭시
        binding.commentBt.setOnClickListener {
            if (binding.commentEt.text.isNotBlank()) { //댓글edittext 빈칸 아닐 경우

                //댓글 DB에 댓글 추가하기(id, nickname, time, comment)

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

        })
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
            writingTitle.text = title
            writingNickname.text = nickname
            writingTime.text = time.toString()
            heartCounts.text = heartcount.toString()
            commentsCounts.text = commentcount.toString()
            writingBody.text = boardtext
        }
    }
    private fun initRecycler() {
        datas.apply {
            add(CommentData(id = "게시판 id" ,nickname = "장구벌레",time = "2021/12/20", comment = "좋은 일 하셨어요!!"))
            add(CommentData(id = "게시판 id" ,nickname = "짬뽕",time = "2022/01/03", comment = "대단해요"))
            add(CommentData(id = "게시판 id" ,nickname = "가나다라",time = "2022/01/05", comment = "멋져요"))
            boardreadAdapter.datas = datas
            boardreadAdapter.notifyDataSetChanged()
        }
        //내가 쓴 댓글( 댓글 닉네임 = 내 닉네임 일경우) 댓글 수정,삭제버튼 visibility true 로 바꾸기
    }
}