package org.smu.blood.ui.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.smu.blood.databinding.FragmentBoardBinding
import org.smu.blood.databinding.FragmentMainReadBinding
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital
import org.smu.blood.ui.base.BaseFragment
import org.smu.blood.ui.main.MainFragment
import org.smu.blood.ui.board.BoardFragment

class BoardReadFragment : BaseFragment<FragmentBoardBinding>() {
    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentBoardBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initBoardRead()
    }
    private fun initBoardRead() {
        var title = ""
        var nickname = ""
        var time = ""
        var heartcount = 0
        var boardtext = ""

        binding.apply {
            ///
        }
    }

}