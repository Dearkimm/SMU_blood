package org.smu.blood.ui.map

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.messaging.FirebaseMessaging
import org.smu.blood.R
import org.smu.blood.api.MainService
import org.smu.blood.api.MessagingService
import org.smu.blood.api.MyPageService
import org.smu.blood.api.database.Request
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.ui.main.MainFragment
import org.smu.blood.ui.my.MyRequestFragment
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// 지도에서 헌혈하기, 지정 헌혈 요청에서 헌혈하기
class MapApplicationActivity : AppCompatActivity() {
    var confirmState = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_blood_notice)


        //확인 버튼
        val okay = findViewById<Button>(R.id.notice_okay)

        //체크박스
        val check = findViewById<CheckBox>(R.id.n_checkbox)
        val check2 = findViewById<CheckBox>(R.id.n_checkbox2)

        //체크박스 옆 텍스트뷰
        val text = findViewById<TextView>(R.id.notice_tv)
        val text2 = findViewById<TextView>(R.id.notice_tv2)

        //체크박스 누르면 텍스트뷰 컬러
        check.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                text.setTextColor(Color.RED)
            } else {
                text.setTextColor(Color.BLACK)
            }
        }

        check2.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                text2.setTextColor(Color.RED)
            } else {
                text2.setTextColor(Color.BLACK)
            }
        }


        // 헌혈 신청 처리
        okay.setOnClickListener {
            if (check.isChecked && check2.isChecked) {

                // 서버에 보낼 Apply 정보 넣기
                val applyInfo = HashMap<String, String>()
                applyInfo["applyDate"] = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + " " +
                        LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"))
                applyInfo["requestId"] = MainFragment.request.requestId.toString()

                // Apply 정보 서버에 보내기
                MainService(this).registerApply(applyInfo){
                    Log.d("[REGISTER BLOOD APPLY] SEND APPLY INFO", "applyDate: ${applyInfo["applyDate"]}, requestId: ${applyInfo["requestId"]}")
                    when(it){
                        401 ->{ // 중복 신청 오류
                            val dialog = MapApplyRejectAlert(this)
                            dialog.callFunction()
                            dialog.show()

                            dialog.setOnDismissListener {
                                Log.d("[REGISTER BLOOD APPLY]", "same user apply in request")
                                // 메인 페이지로 이동
                                startActivity(Intent(this, NavigationActivity::class.java))
                                this.finish()
                            }
                        }
                        200 -> { // 헌혈 신청 성공
                            Log.d("[REGISTER BLOOD APPLY]", "SUCCESS")

                            // 알림 처리 시 요청 정보 필요
                            MyPageService(this).requestOfApply(MainFragment.request.requestId){
                                if(it!= null){
                                    MyRequestFragment.myRequest = it
                                    Log.d("[MY REQUEST]", MyRequestFragment.myRequest.toString())
                                }
                            }

                            // 요청자에게 알림 보내기



                            //헌혈신청완료 다이얼로그
                            val dlg = MapApplicationCompleteAlert(this)
                            dlg.callFunction()
                            dlg.show()

                            dlg.setOnDismissListener {
                                confirmState = dlg.returnState()
                                goCardState = if (confirmState) { //건너뛰기
                                    0
                                } else { //마이페이지에서 신청 확인하기
                                    1
                                }

                                val intent = Intent(this, NavigationActivity::class.java)
                                startActivity(intent)
                                this.finish()
                            }
                        }
                        else -> Log.d("[REGISTER BLOOD APPLY]", "FAILURE") // 토큰 유효 X or 요청 X, 서버 접속 X, 서버 응답 실패
                    }
                }

            } else
                Toast.makeText(this, "주의사항 확인 후 체크해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        var goCardState = 0
    }
}