package com.singpaulee.sinauaksarajawawithknn.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singpaulee.sinauaksarajawawithknn.R
import com.singpaulee.sinauaksarajawawithknn.activity.AddDataTrainingActivity
import com.singpaulee.sinauaksarajawawithknn.activity.LearnAksaraActivity
import com.singpaulee.sinauaksarajawawithknn.helper.ListHanacaraka
import kotlinx.android.synthetic.main.item_hanacaraka.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class HanacarakaAdapter(val context: Context, val action: String?) :
    RecyclerView.Adapter<HanacarakaAdapter.ViewHolder>() {

    lateinit var itemView: View

    val listHanacaraka = ListHanacaraka().listOfHanacaraka()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        itemView = LayoutInflater.from(p0.context).inflate(R.layout.item_hanacaraka, p0, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listHanacaraka.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(listHanacaraka[p1], action)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemsHanacaraka: Array<String>, action: String?) {
            itemView.hi_tv_aksara_jawa.text = itemsHanacaraka[0]
            itemView.hi_tv_aksara_jawa_latin.text = itemsHanacaraka[1]

            itemView.onClick {
                when (action) {
                    "sinau" -> {
                        itemView.context.startActivity<LearnAksaraActivity>(
                            "aksaraJawa" to itemsHanacaraka[0],
                            "aksaraJawaLatin" to itemsHanacaraka[1]
                        )
                    }
                    "tambahData" -> itemView.context.startActivity<AddDataTrainingActivity>(
                        "aksaraJawa" to itemsHanacaraka[0],
                        "aksaraJawaLatin" to itemsHanacaraka[1]
                    )
                }
            }
        }
    }
}