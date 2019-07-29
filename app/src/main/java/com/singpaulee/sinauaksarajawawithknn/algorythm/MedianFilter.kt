package com.singpaulee.sinauaksarajawawithknn.algorythm

import android.graphics.Bitmap
import android.graphics.Color
import java.util.*

class MedianFilter{
    fun medianFilterAlgorithm(bitmap: Bitmap): Bitmap {
        val pixel = IntArray(9)
        val R = IntArray(9)
        val G = IntArray(9)
        val B = IntArray(9)
        for (i in 1 until bitmap.width - 1) {
            for (j in 1 until bitmap.height - 1) {

                pixel[0] = bitmap.getPixel(i - 1, j - 1)
                pixel[1] = bitmap.getPixel(i - 1, j)
                pixel[2] = bitmap.getPixel(i - 1, j + 1)
                pixel[3] = bitmap.getPixel(i, j + 1)
                pixel[4] = bitmap.getPixel(i + 1, j + 1)
                pixel[5] = bitmap.getPixel(i + 1, j)
                pixel[6] = bitmap.getPixel(i + 1, j - 1)
                pixel[7] = bitmap.getPixel(i, j - 1)
                pixel[8] = bitmap.getPixel(i, j)

                for (k in 0..8) {
                    R[k] = Color.red(pixel[k])
                    G[k] = Color.green(pixel[k])
                    B[k] = Color.blue(pixel[k])
                }

                Arrays.sort(R)
                Arrays.sort(G)
                Arrays.sort(B)
                bitmap.setPixel(i, j, Color.rgb(R[4], G[4], B[4]))
            }
        }
        return bitmap
    }
}