package com.singpaulee.sinauaksarajawawithknn.activity

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.singpaulee.sinauaksarajawawithknn.R
import com.singpaulee.sinauaksarajawawithknn.algorythm.CannyAlgorythm
import kotlinx.android.synthetic.main.activity_test_draw.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class TestDrawActivity : AppCompatActivity() {

    var bmp: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_draw)

        drawing_view.onClick {
            bmp = drawing_view.exportDrawing()
            image_result.setImageBitmap(bmp)
        }

        image_result.onClick {
            bmp = CannyAlgorythm().process(bmp!!)
            image_edge_detection.setImageBitmap(bmp)
        }


    }
}
