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
import org.smu.blood.R
import org.smu.blood.api.MainService
import org.smu.blood.api.database.Apply
import org.smu.blood.databinding.FragmentMainReadBinding
import org.smu.blood.ui.NavigationActivity
import org.smu.blood.ui.main.MainFragment
import org.smu.blood.ui.main.MainReadFragment
import org.smu.blood.ui.my.MyCardActivity
import org.smu.blood.ui.my.MyRequestFragment
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MapApplicationActivity : AppCompatActivity() {
    var confirmState = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_blood_notice)


        //확인 버튼
        var okay = findViewById<Button>(R.id.notice_okay)

        //체크박스
        var check = findViewById<CheckBox>(R.id.n_checkbox)
        var check2 = findViewById<CheckBox>(R.id.n_checkbox2)

        //체크박스 옆 텍스트뷰
        var text = findViewById<TextView>(R.id.notice_tv)
        var text2 = findViewById<TextView>(R.id.notice_tv2)

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


            //확인 버튼 누르면 dialog 뜨게
        okay.setOnClickListener {
            if (check.isChecked && check2.isChecked) {
                val dlg = MapApplicationCompleteAlert(this) //헌혈신청완료 다이얼로그
                dlg.callFunction()
                dlg.show()

                dlg.setOnDismissListener {
                    confirmState = dlg.returnState()
                    if (confirmState) { //건너뛰기
                        goCardState = 0
                    } else { //마이페이지에서 신청 확인하기
                        goCardState = 1
                    }

                    // 서버에 보낼 Apply 정보 넣기
                    val applyInfo = HashMap<String, String>()
                    applyInfo["applyDate"] = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) + " " +
                            LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"))
                    applyInfo["requestId"] = MainFragment.request.requestId.toString()

                    // Apply 정보 서버에 보내기
                    MainService(this).registerApply(applyInfo){
                        Log.d("[REGISTER BLOOD APPLY] SEND APPLY INFO", "applyDate: ${applyInfo["applyDate"]}, requestId: ${applyInfo["requestId"]}")
                        if(it==true) Log.d("[REGISTER BLOOD APPLY]", "SUCCESS")
                        else Log.d("[REGISTER BLOOD APPLY]", "FAILURE")
                    }

                    val intent = Intent(this, NavigationActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
            } else
                Toast.makeText(this, "주의사항 확인 후 체크해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        var goCardState = 0
    }
}