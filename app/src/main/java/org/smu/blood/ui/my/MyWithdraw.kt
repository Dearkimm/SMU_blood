package org.smu.blood.ui.my

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.smu.blood.R

class MyWithdraw(context: Context) :
    Dialog(context, android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: MyWithdraw? = null
    var withdrawState = false

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.dialog_withdraw_alert)
        dialog = this
        var ok = findViewById<Button>(R.id.btn_not)
        var withdraw = findViewById<Button>(R.id.btn_withdraw)

        ok.setOnClickListener { //탈퇴하기 취소
            withdrawState = false
            returnState()
            dismiss()
        }
        withdraw.setOnClickListener { //탈퇴하기
            Toast.makeText(context,"탈퇴되었습니다", Toast.LENGTH_SHORT).show()
            withdrawState = true
            //returnState()
            val user = Firebase.auth.currentUser!!

            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User account deleted.")
                    }
                }
            dismiss()
        }
    }fun returnState(): Boolean{
        return withdrawState
    }

}