package com.singpaulee.sinauaksarajawawithknn.connection

import com.singpaulee.sinauaksarajawawithknn.model.ResponseModel
import io.reactivex.Observable
import retrofit2.http.*

interface DataInterface {

    @FormUrlEncoded
    @POST("exec")
    fun postData(
        @Field("action") action: String?,
        @Field("aksara") aksara: String,
        @Field("redPixel") redPixel: Double?,
        @Field("greenPixel") greenPixel: Double?,
        @Field("bluePixel") bluePixel: Double?,
        @Field("result") result: String?
    ): Observable<ResponseModel>

    @FormUrlEncoded
    @POST("exec")
    fun postNewScore(
        @Field("action") action: String?,
        @Field("username") username: String?,
        @Field("score") score:Int
    ): Observable<ResponseModel>

    @GET("exec")
    fun getData(
        @Query("aksaraName") aksaraName: String,
        @Query("action") action: String
    ): Observable<ResponseModel>

    @GET("exec")
    fun getListHighscore(
        @Query("action") action: String
    ): Observable<ResponseModel>
}