package org.smu.blood.ui.board

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import org.smu.blood.R

class BoardFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_board, container, false)

        //버튼
        var writeButton = rootView.findViewById<ImageButton>(R.id.btn_write)

        //글쓰기로 이동
        writeButton.setOnClickListener {
            val intent = Intent(context, BoardWritingActivity()::class.java)
            startActivity(intent)
        }
        return rootView
    }

    companion object {
         @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BoardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}