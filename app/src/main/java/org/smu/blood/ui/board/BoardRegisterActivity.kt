package org.smu.blood.ui.board

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


class BoardRegisterActivity : AppCompatActivity() { //게시판 글 등록
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

        // 날짜, 시간 설정
        val time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))

        //글 등록 버튼 클릭
        registerButton.setOnClickListener {
            title = findViewById<EditText>(R.id.writing_edit_title).text.toString()
            contents = findViewById<EditText>(R.id.writing_edit_body).text.toString()
            var map = HashMap<String,String>()
            var reviewInfo = Review()
            var userId: String? = null
            var userNickname: String? = null
            var heartCount: Int = 0
            if (title.isNotBlank() && contents.isNotBlank()) {
                //등록 전 팝업
                val dlg = BoardRegisterAlert(this)
                dlg.callFunction()
                dlg.show()
                dlg.setOnDismissListener {
                    writingState = dlg.returnState()
                    if (writingState) {
                        //글쓰기 데이터 DB에 넘기기
                        reviewInfo.reviewId = reviewId
                        reviewInfo.userId = null
                        reviewInfo.title = title
                        reviewInfo.contents = contents
                        reviewInfo.writeDate = date
                        reviewInfo.writeTime = time
                        Log.d("[REVIEW WRITE] reviewInfo", reviewInfo.toString())
                        reviewService.myWrite(reviewInfo){
                            if(it != null) {
                                reviewId = it.reviewId!!
                                userId=  it.userId
                                userNickname = it.userNickname
                                heartCount = it.likeNum!!
                                Log.d("[REVIEW WRITE]", it.toString())
                            }
                            else Log.d("[REVIEW WRITE]", "FAILURE")
                        }
                        //BoardFragment로 데이터 넘겨주기
                        val boardfragment = BoardFragment()
                        val bundle = Bundle()
                        bundle.apply {
                            putInt("reviewId", reviewId)
                            putString("userId", userId)
                            putString("userNickname", userNickname)
                            putString("title",title)
                            putString("contents",contents)
                            putString("date", date.toString())
                            putString("time",time.toString())
                            putInt("heartCount", heartCount)
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