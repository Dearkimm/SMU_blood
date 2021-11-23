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

import androidx.recyclerview.widget.RecyclerView

import org.smu.blood.R
import org.smu.blood.ui.board.BoardAdapter
import org.smu.blood.ui.board.BoardData
import org.smu.blood.ui.my.MyActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {

    //어댑터..?
    lateinit var mainAdapter: MainAdapter
    lateinit var recyclerview: RecyclerView
    val datas = mutableListOf<MainData>()
    //????????

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

        //리사이클러뷰 어댑터????????
        mainAdapter = context?.let { MainAdapter(it) }!!
        recyclerview = rootView.findViewById<RecyclerView>(R.id.rc_request_list)
        recyclerview.adapter = mainAdapter
        initmainRecycler()

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

    //리사이클러뷰 초기화????
    private fun initmainRecycler() {

        datas.apply {
            add(MainData(reqHospital = "서울특별시 서초구 강남연세사랑병원",reqBlood = "Rh+ A형 전혈",reqTime = "1시간 전",reqDate = "21.11.16-21.11.19",reqCount=0,circleBlood ="RH A+"))
            add(MainData(reqHospital = "서울특별시 성북구 고려대 안암병원",reqBlood = "Rh+ B형 전혈",reqTime = "1시간 전",reqDate = "21.11.16-21.11.20",reqCount=0,circleBlood="RH B+"))
            add(MainData(reqHospital = "서울특별시 서초구 강남연세사랑병원",reqBlood = "Rh- B형 전혈",reqTime = "3시간 전",reqDate = "21.11.18-21.11.22",reqCount=1,circleBlood="RH B-"))
            add(MainData(reqHospital = "서울특별시 강동구 강동성심병원",reqBlood = "Rh+ O형 전혈",reqTime = "4일 전",reqDate = "21.11.4-21.11.8",reqCount=2,circleBlood="RH O+"))
            add(MainData(reqHospital = "서울특별시 강동구 강동성심병원",reqBlood = "Rh+ O형 전혈",reqTime = "지난 주",reqDate = "21.10.30-21.10.31",reqCount=4,circleBlood="RH O+"))

            mainAdapter.datas = datas
            mainAdapter.notifyDataSetChanged()

        }
    }


}