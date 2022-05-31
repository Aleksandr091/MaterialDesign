package ru.chistov.materialdesign.repositiry

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.chistov.materialdesign.utils.ENDPOINT

interface PicturesOfTheDayApi {
    @GET(ENDPOINT)
    fun  getPicturesOfTheDay(@Query("api_key") apiKey:String): Call<PictureOfTheDayResponseData>
}