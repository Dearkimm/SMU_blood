package org.smu.blood.ui.my.Card

import android.app.Dialog
import android.view.WindowManager
import android.widget.*
import org.smu.blood.R
import android.content.Context

class CardDeleteAlert(context: Context) :
    Dialog(context, android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: CardDeleteAlert? = null

    //카드 삭제 변수
    var deleteState = false

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.dialog_card_delete)
        dialog = this
        var ndel = findViewById<Button>(R.id.card_notdelete)
        var del = findViewById<Button>(R.id.card_delete)

        ndel.setOnClickListener {
            deleteState = false
            returnState()
            dismiss()
        }
        del.setOnClickListener {
            Toast.makeText(context,"삭제되었습니다",Toast.LENGTH_SHORT).show()
            deleteState = true
            returnState()
            dismiss()

        }


    }
    fun returnState(): Boolean{
        return deleteState
    }

}