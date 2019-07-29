package com.singpaulee.sinauaksarajawawithknn.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.singpaulee.sinauaksarajawawithknn.R
import com.singpaulee.sinauaksarajawawithknn.algorythm.KNearestNeighbour
import com.singpaulee.sinauaksarajawawithknn.connection.AppConfig
import com.singpaulee.sinauaksarajawawithknn.connection.DataInterface
import com.singpaulee.sinauaksarajawawithknn.model.DataModel
import com.singpaulee.sinauaksarajawawithknn.model.ResponseModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_training_result.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class TrainingResultActivity : AppCompatActivity() {

    private var listData: ArrayList<DataModel?>? = null
    private var listDataTest: ArrayList<DataModel?>? = null

    var totalTrue = 0
    var totalFalse = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_result)

        getTrainingData()
        getTrainingDataTest()

        tra_btn_test.onClick {
            if (tra_edt_k.text.toString().isBlank()) {
                return@onClick
            } else {
                totalTrue = 0
                totalFalse = 0
                testData()
            }
        }
    }

    private fun testData() {
        val KnnAlgoryhm = KNearestNeighbour()

        GlobalScope.launch {
            for (i in 0 until listDataTest?.size!!) {

                var resultNewData = KnnAlgoryhm.executeWithKnn(
                    listData,
                    listDataTest?.get(i),
                    listDataTest?.get(i)?.classification?.aksara.toString(),
                    tra_edt_k.text.toString().toInt()
                )

                kotlinx.coroutines.delay(200)

                resultNewData?.classification?.result.toString()
                if (resultNewData?.classification?.result.toString() == listDataTest?.get(i)?.classification?.aksara.toString()) {
                    totalTrue += 1
                } else {
                    totalFalse += 1
                }
                Log.d("TOTAL", "$totalTrue, $totalFalse")

                Thread.sleep(200)

                Log.d("TOTAL", "${listDataTest!!.size} ${totalTrue.toDouble()}")

                val totalTruePercent = totalTrue.toDouble() / listDataTest!!.size.toDouble() * 100
                val totalFalsePercent = totalFalse.toDouble() / listDataTest!!.size.toDouble() * 100

                Log.d("TOTAL", "$totalTruePercent $totalFalsePercent")

                tra_tv_true.text = "$totalTruePercent"
                tra_tv_false.text = "$totalFalsePercent"
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun getTrainingData() {
        val information: Observable<ResponseModel> = AppConfig.retrofitConfig(this)
            .create<DataInterface>(DataInterface::class.java)
            .getData("HA", "listData")

        information.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                if (it.success as Boolean) {
                    listData = it.data
                    tra_tv_total_data.text = listData?.size.toString()
                } else {
                    toast(it.message.toString())
                }
            }, {
                toast(it.toString())
                Log.d("ERRORCUK", it.toString())
                finish()
            })
    }

    @SuppressLint("CheckResult")
    private fun getTrainingDataTest() {
        val information: Observable<ResponseModel> = AppConfig.retrofitConfig(this)
            .create<DataInterface>(DataInterface::class.java)
            .getData("HA", "listDataTest")

        information.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                if (it.success as Boolean) {
                    listDataTest = it.data
                    tra_tv_total_data_test.text = listDataTest?.size.toString()
                } else {
                    toast(it.message.toString())
                }
            }, {
                toast(it.toString())
                Log.d("ERRORCUK", it.toString())
                finish()
            })
    }
}
