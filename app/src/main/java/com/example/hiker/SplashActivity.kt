package com.example.hiker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.content.Intent




class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.myLooper()!!).postDelayed ({
            val mIntent = Intent(this@SplashActivity, HikerActivity::class.java)
            startActivity(mIntent)
            finish()
        }, 2000)
    }
}