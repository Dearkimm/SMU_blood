package org.smu.blood.ui.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R

class BoardActivity : AppCompatActivity() {
    lateinit var boardAdapter: BoardAdapter
    val datas = mutableListOf<BoardData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        initRecycler()
    }
    private fun initRecycler() {
        boardAdapter = BoardAdapter(this)
        var recyclerview = findViewById<RecyclerView>(R.id.rc_board_list)
        recyclerview.adapter = boardAdapter


        datas.apply {
            add(BoardData(title = "너무 급박했던 수혈과정",nickname = "kim202",time = "30분 전",heartcount = 1982))
            add(BoardData(title = "희귀혈액형 수혈과정 공유",nickname = "Eun304",time = "1시간 전",heartcount = 234))
            add(BoardData(title = "용산지역 혈액 수급",nickname = "yenomg34",time = "3시간 전",heartcount = 1002))
            add(BoardData(title = "Rh-의 헌혈자 구하기",nickname = "yeziyezi",time = "4일 전",heartcount = 67))
            add(BoardData(title = "희귀혈액형 지정헌혈 후기",nickname = "장구벌레",time = "지난 주",heartcount = 25))

            boardAdapter.datas = datas
            boardAdapter.notifyDataSetChanged()

        }
    }
}