package org.smu.blood.ui.board

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import org.smu.blood.R

class BoardDeleteConfirmAlert(context: Context): Dialog(context, android.R.style.Theme_Translucent_NoTitleBar) {
    var dialog: BoardDeleteConfirmAlert? = null

    fun callFunction(){
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow

        setContentView(R.layout.dialog_board_delete_confirm)
        dialog = this
        val button = findViewById<Button>(R.id.confirm)

        // dialog 사라지게
        button.setOnClickListener {
            dismiss()
        }
    }
}