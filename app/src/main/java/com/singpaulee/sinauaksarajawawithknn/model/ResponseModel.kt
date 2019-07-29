package com.singpaulee.sinauaksarajawawithknn.model

import com.google.gson.annotations.SerializedName

data class ResponseModel(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("jumlahData")
    val jumlahData: Int? = null,

    @field:SerializedName("jumlahAksara")
    val jumlahAksara: Int? = null,

    @field:SerializedName("data")
    val data: ArrayList<DataModel?>? = null,

    @field:SerializedName("highscores")
    val highscore: ArrayList<HighscoreModel>? = null
)