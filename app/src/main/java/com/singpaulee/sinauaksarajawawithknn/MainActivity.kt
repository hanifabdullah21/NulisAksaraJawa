package com.singpaulee.sinauaksarajawawithknn

import android.gesture.Gesture
import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.gesture.GestureOverlayView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
    , GestureOverlayView.OnGesturePerformedListener {

    var mGesture: Gesture? = null
    var gestureLibrary: GestureLibrary? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gesture3)
        if (!gestureLibrary!!.load()) {
            finish()
        }

        gov_writing_area.addOnGesturePerformedListener(this)
    }

    override fun onGesturePerformed(overlay: GestureOverlayView?, gesture: Gesture?) {
        var list = gestureLibrary?.recognize(gesture)

        Log.d("SCORE", list.toString())

        Log.d("SCORE", list!!.get(0).score.toString())
        Log.d("SCORE", list.get(1).score.toString())
//        Log.d("SCORE", overlay?.alpha.toString())
        Log.d("SCORE", gesture?.strokesCount.toString())
        Log.d("SCORE", gesture?.boundingBox.toString())
        Log.d("SCORE", gesture?.id.toString())
        Log.d("SCORE", gesture?.length.toString())
        Log.d("SCORE", gesture?.strokes!!.get(0).points.toString())
    }
}
