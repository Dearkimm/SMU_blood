package org.smu.blood.ui.map

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import org.smu.blood.R

class MapGPSAlert (context: Context) :
    Dialog(context,android.R.style.Theme_Translucent_NoTitleBar){
    var dialog: MapGPSAlert? = null

    fun callFunction() {
        val lpWindow = WindowManager.LayoutParams()
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        lpWindow.dimAmount = 0.5f
        window!!.attributes = lpWindow
        setContentView(R.layout.dialog_map_location_alert)
        dialog = this
        var ok = findViewById<Button>(R.id.map_locationbtn)

        ok.setOnClickListener {
            dismiss()
        }

    }
}
