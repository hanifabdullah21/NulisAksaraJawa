package com.singpaulee.sinauaksarajawawithknn.connection

import android.content.Context
import com.google.gson.GsonBuilder
import com.singpaulee.sinauaksarajawawithknn.HelperClass
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AppConfig {

    fun retrofitConfig(context: Context): Retrofit {
        val okHttpClient = setupOkHttpClient(context)

        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl("https://script.google.com/macros/s/AKfycbwrwqS5wjvBf4PfrgvO19YhuaRGk08OjkKCNUXlkvw8euCmDdNv/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    /** Fungsi ini digunakan untuk mengatur/memodifikasi variabel okHttpClient
     *  yang akan digunakan atau difungsikan untuk membuat atau mengambil cache dari sebuah response
     *  dalam kondisi ada atau tidak ada internet
     *
     * */
    private fun setupOkHttpClient(context: Context): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        return OkHttpClient.Builder()
            //Mengatur ukuran dari cache
            .cache(myCache)

            //Tambahkan interceptor pada okHttpClient
            .addInterceptor { chain ->

                //Mendapatkan request
                var request = chain.request()

                //Mengecek apakah ada internet atau tidak
                request = if (HelperClass().hasNetwork(context)!!)

                /* Jika ada internet, mengambil cache dari 5 detik yang lalu.
                *  Jika cache lebih dari 5 detik yang lalu, buang.
                *  Dan menampilkan kesalahan dalam mengambil response
                *  Attribut 'max-age' bertanggung jawab atas fungsi ini.
                * */
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()

                else

                /* Jika tidak ada internet, mengambil cache dari 7 hari yang lalu.
                *  Jika cache lebih dari 7 hari maka buang.
                *  Dan menampilkan kesalahan dalam mengambil respon
                *  Attribut 'max-stale' bertanggung jawab atas fungsi ini
                *  Attribut 'only-if-cached' dimaksudkan untuk tidak mengambil data baru, ambil data dari cache saja
                * */
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()

                //Tambahkan request yang sudah dimodifikasi pada 'chain'
                chain.proceed(request)
            }
            .build()
    }
}