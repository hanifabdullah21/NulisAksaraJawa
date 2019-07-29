package com.singpaulee.sinauaksarajawawithknn.algorythm

import android.util.Log
import com.singpaulee.sinauaksarajawawithknn.HelperClass
import com.singpaulee.sinauaksarajawawithknn.helper.ListHanacaraka
import com.singpaulee.sinauaksarajawawithknn.model.DataModel
import kotlin.math.pow
import kotlin.math.sqrt

class KNearestNeighbour {

    val tag = "KNN"

    val K = 13

    /** Fungsi ini akan melakukan klasifikasi dengan menggunakan metode KNN yang terbagi menjadi 3 tahap :
     * 1. Penghitungan jarak menggunakan metode euclidian distance
     * 2. Pengurutan berdasarkan jarak terdekat
     * 3. Penentuan hasil menggunakan jumlah k
     *
     * Pada fungsi ini akanmengembalikan model data yang sudah berisi hasil klasifikasi.
     * */
    fun executeWithKnn(listData: ArrayList<DataModel?>?, newDataModel: DataModel?, aksara: String?): DataModel? {
        //TODO Tahap 1. Penghitungan jarak
        val listAfterCountDistance = countDistance(listData, newDataModel)
        Log.d(tag, "List After Count Distance ${listAfterCountDistance.toString()}")

        //TODO Tahap 2. Pengurutan berdasarkan jarak terpendek
        val listAfterSorting = sortList(listAfterCountDistance)
        for (i in 0 until listAfterSorting!!.size) {
            Log.d(
                tag,
                "List after sorting ${listAfterSorting?.get(i)?.classification?.id} ${listAfterSorting?.get(i)?.classification?.result}"
            )
        }

        //TODO Tahap 3. Mendapatkan hasil klasifikasi
        val resultClassification = specifyResult(listAfterSorting, K, aksara)
        Log.d(tag, "The result of the classification is $resultClassification")

        newDataModel?.classification?.result = resultClassification
        return newDataModel?.copy(classification = newDataModel.classification)
    }

    /** Fungsi ini akan melakukan klasifikasi dengan menggunakan metode KNN yang terbagi menjadi 3 tahap :
     * 1. Penghitungan jarak menggunakan metode euclidian distance
     * 2. Pengurutan berdasarkan jarak terdekat
     * 3. Penentuan hasil menggunakan jumlah k
     *
     * Pada fungsi ini akanmengembalikan model data yang sudah berisi hasil klasifikasi.
     * */
    fun executeWithKnn(listData: ArrayList<DataModel?>?, newDataModel: DataModel?, aksara: String?, k:Int): DataModel? {
        //TODO Tahap 1. Penghitungan jarak
        val listAfterCountDistance = countDistance(listData, newDataModel)
        Log.d(tag, "List After Count Distance ${listAfterCountDistance.toString()}")

        //TODO Tahap 2. Pengurutan berdasarkan jarak terpendek
        val listAfterSorting = sortList(listAfterCountDistance)
        for (i in 0 until listAfterSorting!!.size) {
            Log.d(
                tag,
                "List after sorting ${listAfterSorting?.get(i)?.classification?.id} ${listAfterSorting?.get(i)?.classification?.result}"
            )
        }

        //TODO Tahap 3. Mendapatkan hasil klasifikasi
        val resultClassification = specifyResult(listAfterSorting, k, aksara)
        Log.d(tag, "The result of the classification is $resultClassification")

        newDataModel?.classification?.result = resultClassification
        return newDataModel?.copy(classification = newDataModel.classification)
    }

    /** Method ini digunakan untuk menghitung jarak
     * dengan menggunakan metode Euclidian Distance
     *
     * @param listData adalah daftar data yang akan dihitung jaraknya
     * @param newDataModel adalah data yang akan dicari jaraknya
     *
     * */
    private fun countDistance(listData: ArrayList<DataModel?>?, newDataModel: DataModel?): ArrayList<DataModel?>? {
        for (i in listData?.indices!!) {
            Log.d(tag, "ListData "+ listData[i])

            //Pixel from new data
            val newRedPixel = newDataModel?.rgb?.redPixel
            val newGreenPixel = newDataModel?.rgb?.greenPixel
            val newBluePiel = newDataModel?.rgb?.bluePixel

            //Pixel from data training
            val redPixel = listData[i]?.rgb?.redPixel
            val greenPixel = listData[i]?.rgb?.greenPixel
            val bluePixel = listData[i]?.rgb?.bluePixel

            Log.d(tag, "Pixel $newRedPixel $redPixel")

            //difference of pixel (k-n)^2
            val differenceRed = (newRedPixel!! -redPixel!!).pow(2.toDouble())
            val differenceGreen = (newGreenPixel!! - greenPixel!!).pow(2.toDouble())
            val differenceBlue = (newBluePiel!! - bluePixel!!).pow(2.toDouble())

            var distance = sqrt(differenceRed + differenceGreen + differenceBlue)
            distance = HelperClass().setNumberbehindComma(distance)

            listData[i]?.classification?.distance = distance

            Log.d(tag, "Distance ${listData[i]?.classification?.id} ${listData[i]?.classification?.distance}")
        }

        return listData
    }

    /** Langkah kedua setelah penghitungan jarak adalah
     * mengurutkan daftar mulai dari jarak terdekat ke terjauh
     *
     * @param listData adalah daftar yang akan diurutkan
     *
     * */
    private fun sortList(listData: ArrayList<DataModel?>?): List<DataModel?>? {
        return listData?.sortedBy { it?.classification?.distance }
    }

    /** Langkah terakhir adalah menentukan nilai/hasil dari data baru apakah bernilai true atau false.
     *  Dengan cara mambandingkan jumlah data yang bernilai true dan false sebanyak k
     *
     * @param k adalah angka penentu untuk mendapatkan hasil yang akan dilakukan perbandingan
     *
     * */
    private fun specifyResult(listData: List<DataModel?>?, k: Int, aksara: String?): String {
        var listAksara = ListHanacaraka().getListAksaraCount()

        for (i in 0 until k) {
            when(listData!![i]?.classification?.aksara){
                "HA"  -> listAksara!![0].total = listAksara[0].total!!+1
                "NA"  -> listAksara!![1].total = listAksara[1].total!!+1
                "CA"  -> listAksara!![2].total = listAksara[2].total!!+1
                "RA"  -> listAksara!![3].total = listAksara[3].total!!+1
                "KA"  -> listAksara!![4].total = listAksara[4].total!!+1
                "DA"  -> listAksara!![5].total = listAksara[5].total!!+1
                "TA"  -> listAksara!![6].total = listAksara[6].total!!+1
                "SA"  -> listAksara!![7].total = listAksara[7].total!!+1
                "WA"  -> listAksara!![8].total = listAksara[8].total!!+1
                "LA"  -> listAksara!![9].total = listAksara[9].total!!+1
                "PA"  -> listAksara!![10].total = listAksara[10].total!!+1
                "DHA" -> listAksara!![11].total = listAksara[11].total!!+1
                "JA"  -> listAksara!![12].total = listAksara[12].total!!+1
                "YA"  -> listAksara!![13].total = listAksara[13].total!!+1
                "NYA" -> listAksara!![14].total = listAksara[14].total!!+1
                "MA"  -> listAksara!![15].total = listAksara[15].total!!+1
                "GA"  -> listAksara!![16].total = listAksara[16].total!!+1
                "BA"  -> listAksara!![17].total = listAksara[17].total!!+1
                "THA" -> listAksara!![18].total = listAksara[18].total!!+1
                "NGA" -> listAksara!![19].total = listAksara[19].total!!+1
            }
        }

        var jumlahAksara = listAksara?.filter { it.aksara==aksara }!![0].total
        listAksara?.sortByDescending { it.total }

        return if (listAksara[0].total==jumlahAksara) aksara.toString()else listAksara[0].aksara.toString()
    }
}