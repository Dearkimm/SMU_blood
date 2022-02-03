package org.smu.blood.ui.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import android.widget.EditText
import org.smu.blood.databinding.ActivityBoardRegisterBinding
import org.smu.blood.databinding.ActivityLoginBinding
import org.smu.blood.ui.SignUpActivity
import org.smu.blood.util.shortToast
import java.time.LocalDateTime
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.ui.board.BoardAdapter
import org.smu.blood.ui.my.MyFragment
import java.nio.BufferUnderflowException
import java.nio.ByteOrder


class BoardRegisterActivity : AppCompatActivity() { //게시판 글 등록
    private lateinit var binding: ActivityBoardRegisterBinding
    var writingState = false
    lateinit var title : String
    lateinit var contents : String
    val dateandtime: LocalDateTime = LocalDateTime.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_board_register)
        binding = ActivityBoardRegisterBinding.inflate(layoutInflater)
        var registerButton = findViewById<TextView>(R.id.register_button)

        //글 등록 버튼 클릭
        registerButton.setOnClickListener {
            title = findViewById<EditText>(R.id.writing_edit_title).text.toString()
            contents = findViewById<EditText>(R.id.writing_edit_body).text.toString()
            if (title.isNotBlank() && contents.isNotBlank()) {
                //등록 전 팝업
                val dlg = BoardRegisterAlert(this)
                dlg.callFunction()
                dlg.show()
                dlg.setOnDismissListener {
                    writingState = dlg.returnState()
                    if (writingState) {
                        //글쓰기 데이터 DB에 넘기기
                        Log.d("글쓰기 데이터", writingState.toString() + ", " + title + ", " + contents)
                        Log.d("현재날짜시간", writingState.toString() + ", " + dateandtime)

                        //BoardFragment로 데이터 넘겨주기
                        val boardfragment = BoardFragment()
                        val bundle = Bundle()
                        bundle.apply {
                            putString("title",title)
                            putString("contents",contents)
                            putString("time",dateandtime.toString())
                        }
                        boardfragment.arguments = bundle

                        finish()

                    }
                }
            }else
                shortToast("빈 칸이 있습니다")
        }
    }
}