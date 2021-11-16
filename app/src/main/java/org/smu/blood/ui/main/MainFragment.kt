package org.smu.blood.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import org.smu.blood.R
import org.smu.blood.ui.my.MyActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {

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
        var rootView = inflater.inflate(R.layout.fragment_main, container, false)
        var requestButton = rootView.findViewById<Button>(R.id.btn_request)
        var myButton = rootView.findViewById<ImageButton>(R.id.btn_my)

        //지정 헌혈 요청 화면으로 이동
        requestButton.setOnClickListener {
            val intent = Intent(context, MainRequestActivity::class.java)
            startActivity(intent)
        }

        //마이페이지로 이동
        myButton.setOnClickListener {
            val intent = Intent(context, MyActivity()::class.java)
            startActivity(intent)
        }



        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}