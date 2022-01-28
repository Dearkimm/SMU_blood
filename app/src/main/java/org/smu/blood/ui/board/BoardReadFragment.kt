package org.smu.blood.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R
import org.smu.blood.databinding.ActivityBoardReadBinding
import org.smu.blood.databinding.ActivityLoginBinding
import org.smu.blood.databinding.FragmentBoardBinding
import org.smu.blood.databinding.FragmentMainReadBinding
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.MainFragment
import org.smu.blood.ui.board.BoardFragment
import org.w3c.dom.Comment
//--------------------------------사용안하는코드---------------
class BoardReadFragment : BaseFragment<FragmentBoardBinding>() {
    lateinit var boardreadAdapter: BoardReadAdapter
    lateinit var recyclerview: RecyclerView
    val datas = mutableListOf<CommentData>()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentBoardBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initBoardRead()

        //리사이클러뷰 어댑터
        boardreadAdapter = context?.let { BoardReadAdapter(it) }!!
        recyclerview = binding.root.findViewById<RecyclerView>(R.id.rc_board_list)
        recyclerview.adapter = boardreadAdapter
        initRecycler()
    }
    private fun initBoardRead() {
        var title = ""
        var nickname = ""
        var time = ""
        var heartcount = 0
        var commentcount = 0
        var boardtext = ""

        binding.apply {
            ///
        }
    }
    private fun initRecycler() {
        datas.apply {
            add(CommentData(id = "게시판 id" ,nickname = "장구벌레",time = "2021/12/20", comment = "좋은 일 하셨어요!!"))
            add(CommentData(id = "게시판 id" ,nickname = "짬뽕",time = "2022/01/03", comment = "대단해요"))
            add(CommentData(id = "게시판 id" ,nickname = "가나다라",time = "2022/01/05", comment = "멋져요"))
            boardreadAdapter.datas = datas
            boardreadAdapter.notifyDataSetChanged()
        }
    }

}