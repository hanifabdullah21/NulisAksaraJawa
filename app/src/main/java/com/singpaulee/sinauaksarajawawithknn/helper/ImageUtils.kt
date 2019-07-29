package com.singpaulee.sinauaksarajawawithknn.helper

import android.gesture.Gesture
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import com.singpaulee.sinauaksarajawawithknn.HelperClass
import com.singpaulee.sinauaksarajawawithknn.R
import com.singpaulee.sinauaksarajawawithknn.algorythm.CannyAlgorythm
import com.singpaulee.sinauaksarajawawithknn.algorythm.MedianFilter
import com.singpaulee.sinauaksarajawawithknn.model.RgbModel

class ImageUtils{
    private val IMAGE_WIDTH = 32
    private val IMAGE_HEIGHT = 32
    private val IMAGE_INSET = 8

    fun edgeDetection(gesture: Gesture?): Bitmap{
        var bitmap = convertGestureToBitmap(gesture)

        //Filter image using median filter
        bitmap  = MedianFilter().medianFilterAlgorithm(bitmap)

        //Process edge detection using Canny Algorythm
        bitmap = CannyAlgorythm().process(bitmap)

        return bitmap
    }

    fun edgeDetectionBmp(bmp: Bitmap?): Bitmap{
        var bitmap = bmp

        bitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT,false)

        //Process edge detection using Canny Algorythm
        bitmap = CannyAlgorythm().process(bitmap!!)

        return bitmap
    }

    private fun convertGestureToBitmap(gesture: Gesture?): Bitmap{
        var bitmap = gesture?.toBitmap(IMAGE_WIDTH,IMAGE_HEIGHT,IMAGE_INSET, R.color.colorBlack)
        bitmap = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT,false)
        return bitmap
    }

    /** Method ini menghitung RGB pixel pada sebi=uah gambar bitmap
     *
     * @param image gambar yang akan dihitung pikselnya
     *
     * @return RGB Model
     * */
    fun countPixelOfBitmap(image: Bitmap): RgbModel {
        val bitmapWidth = image.width
        val bitmapHeight = image.height

        var red = 0
        var green = 0
        var blue = 0
        for (i in 0 until bitmapWidth) {
            for (j in 0 until bitmapHeight) {
                val pixel = image.getPixel(i, j)
                red += Color.red(pixel)
                green += Color.green(pixel)
                blue += Color.blue(pixel)
            }
        }

        Log.d("RGB", "$red $green $blue")
        val mRed = HelperClass().setNumberbehindComma(meanPixels(image, red))
        val mGreen = HelperClass().setNumberbehindComma(meanPixels(image, green))
        val mBlue = HelperClass().setNumberbehindComma(meanPixels(image, blue))
        Log.d("RGB", "$mRed $mGreen $mBlue")
        return RgbModel(redPixel = mRed, greenPixel = mGreen, bluePixel = mBlue)
    }

    private fun meanPixels(image:Bitmap, totalPixel:Int): Double{
        return totalPixel.toDouble()/(image.height*image.width)
    }

}