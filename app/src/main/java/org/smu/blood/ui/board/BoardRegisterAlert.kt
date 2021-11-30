package org.smu.blood.ui.board

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.WindowManager
import android.widget.*
import org.smu.blood.R
import org.smu.blood.ui.my.MyModActivity

class BoardRegisterAlert(context: Context) :
    Dialog(context, android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: BoardRegisterAlert? = null

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.dialog_board_register)
        dialog = this
        var nok = findViewById<Button>(R.id.board_notregister)
        var ok = findViewById<Button>(R.id.board_register)

        nok.setOnClickListener {
            dismiss() //취소눌렀을때
        }
        ok.setOnClickListener {
//            val intent = Intent(this, fragment_board()::class.java)
//            startActivity(intent)
        }

    }

}