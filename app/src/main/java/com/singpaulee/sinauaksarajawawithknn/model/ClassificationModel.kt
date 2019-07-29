package com.singpaulee.sinauaksarajawawithknn.model

import com.google.gson.annotations.SerializedName

data class ClassificationModel(

    @field:SerializedName("result")
    var result: String? = null,

    @field:SerializedName("aksara")
    var aksara: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    var distance: Double? = null
)