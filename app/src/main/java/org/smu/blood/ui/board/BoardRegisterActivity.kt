package org.smu.blood.ui.board

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.smu.blood.NavigationActivity
import org.smu.blood.R
import org.smu.blood.ui.board.BoardRegisterAlert
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext


class BoardRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_register)

        var registerButton = findViewById<TextView>(R.id.register_button)

        //글 작성 후 메인으로 이동
        registerButton.setOnClickListener {
            //글 작성 버튼 누르면 팝업 띄워주고
            val dlg = BoardRegisterAlert(this)
            dlg.callFunction()
            dlg.show()

            dlg.setOnDismissListener {

            }
            //메인으로 이동하고
            //finish()

        }
    }
}