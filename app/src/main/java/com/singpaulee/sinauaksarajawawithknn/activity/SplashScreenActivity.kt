package com.singpaulee.sinauaksarajawawithknn.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.singpaulee.sinauaksarajawawithknn.R


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler().postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MenuActivity::class.java))
            finish()
        },3000)
    }
}
