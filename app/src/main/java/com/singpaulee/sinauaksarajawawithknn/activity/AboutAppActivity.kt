package com.singpaulee.sinauaksarajawawithknn.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.singpaulee.sinauaksarajawawithknn.R
import kotlinx.android.synthetic.main.activity_about_app.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick

class AboutAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)

        aaa_tv_title.onClick {
            startActivity(intentFor<TrainingResultActivity>())
        }
    }
}
