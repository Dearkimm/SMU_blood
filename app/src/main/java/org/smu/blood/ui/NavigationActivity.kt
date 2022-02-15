package org.smu.blood.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.google.android.gms.common.util.CollectionUtils
import org.smu.blood.R
import org.smu.blood.api.database.MainRequest
import org.smu.blood.databinding.ActivityNavigationBinding
import org.smu.blood.ui.board.BoardFragment
import org.smu.blood.ui.main.MainFragment
import org.smu.blood.ui.main.MainReadFragment
import org.smu.blood.ui.main.MainRequestFragment
import org.smu.blood.ui.main.MainSearchHospitalFragment
import org.smu.blood.ui.map.MapActivity
import org.smu.blood.ui.map.MapApplicationActivity
import org.smu.blood.ui.my.MyRequestFragment
import org.smu.blood.ui.my.MyFragment
import org.smu.blood.util.popFragment
import org.smu.blood.util.replaceFragment
import org.smu.blood.util.shortToast


class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //replaceFragment(binding.fragmentContainer, MainFragment::class.java, withAnim = false)
        initMain()
        configureBottomNav()
    }

    private fun initMain() {
        Log.d("mapState", MapActivity.mapState.toString())
        when (MapActivity.mapState) {
            1 -> {
                replaceFragment(binding.fragmentContainer, MainReadFragment::class.java, withAnim = false)
                MapActivity.mapState = 0
            }

            else -> {
                when(MapApplicationActivity.goCardState){
                    1 -> {
                        replaceFragment(binding.fragmentContainer, MyRequestFragment::class.java, withAnim = false)

                    }
                    else ->replaceFragment(binding.fragmentContainer, MainFragment::class.java, withAnim = false)

                }
            }
        }

    }

    private fun configureBottomNav() {
        binding.btnMain.setOnClickListener {
            changeFragment(MainFragment())
        }
        binding.btnMap.setOnClickListener {
            //replaceFragment(binding.fragmentContainer, MapFragment::class.java, withAnim = false)
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        binding.btnReview.setOnClickListener {
            changeFragment(BoardFragment())
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    fun navigateMainToRequest(){
        replaceFragment(binding.fragmentContainer, MainRequestFragment::class.java, true)
    }

    fun navigateRequestToSearchHospital(){
        replaceFragment(binding.fragmentContainer, MainSearchHospitalFragment::class.java, true)
    }

    fun navigateMainToRead(){
        replaceFragment(binding.fragmentContainer, MainReadFragment::class.java, true)
    }

    fun navigateMainToMy(){
        replaceFragment(binding.fragmentContainer, MyFragment::class.java, true)
    }

    fun navigateMyToRequest(){
        replaceFragment(binding.fragmentContainer, MyRequestFragment::class.java, true)
    }

    fun navigateCardApply(){
        replaceFragment(binding.fragmentContainer, MyRequestFragment::class.java, true)
    }

    fun popMainSearchHospital(){
        popFragment(MainSearchHospitalFragment::class.java)
    }

    fun popMainRequest(){
        popFragment(MainRequestFragment::class.java)

    }

    fun popMainRead(){
        popFragment(MainReadFragment::class.java)
    }

    fun popMy(){
        popFragment(MyFragment::class.java)
    }

    fun popMyRequest(){
        popFragment(MyRequestFragment::class.java)
    }

    fun logoutAndfinish(){
        finish()
    }

    fun showFinishToast() {
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finish()
            return
        }
        shortToast("한 번 더 누르시면 종료됩니다.")
        backPressedTime = System.currentTimeMillis()
    }

}