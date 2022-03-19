package org.smu.blood.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import org.smu.blood.R

class SessionManager(context: Context) {
    var pref: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

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
        pref.edit().remove("token").apply()
    }
}