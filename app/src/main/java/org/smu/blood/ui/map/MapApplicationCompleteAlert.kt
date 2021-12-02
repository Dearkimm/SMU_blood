package org.smu.blood.ui.map

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.WindowManager
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import org.smu.blood.R
import org.smu.blood.ui.main.MainRequestAlert
import org.smu.blood.ui.my.MyModActivity

class MapApplicationCompleteAlert (context: Context) :
    Dialog(context,android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: MapApplicationCompleteAlert? = null

    //전자문진or건너뛰기 변수
    var confirmState = false

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.dialog_map_application_confirm)
        dialog = this
        var ok = findViewById<Button>(R.id.btn_ok) //마이페이지에서 신청 확인하기
        var skip = findViewById<Button>(R.id.btn_skip) //건너뛰기

        ok.setOnClickListener { //마이페이지에서 신청 확인하기
            confirmState = false
            returnState()
            dismiss()
        }
        skip.setOnClickListener { //건너뛰기
            confirmState = true
            returnState()
            dismiss()
        }
    }
    fun returnState(): Boolean{
        return confirmState
    }

}