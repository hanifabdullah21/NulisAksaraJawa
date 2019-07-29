package com.singpaulee.sinauaksarajawawithknn.helper

import com.singpaulee.sinauaksarajawawithknn.model.AksaraModel

class ListHanacaraka {

    private var ha = arrayOf("a", "HA")
    private var na = arrayOf("n", "NA")
    private var ca = arrayOf("c", "CA")
    private var ra = arrayOf("r", "RA")
    private var ka = arrayOf("k", "KA")
    private var da = arrayOf("f", "DA")
    private var ta = arrayOf("t", "TA")
    private var sa = arrayOf("s", "SA")
    private var wa = arrayOf("w", "WA")
    private var la = arrayOf("l", "LA")
    private var pa = arrayOf("p", "PA")
    private var dha = arrayOf("d", "DHA")
    private var ja = arrayOf("j", "JA")
    private var ya = arrayOf("y", "YA")
    private var nya = arrayOf("v", "NYA")
    private var ma = arrayOf("m", "MA")
    private var ga = arrayOf("g", "GA")
    private var ba = arrayOf("b", "BA")
    private var tha = arrayOf("q", "THA")
    private var nga = arrayOf("z", "NGA")

    fun listOfHanacaraka(): Array<Array<String>> {
        return arrayOf(
            ha, na, ca, ra, ka,
            da, ta, sa, wa, la,
            pa, dha, ja, ya, nya,
            ma, ga, ba, tha, nga
        )
    }

    fun getAksara(x: Int): String {
        return listOfHanacaraka()[x][0]
    }

    fun getListAksaraCount(): ArrayList<AksaraModel>? {
        val listAksara: ArrayList<AksaraModel>? = ArrayList()
        for (i in 0 until listOfHanacaraka().size) {
            val aksaraModel = AksaraModel(listOfHanacaraka()[i][1], 0)
            listAksara?.add(aksaraModel)

        }
        return listAksara
    }
}