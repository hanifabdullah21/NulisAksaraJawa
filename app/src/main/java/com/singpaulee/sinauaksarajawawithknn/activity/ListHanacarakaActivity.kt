package com.singpaulee.sinauaksarajawawithknn.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.singpaulee.sinauaksarajawawithknn.R
import com.singpaulee.sinauaksarajawawithknn.adapter.HanacarakaAdapter
import kotlinx.android.synthetic.main.activity_list_hanacaraka.*

class ListHanacarakaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_hanacaraka)

        val action : String? = intent.getStringExtra("action")

        var layoutManager = GridLayoutManager(this,5)
        var adapter = HanacarakaAdapter(this, action)

        lha_rv_hanacaraka.layoutManager = layoutManager
        lha_rv_hanacaraka.adapter = adapter

    }
}


