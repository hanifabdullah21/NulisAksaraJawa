package com.singpaulee.sinauaksarajawawithknn.model

import com.google.gson.annotations.SerializedName

data class HighscoreModel(

    @field:SerializedName("score")
    val score: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)