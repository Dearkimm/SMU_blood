package org.smu.blood.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.smu.blood.NavigationActivity
import org.smu.blood.R
import org.smu.blood.databinding.FragmentMainRequestBinding
import org.smu.blood.ui.base.BaseFragment

class MainRequestFragment : BaseFragment<FragmentMainRequestBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainRequestBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureRequestNavigation()

        //뷰바인딩 쓸 수 있게 되어있어서 findViewById말로 binding 쓰시면 되고
        //프래그먼트에서는 여기서 쓰시면 돼요
        //얘처럼...binding.xml변수이름카멜식.메소드이름  이런 식으
        // 로
       binding.type2A.setOnClickListener {
           binding.type2A.setBackgroundResource(R.drawable.bg_btn_red_5dp)
           binding.type2A.setTextColor(Color.WHITE)
           binding.type2B.setTextColor(Color.BLACK)
           binding.type2Ab.setTextColor(Color.BLACK)
           binding.type2O.setTextColor(Color.BLACK)
           binding.type2B.setBackgroundResource(R.drawable.bg_btn_type)
           binding.type2O.setBackgroundResource(R.drawable.bg_btn_type)
           binding.type2Ab.setBackgroundResource(R.drawable.bg_btn_type)
       }
        binding.type2B.setOnClickListener {
            binding.type2B.setBackgroundResource(R.drawable.bg_btn_red_5dp)
            binding.type2B.setTextColor(Color.WHITE)
            binding.type2A.setTextColor(Color.BLACK)
            binding.type2Ab.setTextColor(Color.BLACK)
            binding.type2O.setTextColor(Color.BLACK)
            binding.type2A.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2O.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2Ab.setBackgroundResource(R.drawable.bg_btn_type)
        }
        binding.type2O.setOnClickListener{
            binding.type2O.setBackgroundResource(R.drawable.bg_btn_red_5dp)
            binding.type2O.setTextColor(Color.WHITE)
            binding.type2B.setTextColor(Color.BLACK)
            binding.type2Ab.setTextColor(Color.BLACK)
            binding.type2A.setTextColor(Color.BLACK)
            binding.type2B.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2A.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2Ab.setBackgroundResource(R.drawable.bg_btn_type)
        }
        binding.type2Ab.setOnClickListener {
            binding.type2Ab.setBackgroundResource(R.drawable.bg_btn_red_5dp)
            binding.type2Ab.setTextColor(Color.WHITE)
            binding.type2B.setTextColor(Color.BLACK)
            binding.type2A.setTextColor(Color.BLACK)
            binding.type2O.setTextColor(Color.BLACK)
            binding.type2B.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2O.setBackgroundResource(R.drawable.bg_btn_type)
            binding.type2A.setBackgroundResource(R.drawable.bg_btn_type)
        }
        binding.mCheckbox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) { binding.mcheckboxTv.setTextColor(Color.RED) }
            else { binding.mcheckboxTv.setTextColor(Color.BLACK) }
        }

        //라디오버튼 텍스트 컬러
        binding.radio1.setOnClickListener {
            binding.radio1.setTextColor(Color.RED)
            binding.radio2.setTextColor(Color.BLACK)
            binding.radio3.setTextColor(Color.BLACK)
            binding.radio4.setTextColor(Color.BLACK)
            binding.radio5.setTextColor(Color.BLACK)
        }
        binding.radio2.setOnClickListener {
            binding.radio2.setTextColor(Color.RED)
            binding.radio1.setTextColor(Color.BLACK)
            binding.radio3.setTextColor(Color.BLACK)
            binding.radio4.setTextColor(Color.BLACK)
            binding.radio5.setTextColor(Color.BLACK)
        }
        binding.radio3.setOnClickListener {
            binding.radio3.setTextColor(Color.RED)
            binding.radio2.setTextColor(Color.BLACK)
            binding.radio1.setTextColor(Color.BLACK)
            binding.radio4.setTextColor(Color.BLACK)
            binding.radio5.setTextColor(Color.BLACK)
        }
        binding.radio4.setOnClickListener {
            binding.radio4.setTextColor(Color.RED)
            binding.radio2.setTextColor(Color.BLACK)
            binding.radio3.setTextColor(Color.BLACK)
            binding.radio1.setTextColor(Color.BLACK)
            binding.radio5.setTextColor(Color.BLACK)
        }
        binding.radio5.setOnClickListener {
            binding.radio5.setTextColor(Color.RED)
            binding.radio2.setTextColor(Color.BLACK)
            binding.radio3.setTextColor(Color.BLACK)
            binding.radio4.setTextColor(Color.BLACK)
            binding.radio1.setTextColor(Color.BLACK)
        }

    }

    private fun configureRequestNavigation() {
        binding.btnRegister.setOnClickListener {
            if (binding.metHnum.text.isNullOrBlank() || binding.metGnum.text.isNullOrBlank() || binding.metPname.text.isNullOrBlank() || binding.metPnum.text.isNullOrBlank()
                || binding.metStart.text.isNullOrBlank() || binding.metEnd.text.isNullOrBlank()) {
                Toast.makeText(activity, "빈 칸이 있습니다", Toast.LENGTH_SHORT).show()}
            else {
                val dlg = MainRequestAlert(requireContext())
                dlg.callFunction()
                dlg.show()
                (activity as NavigationActivity).navigateMain()}
        }

        binding.imgbHos.setOnClickListener {
            (activity as NavigationActivity).navigateRequestToSearchHospital()
        }
    }
}