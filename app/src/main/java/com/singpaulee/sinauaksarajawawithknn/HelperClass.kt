package com.singpaulee.sinauaksarajawawithknn

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.singpaulee.sinauaksarajawawithknn.model.RgbModel
import java.lang.NumberFormatException

class HelperClass {

    /** Method ini digunakan untuk melakukan pengecekan apakah ada koneksi internet atau tidak
     * @param context adalah penunjuk / context pada kelas mana method ini dipanggil
     *
     * */
    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    /** Method ini digunakan untuk mengkonversi bilangan Float
     * menjadi hanya punya 2 angka di belakang koma
     *
     * */
    fun makeTwoNumberBehindComma(value: Float?): Float {
        val resultNumber: Float = try {
            String.format("%.3f", value).toFloat()
        } catch (nfe: NumberFormatException) {
            value!!.toFloat()
        }
        Log.d("HelperClass", "$resultNumber")
        return resultNumber
    }

    /** Method ini digunakan untuk mengkonversi bilangan Float
     * menjadi hanya punya 2 angka di belakang koma
     *
     * */
    fun setNumberbehindComma(value: Double?): Double {
        val resultNumber: Double = try {
            String.format("%.3f", value?.toDouble()).toDouble()
        } catch (nfe: NumberFormatException) {
            value!!.toDouble()
        }
        Log.d("HelperClass", "$resultNumber")
        return resultNumber
    }
}