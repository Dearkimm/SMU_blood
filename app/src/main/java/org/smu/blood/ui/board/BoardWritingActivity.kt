package org.smu.blood.ui.board

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.databinding.ActivityBoardReadBinding

class BoardWritingActivity : AppCompatActivity() {
    private var _binding: ActivityBoardReadBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        var boardtext = ""

        val apply = binding.apply {
            writingTitle.text = title
            writingNickname.text = nickname
            writingTime.text = time.toString()
            heartCounts.text = heartcount.toString()
            writingBody.text = boardtext
        }
    }
}