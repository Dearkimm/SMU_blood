package org.smu.blood.ui.my.Card

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Dimension
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.databinding.ActivityCardRequestBinding
import org.smu.blood.ui.board.BoardDeleteAlert
import org.smu.blood.util.shortToast

class CardRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardRequestBinding
    //카드 삭제 다이얼로그 관련변수
    var deleteState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityCardRequestBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_card_request)

        val timelist = findViewById<LinearLayout>(R.id.card_request_list)
        for(i: Int in 1..3){
            val time = TextView(this@CardRequestActivity)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
            layoutParams.setMargins(0,0,0,5)
            time.layoutParams = layoutParams
            time.text = "$i 텍스트뷰" //요청 일시넣기
            time.setTextSize(Dimension.SP,13.0f)
            time.typeface = resources.getFont(R.font.notosans_light)
            time.includeFontPadding = false
            time.setTextColor(Color.BLACK)
            timelist.addView(time)
        }
        val btn = findViewById<Button>(R.id.request_delete)
        btn.setOnClickListener{
            val dlg = CardDeleteAlert(this)
            dlg.callFunction()
            dlg.show()
            dlg.setOnDismissListener {
                deleteState = dlg.returnState()
                if(deleteState){
                    //카드 삭제
                    finish()
                }
            }
        }


    }
}