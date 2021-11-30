package org.smu.blood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.smu.blood.databinding.ActivityNavigationBinding
import org.smu.blood.ui.board.BoardFragment
import org.smu.blood.ui.main.MainFragment
import org.smu.blood.ui.main.MainReadFragment
import org.smu.blood.ui.main.MainRequestFragment
import org.smu.blood.ui.main.MainSearchHospitalFragment
import org.smu.blood.ui.map.MapFragment
import org.smu.blood.ui.my.MyRequestFragment
import org.smu.blood.ui.my.MyFragment
import org.smu.blood.util.replaceFragment

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding

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
            replaceFragment(binding.fragmentContainer, MapFragment::class.java, withAnim = false)
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
}