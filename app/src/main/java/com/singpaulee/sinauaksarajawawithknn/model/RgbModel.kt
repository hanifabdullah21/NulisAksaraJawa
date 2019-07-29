package com.singpaulee.sinauaksarajawawithknn.model

import com.google.gson.annotations.SerializedName

data class RgbModel(
    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("red_pixel")
    var redPixel: Double? = null,

    @field:SerializedName("green_pixel")
    var greenPixel: Double? = null,

    @field:SerializedName("blue_pixel")
    var bluePixel: Double? = null


)