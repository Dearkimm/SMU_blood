package org.smu.blood.ui.board

import android.app.Dialog
import android.view.WindowManager
import android.widget.*
import org.smu.blood.R
import android.content.Context

class BoardDeleteAlert(context: Context) :
    Dialog(context, android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: BoardDeleteAlert? = null

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.dialog_board_delete)
        dialog = this
        var ok = findViewById<Button>(R.id.board_notdelete)

        ok.setOnClickListener {
            dismiss()
        }

    }

}