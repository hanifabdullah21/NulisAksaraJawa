package com.singpaulee.sinauaksarajawawithknn.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.singpaulee.sinauaksarajawawithknn.R
import com.singpaulee.sinauaksarajawawithknn.adapter.HighscoreAdapter
import com.singpaulee.sinauaksarajawawithknn.connection.AppConfig
import com.singpaulee.sinauaksarajawawithknn.connection.DataInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_highscores.*
import org.jetbrains.anko.toast

class HighscoresActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscores)

        getListHighscores()
    }

    @SuppressLint("CheckResult")
    private fun getListHighscores() {
        var highscore = AppConfig.retrofitConfig(this)
            .create(DataInterface::class.java)
            .getListHighscore("listHighscore")

        highscore.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if (response.success as Boolean){
                    val listHighscore = response?.highscore
                    listHighscore?.sortByDescending { it.score }

                    val adapter = HighscoreAdapter(this@HighscoresActivity, listHighscore)
                    val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

                    hia_rv_highscore.layoutManager = layoutManager
                    hia_rv_highscore.adapter = adapter

                    hia_lottie_loading.visibility = View.GONE
                    hia_rv_highscore.visibility = View.VISIBLE

                }else{
                    toast("ERROR")
                }
            }, {

            })
    }
}
