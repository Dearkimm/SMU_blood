package org.smu.blood.ui.my

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import org.smu.blood.R
import org.smu.blood.api.MyPageService
import org.smu.blood.api.SessionManager
import org.smu.blood.ui.LoginActivity

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
        val ok = findViewById<Button>(R.id.btn_not)
        val withdraw = findViewById<Button>(R.id.btn_withdraw)

        ok.setOnClickListener { //탈퇴하기 취소
            withdrawState = false
            returnState()
            dismiss()
        }
        withdraw.setOnClickListener { //탈퇴하기
            // 회원 정보 DB에서 삭제
            withdrawState = true
            MyPageService(context).withDraw{
                if(it==true) {
                    // token 삭제
                    SessionManager(context).removeToken()

                    // fcm token 삭제
                    FirebaseMessaging.getInstance().deleteToken()

                    // google 연동이면 삭제
                    if(LoginActivity.mGoogleSignInClient != null){
                        Log.d("[GOOGLE LOGIN]", "withdraw")
                        LoginActivity.mGoogleSignInClient?.revokeAccess()
                    }
                    Log.d("[WITHDRAW]", "SUCCESS")
                } else {
                    Log.d("[WITHDRAW]", "FAILURE")
                    withdrawState = false
                }
            }
            Toast.makeText(context,"탈퇴되었습니다", Toast.LENGTH_SHORT).show()
            returnState()
            dismiss()
        }
    }
    fun returnState(): Boolean{
        return withdrawState
    }

}