package org.smu.blood.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import org.smu.blood.R

class SessionManager(context: Context) {
    private var pref: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    // save token
    fun saveToken(token: String){
        val editor = pref.edit()
        editor.putString("token", token)
        editor.apply()
    }
    // fetch token
    fun fetchToken(): String? {
        Log.d("GET TOKEN", pref.getString("token",null).toString())
        return pref.getString("token", null)
    }
    // remove token
    fun removeToken(){
        pref.edit().remove("token").commit()
    }

    // delete heart state
    fun deleteHeartChecked(nickname: String, reviewId: Int){
        pref.edit().remove("${nickname}_${reviewId}").commit()
    }

    // check heart state
    fun checkHeart(nickname: String, reviewId: Int):Boolean{
        Log.d("[HEART EVENT2] HEART CHECK", "nickname:$nickname, reviewId: $reviewId")
        if(pref.contains("${nickname}_${reviewId}")){
            Log.d("[HEART EVENT2] HEART CHECK", pref.getBoolean("${nickname}_${reviewId}",false).toString())
            return pref.getBoolean("${nickname}_${reviewId}", false)
        }
        // 없는 경우
        return false
    }

    fun fetchHeart(nickname: String, reviewId: Int, check: Boolean){
        Log.d("[HEART EVENT2] FETCH HEART", "nickname:$nickname, reviewId: $reviewId")
        pref.edit().putBoolean("${nickname}_${reviewId}", check).apply()
    }

}