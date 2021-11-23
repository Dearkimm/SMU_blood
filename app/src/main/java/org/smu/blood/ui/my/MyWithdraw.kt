package org.smu.blood.ui.my

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import org.smu.blood.R

class MyWithdraw(context: Context) :
    Dialog(context, android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: MyWithdraw? = null

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.dialog_withdraw_alert)
        dialog = this
        var ok = findViewById<Button>(R.id.btn_not)

        ok.setOnClickListener {
            dismiss()
        }

    }

}