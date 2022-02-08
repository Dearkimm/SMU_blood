package org.smu.blood.ui.board

import android.app.Dialog
import android.view.WindowManager
import android.widget.*
import org.smu.blood.R
import android.content.Context

class CommentDeleteAlert(context: Context) :
    Dialog(context, android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: CommentDeleteAlert? = null

    //댓글 삭제 변수
    var deleteState = false

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.dialog_comment_delete)
        dialog = this
        var ndel = findViewById<Button>(R.id.board_notdelete)
        var del = findViewById<Button>(R.id.board_delete)

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