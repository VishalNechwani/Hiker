package com.example.hiker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class HikerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val count = fragmentManager.backStackEntryCount
        if(count == 0) {
            super.onBackPressed();
        }
        else{
            fragmentManager.popBackStack()
        }
    }
}