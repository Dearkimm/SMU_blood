package org.smu.blood.ui.main

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.smu.blood.R

class HospitalSearchActivity : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_search)

        //데이터베이스
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }
}