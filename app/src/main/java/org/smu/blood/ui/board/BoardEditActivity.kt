package org.smu.blood.ui.board

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import android.widget.EditText
import org.smu.blood.util.shortToast
import org.smu.blood.api.ReviewService
import org.smu.blood.databinding.ActivityBoardEditBinding
import org.smu.blood.databinding.ActivityNavigationBinding
import org.smu.blood.util.replaceFragment
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

//게시판 내가 쓴 글 수정
class BoardEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardEditBinding
    var writingState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_board_edit)
        binding = ActivityBoardEditBinding.inflate(layoutInflater)
        var registerButton = findViewById<TextView>(R.id.register_button)

        lateinit var editTitle : String
        lateinit var editContents : String
        lateinit var editTime : String

        var reviewService = ReviewService(this)

        // 수정 날짜, 시간 가져오기
        val time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        editTime = "$date $time"

        //원래 내용 게시글에서 가져오기
        val originTitle = intent.getStringExtra("originTitle")
        val originBody = intent.getStringExtra("originContent")
        val reviewId = intent.getStringExtra("reviewId")

        // 원래 내용 EditText에 보여주기
        val edittitle = findViewById<EditText>(R.id.writing_edit_title)
        val editbody = findViewById<EditText>(R.id.writing_edit_body)

        //원래 내용 받아오기
        edittitle.setText(originTitle)
        editbody.setText(originBody)


        //글 등록 버튼 클릭
        registerButton.setOnClickListener {
            // 수정하는 내용 EditText에서 받아오기
            editTitle = edittitle.text.toString()
            editContents = editbody.text.toString()

            if (editTitle.isNotBlank() && editContents.isNotBlank()) {
                //등록 전 팝업
                val dlg = BoardRegisterAlert(this)
                dlg.callFunction()
                dlg.show()
                dlg.setOnDismissListener {
                    writingState = dlg.returnState()
                    if (writingState) {
                        //DB 게시글 내용 수정
                        Log.d("[EDIT REVIEW]", "$writingState, title: $editTitle, content: $editContents")
                        Log.d("[EDIT REVIEW]", "edit state: $writingState, editTime: $editTime")
                        val editInfo = HashMap<String,String>()
                        editInfo["editTitle"] = editTitle
                        editInfo["editContent"] = editContents
                        editInfo["editTime"] = editTime
                        editInfo["reviewId"] = reviewId!!

                        reviewService.reviewEdit(editInfo){
                            if(it==true){
                                // 수정 후 정보가 업데이트된 게시글 가져오기
                                val binding2: ActivityNavigationBinding = ActivityNavigationBinding.inflate(layoutInflater)
                                setContentView(binding2.root)
                                replaceFragment(binding2.fragmentContainer, BoardFragment::class.java, true)
                            }
                            else Log.d("[EDIT REVIEW]", "FAILED")
                        }
                    }
                }
            }else
                shortToast("빈 칸이 있습니다")
        }
    }
}