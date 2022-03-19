package org.smu.blood.ui.my

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.smu.blood.R

class MyLogoutDialog(context: Context) :
Dialog(context, android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: MyLogoutDialog? = null
    var logoutState = false

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.dialog_logout_alert)
        dialog = this
        var ok = findViewById<Button>(R.id.btn_skip)
        var logout = findViewById<Button>(R.id.btn_ok)

        ok.setOnClickListener { //로그아웃 취소
            logoutState = false
            returnState()
            dismiss()
        }
        logout.setOnClickListener { //로그아웃하기
            Toast.makeText(context,"로그아웃되었습니다",Toast.LENGTH_SHORT).show()
            logoutState = true
            //returnState()
            dismiss()
            Firebase.auth.signOut()
        }
    }fun returnState(): Boolean{
        return logoutState
    }
}