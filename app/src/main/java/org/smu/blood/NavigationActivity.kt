package org.smu.blood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.smu.blood.databinding.ActivityNavigationBinding
import org.smu.blood.ui.board.BoardFragment
import org.smu.blood.ui.main.MainFragment
import org.smu.blood.ui.map.MapFragment
import org.smu.blood.util.replaceFragment

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}