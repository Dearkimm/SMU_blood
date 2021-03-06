package org.smu.blood.ui.board

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.*
import org.smu.blood.R
import android.widget.Toast


class BoardRegisterAlert(context: Context) :
    Dialog(context, android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: BoardRegisterAlert? = null

    //글쓰기 상태 변수
    var writingState = false

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.dialog_board_register)
        dialog = this

        //버튼
        var nok = findViewById<Button>(R.id.board_notregister)
        var ok = findViewById<Button>(R.id.board_register)


        nok.setOnClickListener {
            writingState = false
            returnState()
            dismiss() //취소눌렀을때
        }

        ok.setOnClickListener {
            Toast.makeText(context,"등록되었습니다",Toast.LENGTH_SHORT).show()
            writingState = true
            returnState()
            dismiss()

        }

    }

    fun returnState(): Boolean{
        return writingState
    }

}

