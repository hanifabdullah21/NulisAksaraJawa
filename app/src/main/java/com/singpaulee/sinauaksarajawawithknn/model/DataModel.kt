package com.singpaulee.sinauaksarajawawithknn.model

import com.google.gson.annotations.SerializedName

data class DataModel(

    @field:SerializedName("classification")
    var classification: ClassificationModel? = null,

    @field:SerializedName("rgb")
    var rgb: RgbModel? = null
)