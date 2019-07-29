package com.singpaulee.sinauaksarajawawithknn.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.singpaulee.sinauaksarajawawithknn.R
import com.singpaulee.sinauaksarajawawithknn.fragment_dialog.DialogInputUsernameFragment
import com.singpaulee.sinauaksarajawawithknn.fragment_dialog.DialogQuizResultFragment
import kotlinx.android.synthetic.main.activity_menu.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MenuActivity : AppCompatActivity(), View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        mea_iv_title_menu.setOnClickListener(this)
        mea_iv_sinau_nulis.setOnClickListener(this)
        mea_iv_quiz.setOnClickListener(this)
        mea_iv_highscore.setOnClickListener(this)
        mea_iv_about_app.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            mea_iv_sinau_nulis -> {
                startActivity<ListHanacarakaActivity>("action" to "sinau")
            }
            mea_iv_quiz -> {
                startActivity<QuizActivity>()
            }
            mea_iv_about_app -> {
//                startActivity<TrainingResultActivity>()
                startActivity<AboutAppActivity>()
            }
            mea_iv_highscore -> {
                startActivity<HighscoresActivity>()
            }
            mea_iv_title_menu -> {
                startActivity<ListHanacarakaActivity>("action" to "tambahData")
            }
        }
    }
}
