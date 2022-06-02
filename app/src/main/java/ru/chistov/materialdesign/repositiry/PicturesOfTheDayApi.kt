package ru.chistov.materialdesign.repositiry

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.chistov.materialdesign.utils.ENDPOINT
import java.util.*

interface PicturesOfTheDayApi {
    @GET(ENDPOINT)
    fun  getPicturesOfTheDay(@Query("date")date: String,@Query("api_key")apiKey:String): Call<PictureOfTheDayResponseData>
}