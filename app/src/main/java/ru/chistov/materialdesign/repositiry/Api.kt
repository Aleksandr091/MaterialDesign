package ru.chistov.materialdesign.repositiry

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.chistov.materialdesign.repositiry.dto.EarthEpicServerResponseData
import ru.chistov.materialdesign.repositiry.dto.MarsPhotosServerResponseData
import ru.chistov.materialdesign.repositiry.dto.PictureOfTheDayResponseData
import ru.chistov.materialdesign.utils.ENDPOINT_EARTH
import ru.chistov.materialdesign.utils.ENDPOINT_MARS
import ru.chistov.materialdesign.utils.ENDPOINT_POD

interface Api {
    @GET(ENDPOINT_POD)
    fun getPicturesOfTheDay(
        @Query("date") date: String,
        @Query("api_key") apiKey: String
    ): Call<PictureOfTheDayResponseData>


    @GET(ENDPOINT_EARTH)
    fun getEPIC(
        @Query("api_key") apiKey: String,
    ): Call<List<EarthEpicServerResponseData>>

    @GET(ENDPOINT_MARS)
    fun getMarsImageByDate(
        @Query("earth_date") earth_date: String,
        @Query("api_key") apiKey: String,
    ): Call<MarsPhotosServerResponseData>
}
