package org.smu.blood.ui.map

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import org.smu.blood.R

class MapCheckConditionAlert (context: Context) :
        Dialog(context,android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: MapCheckConditionAlert? = null

    //전자문진or건너뛰기 변수
    var checkState = false

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.dialog_map_check_condition)
        dialog = this
        var ok = findViewById<Button>(R.id.btn_ok) //전자문진하기
        var skip = findViewById<Button>(R.id.btn_skip) //건너뛰어서 헌혈 신청화면

        ok.setOnClickListener { //전자문진하기
            checkState = false
            returnState()
            dismiss()
        }
        skip.setOnClickListener { //건너뛰어서 헌혈 신청화면
            checkState = true
            returnState()
            dismiss()
        }
    }
    fun returnState(): Boolean{
        return checkState
    }

}

