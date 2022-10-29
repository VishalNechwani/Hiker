package com.example.hiker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hiker.view.CompanyListFragment

class HikerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.companyListFragment)
        if(fragment is CompanyListFragment){
           val isVisible = fragment.isVisible
           if(isVisible){
              finish()
           }else{
               super.onBackPressed()
           }
        }else{
            super.onBackPressed()
        }
    }
}