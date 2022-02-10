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
import org.smu.blood.util.shortToast
import java.time.LocalDateTime
import org.smu.blood.api.ReviewService
import org.smu.blood.api.database.Review
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class BoardRegisterActivity : AppCompatActivity() { //게시판 글쓰고 등록
    private lateinit var binding: ActivityBoardRegisterBinding
    var writingState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_board_register)
        binding = ActivityBoardRegisterBinding.inflate(layoutInflater)
        var registerButton = findViewById<TextView>(R.id.register_button)
        var reviewService = ReviewService(this)

        lateinit var title : String
        lateinit var contents : String
        var reviewId: Int = 0
        var userId: String? = null
        var userNickname: String? = null
        var heartCount: Int = 0
        lateinit var writeTime: String

        // 날짜, 시간 가져오기
        val time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        writeTime = "$date $time"

        //글 등록 버튼 클릭
        registerButton.setOnClickListener {
            title = findViewById<EditText>(R.id.writing_edit_title).text.toString()
            contents = findViewById<EditText>(R.id.writing_edit_body).text.toString()

            var reviewInfo = Review()
            if (title.isNotBlank() && contents.isNotBlank()) {
                //등록 전 팝업
                val dlg = BoardRegisterAlert(this)
                dlg.callFunction()
                dlg.show()
                dlg.setOnDismissListener {
                    writingState = dlg.returnState()
                    if (writingState) {
                        //글쓰기 데이터 DB에 넘기기
                        reviewInfo.reviewId = 0
                        reviewInfo.userId = null
                        reviewInfo.nickname = null
                        reviewInfo.title = title
                        reviewInfo.contents = contents
                        reviewInfo.writeTime = writeTime
                        reviewInfo.likeNum = 0 // 초기 좋아요 0으로 설정
                        Log.d("[REVIEW WRITE] reviewInfo", "$reviewInfo")
                        reviewService.reviewWrite(reviewInfo){
                            if(it != null) {
                                reviewId = it.reviewId!!
                                userId=  it.userId
                                userNickname = it.nickname
                                heartCount = it.likeNum!!
                                Log.d("[REVIEW WRITE]", "$it")
                            }
                            else Log.d("[REVIEW WRITE]", "FAILURE")
                        }
                        //BoardFragment로 데이터 넘겨주기
                        val boardfragment = BoardFragment()
                        val bundle = Bundle()
                        bundle.apply {
                            putInt("reviewId", reviewId)
                            putString("userId", userId)
                            putString("nickname", userNickname)
                            putString("title",title)
                            putString("contents",contents)
                            putString("time",writeTime)
                            putInt("heartCount", heartCount)
                        }
                        boardfragment.arguments = bundle

                        finish()
                        val intent = Intent(this,BoardWritingActivity::class.java)
                        startActivity(intent)
                    }
                }
            }else
                shortToast("빈 칸이 있습니다")
        }
    }
}