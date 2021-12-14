package org.smu.blood.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.smu.blood.databinding.ActivityNavigationBinding
import org.smu.blood.ui.board.BoardFragment
import org.smu.blood.ui.main.MainFragment
import org.smu.blood.ui.main.MainReadFragment
import org.smu.blood.ui.main.MainRequestFragment
import org.smu.blood.ui.main.MainSearchHospitalFragment
import org.smu.blood.ui.map.MapActivity
import org.smu.blood.ui.map.MapFragment
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
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(binding.fragmentContainer, MainFragment::class.java, withAnim = false)

        configureBottomNav()
    }

    private fun configureBottomNav() {
        binding.btnMain.setOnClickListener {
            replaceFragment(binding.fragmentContainer, MainFragment::class.java, withAnim = false)
        }
        binding.btnMap.setOnClickListener {
            //replaceFragment(binding.fragmentContainer, MapFragment::class.java, withAnim = false)
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
        binding.btnReview.setOnClickListener {
            replaceFragment(binding.fragmentContainer, BoardFragment::class.java, withAnim = false)
        }
    }

    fun navigateMain(){
        replaceFragment(binding.fragmentContainer, MainFragment::class.java, withAnim = false)

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

    fun showFinishToast() {
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finish()
            return
        }
        shortToast("한 번 더 누르시면 종료됩니다.")
        backPressedTime = System.currentTimeMillis()
    }

}