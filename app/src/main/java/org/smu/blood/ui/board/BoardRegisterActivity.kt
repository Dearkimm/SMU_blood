package org.smu.blood.ui.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import android.widget.EditText
import org.smu.blood.databinding.ActivityBoardRegisterBinding
import org.smu.blood.databinding.ActivityLoginBinding
import org.smu.blood.ui.SignUpActivity
import org.smu.blood.util.shortToast
import java.time.LocalDateTime


class BoardRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardRegisterBinding
    var writingState = false
    lateinit var title : String
    lateinit var contents : String
    val dateandtime: LocalDateTime = LocalDateTime.now()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_register)
        binding = ActivityBoardRegisterBinding.inflate(layoutInflater)
        var registerButton = findViewById<TextView>(R.id.register_button)

        //글 작성 후 메인으로 이동
        registerButton.setOnClickListener {
            title = findViewById<EditText>(R.id.writing_edit_title).text.toString()
            contents = findViewById<EditText>(R.id.writing_edit_body).text.toString()
            if (title.isNotBlank() && contents.isNotBlank()) {
                //글 작성 버튼 누르면 팝업 띄워주고
                val dlg = BoardRegisterAlert(this)
                dlg.callFunction()
                dlg.show()
                dlg.setOnDismissListener {
                    writingState = dlg.returnState()

                    if (writingState) {
                        //title = findViewById<EditText>(R.id.writing_edit_title).text.toString()
                        //contents = findViewById<EditText>(R.id.writing_edit_body).text.toString()
                        //게시판으로 이동하고
                        //글쓰기 데이터
                        Log.d("글쓰기 데이터", writingState.toString() + ", " + title + ", " + contents)
                        Log.d("현재날짜시간", writingState.toString() + ", " + dateandtime)
                        finish()
                    }
                }
            }else
                shortToast("빈 칸이 있습니다")
        }
    }
}