package org.smu.blood.ui.my.Card

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.databinding.ActivityCardRequestBinding
import org.smu.blood.util.shortToast

class CardRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCardRequestBinding

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
            Log.d("sd","ss")
            shortToast("클릭")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("헌혈을 받으셨나요?")
            builder.setMessage("헌혈 요청 글이 삭제됩니다.")
            var listener = DialogInterface.OnClickListener { dialog, p1 ->
                when (p1) {
                    DialogInterface.BUTTON_POSITIVE ->
                        shortToast("요청을 삭제했습니다")
                    DialogInterface.BUTTON_NEGATIVE ->
                        shortToast("취소")
                }
            }
            builder.setPositiveButton("확인",listener)
            builder.setNegativeButton("취소",listener)
            builder.show()
        }


    }
}