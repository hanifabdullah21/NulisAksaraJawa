package com.singpaulee.sinauaksarajawawithknn.activity

import android.annotation.SuppressLint
import android.gesture.*
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.singpaulee.sinauaksarajawawithknn.HelperClass
import com.singpaulee.sinauaksarajawawithknn.R
import com.singpaulee.sinauaksarajawawithknn.algorythm.CannyAlgorythm
import com.singpaulee.sinauaksarajawawithknn.algorythm.MedianFilter
import com.singpaulee.sinauaksarajawawithknn.connection.AppConfig
import com.singpaulee.sinauaksarajawawithknn.connection.DataInterface
import com.singpaulee.sinauaksarajawawithknn.helper.ImageUtils
import com.singpaulee.sinauaksarajawawithknn.model.ResponseModel
import com.singpaulee.sinauaksarajawawithknn.model.RgbModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_data_training.*
import org.jetbrains.anko.selector
import org.jetbrains.anko.toast

class AddDataTrainingActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "DrawNewDataTraining"

    var aksaraJawa: String? = null
    var aksaraJawaLatin: String? = null

    var mRedPixel: Double? = 0.0
    var mGreenPixel: Double? = 0.0
    var mBluePixel: Double? = 0.0

    var mResult: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data_training)

        /*TODO Mendapatkan data yang dilempar dari HanacarakaAdapter.class*/
        aksaraJawa = intent.getStringExtra("aksaraJawa")
        aksaraJawaLatin = intent.getStringExtra("aksaraJawaLatin")

        getInformationData()

        adta_tv_aksara_jawa.text = aksaraJawa
        adta_tv_aksara_jawa_latin.text = aksaraJawaLatin

        adta_iv_save.setOnClickListener(this)
        adta_iv_delete.setOnClickListener(this)
    }

    /** Fungsi ini digunakan untuk mendapatkan informasi jumlah data pada database
     * dengan melakukan request menggunakan retrofit
     *
     * */
    @SuppressLint("CheckResult", "SetTextI18n")
    private fun getInformationData() {
        val information: Observable<ResponseModel> = AppConfig.retrofitConfig(this)
            .create<DataInterface>(DataInterface::class.java)
            .getData(aksaraJawaLatin.toString(), "information")

        information.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.success as Boolean) {
                    adta_tv_total_data.text = "Jumlah Data: " + it.jumlahData.toString()
                    adta_tv_total_data_true.text = "Jumlah Data Aksara $aksaraJawaLatin: " + it.jumlahAksara.toString()

                } else {
                    toast(it.message.toString())
                }
            }, {
                toast(it.toString())
            })
    }

    /**
     * Sebuah fungsi yang digunakan untuk membuka dialog berupa pilihan hasil gambar
     * dengan pilihan benar atau salah
     * */
    private fun openSelectionResult() {
        val answer = listOf("postDataTraining", "postDataTrainingTest")
        selector("Gambar aksara-ne bener opo salah?", answer) { dialogInterface, i ->
            when (answer[i]) {
                "postDataTraining" -> postNewData(answer[i])
                "postDataTrainingTest" -> postNewData(answer[i])
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun postNewData(action: String) {
        val newData: Observable<ResponseModel> = AppConfig.retrofitConfig(this)
            .create<DataInterface>(DataInterface::class.java)
            .postData(
                action,
                aksaraJawaLatin.toString(),
                mRedPixel,
                mGreenPixel,
                mBluePixel,
                aksaraJawaLatin.toString()
            )
        newData.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.success as Boolean) {
                    toast(it.message.toString())
                    reDrawGestureView()
                    getInformationData()
                } else {
                    toast(it.message.toString())
                }
            }, {
                toast(it.message.toString())
            })
    }

    /** Fungsi ini digunakan untuk me-reset Gesture agar pengguna bisa menggambar ulang Gesture
     *
     * */
    private fun reDrawGestureView() {
        adta_dv_aksara.clear()
    }

    override fun onClick(v: View?) {
        when (v) {
            adta_iv_save -> {
                if (!adta_dv_aksara.isCleared){
                    toast("oiiii")

                    //TODO Lakukan konversi gesture ke dalam bitmap lalu lakukan deteksi tepi untuk mendapatkan nilai piksel
                    processEdgeDetection()

                    openSelectionResult()

                }else {
                    toast("Silahkan gambar aksara jawa dahulu")
                }
            }
            adta_iv_delete -> {
                reDrawGestureView()
            }
        }
    }

    private fun processEdgeDetection() {

        val bitmap : Bitmap? = adta_dv_aksara.exportDrawing()
        val imageUtils = ImageUtils()
        val image = imageUtils.edgeDetectionBmp(bitmap!!)
        iv_bitmap.setImageBitmap(image)
        val rgb = imageUtils.countPixelOfBitmap(image)
        mRedPixel = rgb.redPixel
        mGreenPixel = rgb.greenPixel
        mBluePixel = rgb.bluePixel
    }
}
