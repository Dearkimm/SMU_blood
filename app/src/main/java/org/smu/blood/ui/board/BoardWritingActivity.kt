package org.smu.blood.ui.board

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.databinding.ActivityBoardReadBinding

class BoardWritingActivity : AppCompatActivity() {
    private var _binding: ActivityBoardReadBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        _binding = ActivityBoardReadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoardRead()

    }

    private fun initBoardRead() {
        val intent = intent
        var title = intent.getStringExtra("title")
        var nickname = intent.getStringExtra("nickname")
        var time = intent.getStringExtra("time")
        var heartcount = intent.getIntExtra("heartcount", 0)
        var boardtext = "안녕하세요, 얼마 전 Rh- O형의 헌혈이 시급하다는 소식을 듣고 Rh- 혈액은 워낙 구하기가 힘들다는 걸 알기에 지정 헌혈에 참여했습니다. 이번에는 헌혈자이지만 미래에 제가 혈액이 필요한 요청자가 될 수 있다는 걸 희귀 혈액형인 저는 너무나도 잘 압니다. 빠른 시일 내에 회복하시길 바라요. Rh- 환자분들 다들 힘내세요!"

        val apply = binding.apply {
            writingTitle.text = title
            writingNickname.text = nickname
            writingTime.text = time.toString()
            heartCounts.text = heartcount.toString()
            writingBody.text = boardtext
        }
    }
}