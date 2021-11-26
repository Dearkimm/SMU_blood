package org.smu.blood.ui.board

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.*
import org.smu.blood.R

class BoardRegisterAlert(context: Context) :
    Dialog(context, android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: BoardRegisterAlert? = null

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.board_register_alert)
        dialog = this
        var ok = findViewById<Button>(R.id.board_notregister)

        ok.setOnClickListener {
            dismiss()
        }

    }

}