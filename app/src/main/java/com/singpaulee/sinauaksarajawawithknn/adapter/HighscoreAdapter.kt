package com.singpaulee.sinauaksarajawawithknn.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singpaulee.sinauaksarajawawithknn.R
import com.singpaulee.sinauaksarajawawithknn.model.HighscoreModel
import kotlinx.android.synthetic.main.item_highscore.view.*

class HighscoreAdapter(val context:Context, val list:ArrayList<HighscoreModel>?):
    RecyclerView.Adapter<HighscoreAdapter.ViewHolder>() {

    lateinit var itemView: View

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        itemView = LayoutInflater.from(context).inflate(R.layout.item_highscore, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if (list?.size!! > 10) 10 else list.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(list!![p1], p1)
    }

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(highscoreModel: HighscoreModel?, number: Int) {
            itemView.ihi_tv_number.text = ""+(number+1)
            itemView.ihi_tv_username.text = highscoreModel?.name.toString()
            itemView.ihi_tv_score.text = highscoreModel?.score.toString()
        }

    }

}