package com.singpaulee.sinauaksarajawawithknn.activity

import android.annotation.SuppressLint
import android.gesture.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.singpaulee.sinauaksarajawawithknn.HelperClass
import com.singpaulee.sinauaksarajawawithknn.R
import com.singpaulee.sinauaksarajawawithknn.algorythm.KNearestNeighbour
import com.singpaulee.sinauaksarajawawithknn.connection.AppConfig
import com.singpaulee.sinauaksarajawawithknn.connection.DataInterface
import com.singpaulee.sinauaksarajawawithknn.fragment_dialog.DialogAddDataFragment
import com.singpaulee.sinauaksarajawawithknn.helper.ImageUtils
import com.singpaulee.sinauaksarajawawithknn.model.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_data_training.*
import kotlinx.android.synthetic.main.activity_learn_aksara.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.toast

class LearnAksaraActivity : AppCompatActivity(), View.OnClickListener, DialogAddDataFragment.DialogListener {

    val TAG = "LearnAksaraActivity"

    var aksaraJawaLatin: String? = null    //Menampung aksara yang dilempar dari daftar aksara

    var listData: ArrayList<DataModel?>? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn_aksara)

        //TODO Set Title Sinau
        aksaraJawaLatin = intent.getStringExtra("aksaraJawaLatin")
        ala_tv_title.text = "Sinau Nulis Aksara $aksaraJawaLatin"

        hideDrawer(true)
        getListData()

        ala_iv_delete.setOnClickListener(this)
        ala_btn_done.setOnClickListener(this)
    }

    private fun hideDrawer(isHide: Boolean) {
        if(isHide){
            ala_ll_grup_gesture.backgroundColor = resources.getColor(R.color.colorWhite)
            ala_dv_aksara.visibility = View.INVISIBLE
            ala_lottie_anim.visibility = View.VISIBLE
        }else{
            ala_ll_grup_gesture.background = resources.getDrawable(R.drawable.background_wood)
            ala_dv_aksara.visibility = View.VISIBLE
            ala_lottie_anim.visibility = View.GONE
        }

    }

    /** Fungsi ini bertujuan untuk menganalisa data gesture yang baru saja dibuat.
     * Mendapatkan nilai prediksi, bound right, bound left, bound top dan bound bottom
     * kemudian akan diklasifikasikan dengan menggunakan metode KNN untuk mendapatkan hasil klasifikasinya.
     * Dan yang terakhir, data baru tersebut akan ditambahkan ke dalam database.
     *
     * */
    private fun analyzeData() {
        if (!ala_dv_aksara.isCleared) {

            //TODO Do process edge detection
            val bmp = ala_dv_aksara.exportDrawing()
            val imageUtils = ImageUtils()
            val image = imageUtils.edgeDetectionBmp(bmp)

            val newClassification = ClassificationModel(aksara = aksaraJawaLatin)
            val newRgb = imageUtils.countPixelOfBitmap(image)

            var newData = DataModel(newClassification, newRgb)

            //TODO Classification with KNN
            val KnnAlgoryhm = KNearestNeighbour()
            val resultNewData = KnnAlgoryhm.executeWithKnn(listData, newData, aksaraJawaLatin.toString())

            Log.d(TAG, "Result $aksaraJawaLatin ${resultNewData?.classification?.result}")
            val result = resultNewData?.classification?.result.toString()==aksaraJawaLatin
            openDialog(result)

            //TODO Post a new data
//            postNewData(resultNewData)
        } else {
            toast("Silahkan gambar aksara jawa dahulu")
        }
    }

//    @SuppressLint("CheckResult")
//    private fun postNewData(newDataModel: DataModel?) {
//        val newData: Observable<ResponseModel> = AppConfig.retrofitConfig(this)
//            .create<DataInterface>(DataInterface::class.java)
//            .postData(
//                "postDataTraining",
//                newDataModel?.classification?.aksara.toString(),
//                (0).toDouble(),
//                (0).toDouble(),
//                newDataModel?.rgb?.redPixel,
//                newDataModel?.rgb?.greenPixel,
//                newDataModel?.rgb?.bluePixel,
//                newDataModel?.classification?.result
//            )
//        newData.subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                if (it.success as Boolean) {
//                    Log.d(TAG, "Post new data is ${it.message}")
//                    openDialog(newDataModel?.classification?.result as Boolean)
//                } else {
//                    toast(it.message.toString())
//                }
//            }, {
//                toast(it.message.toString())
//            })
//    }

    /** Fungsi ini digunakan untuk membuka dialog (DialogFragment)
     * yang akan menampilkan animasi nilai untuk pengguna
     * dari hasil pembuatan gesture
     *
     * @param result adalah nilai yang akan ditampilkan: true untuk benar dan false untuk salah
     *
     * */
    private fun openDialog(result: Boolean) {
        val dialogFragment = DialogAddDataFragment()
        val bundleArg = Bundle()
        bundleArg.putBoolean("result", result)
        bundleArg.putBoolean("isFullScreen", true)
        bundleArg.putBoolean("isAlertDialog", false)
        dialogFragment.arguments = bundleArg
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        dialogFragment.show(ft, "dialog")
    }

    /** Metode atau fungsi ini digunakan untuk mengeksekusi
     * hasil listener yang didapat dari aktivitas pada dialog
     *
     * @param message berupa pesan yang akan diolah menjadi informasi atau aktivitas tertentu
     *
     * */
    override fun onFinishEditDialog(message: String) {
        when (message) {
            "reset" -> reDrawGestureView()
            "change" -> finish()
        }
    }

    /** Fungsi ini digunakan untuk mendapatkan daftar data yang ada pada database
     * sesuai dengan aksara yang dipilih
     *
     * */
    @SuppressLint("CheckResult")
    private fun getListData() {
        val information: Observable<ResponseModel> = AppConfig.retrofitConfig(this)
            .create<DataInterface>(DataInterface::class.java)
            .getData("HA", "listData")

        information.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                hideDrawer(false)
                if (it.success as Boolean) {
                    listData = it.data
                    Log.d(TAG, "Jumlah Data ${it.data?.size}")
                } else {
                    toast(it.message.toString())
                }
            }, {
                toast(it.toString())
                finish()
            })
    }

    /** Fungsi ini digunakan untuk me-reset Gesture agar pengguna bisa menggambar ulang Gesture
     *
     * */
    private fun reDrawGestureView() {
        ala_dv_aksara.clear()
    }

    override fun onClick(v: View?) {
        when (v) {
            ala_iv_delete -> {
                reDrawGestureView()
            }
            ala_btn_done -> {
                analyzeData()
            }
        }
    }
}
