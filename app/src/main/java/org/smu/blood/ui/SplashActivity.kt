package org.smu.blood.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.R
import org.smu.blood.api.SessionManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var intent: Intent
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            var sessionManager = SessionManager(this)
            if(sessionManager.fetchToken()!=null){
                 intent = Intent(this, NavigationActivity::class.java)
            }
            else { intent = Intent(this, LoginActivity::class.java) }
            startActivity(intent)
            finish()
        }, 1000)
    }
}