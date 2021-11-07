package org.smu.blood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.smu.blood.ui.board.BoardFragment
import org.smu.blood.ui.main.MainFragment
import org.smu.blood.ui.map.MapFragment

class NavigationActivity : AppCompatActivity() {
    //프래그먼트 관련 변수
    private var fragmentManager: FragmentManager? = null
    private var fragmentMap: MapFragment? = null
    private var fragmentMain: MainFragment? = null
    private var fragmentBoard: BoardFragment? = null
    private var transaction: FragmentTransaction? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        //네이게이션 버튼
        var mainButton = findViewById<ImageButton>(R.id.btn_main)
        var mapButton = findViewById<ImageButton>(R.id.btn_map)
        var reviewButton = findViewById<ImageButton>(R.id.btn_review)

        //프래그먼트 관련 초기화
        fragmentManager = supportFragmentManager
        fragmentMap = MapFragment()
        fragmentMain = MainFragment()
        fragmentBoard = BoardFragment()
        transaction = fragmentManager?.beginTransaction()
        transaction!!.replace(R.id.frame, fragmentMain!!).commitAllowingStateLoss()
        //메인 프래그먼트 띄우기
        mainButton.setOnClickListener {
            transaction = fragmentManager?.beginTransaction()
            transaction!!.replace(R.id.frame, fragmentMain!!).commitAllowingStateLoss()
        }

        //지도 프래그먼트 띄우기
        mapButton.setOnClickListener {
            transaction = fragmentManager?.beginTransaction()
            transaction!!.replace(R.id.frame, fragmentMap!!).commitAllowingStateLoss()
        }

        //후기 프래그먼트 띄우기
        reviewButton.setOnClickListener {
            transaction = fragmentManager?.beginTransaction()
            transaction!!.replace(R.id.frame, fragmentBoard!!).commitAllowingStateLoss()
        }




    }
}