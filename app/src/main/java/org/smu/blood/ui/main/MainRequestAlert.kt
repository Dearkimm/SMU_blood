package org.smu.blood.ui.main

import android.content.Context
import android.app.Dialog
import android.view.WindowManager
import android.widget.*
import org.smu.blood.R
import org.smu.blood.ui.board.BoardDeleteAlert

class MainRequestAlert (context: Context):
    Dialog(context, android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: MainRequestAlert? = null

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.dialog_main_request_confirm)
        dialog = this
        var ndel = findViewById<Button>(R.id.request_confirm)


        ndel.setOnClickListener {
            dismiss()
        }
    } //ㄲ
}