package ru.chistov.materialdesign.repositiry


import android.app.Application
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.chistov.materialdesign.BuildConfig
import ru.chistov.materialdesign.repositiry.dto.EarthEpicServerResponseData
import ru.chistov.materialdesign.repositiry.dto.MarsPhotosServerResponseData
import ru.chistov.materialdesign.repositiry.dto.PictureOfTheDayResponseData
import ru.chistov.materialdesign.utils.DOMAIN

class Retrofit2Impl : Application(), Repository {


    private var pictureOfTheDayRetrofit2: Retrofit? = null

    private fun getRetrofit(): Api {
        if (pictureOfTheDayRetrofit2 == null) {
            pictureOfTheDayRetrofit2 = Retrofit.Builder().apply {
                baseUrl(DOMAIN)
                addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
            }.build()
        }
        return pictureOfTheDayRetrofit2!!.create(Api::class.java)
    }


    override fun getPicture(date: String, podCallback: PodCallback) {
       getRetrofit().getPicturesOfTheDay(date, BuildConfig.NASA_API_KEY)
            .enqueue(object : Callback<PictureOfTheDayResponseData> {
                override fun onResponse(
                    call: Call<PictureOfTheDayResponseData>,
                    response: Response<PictureOfTheDayResponseData>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { podCallback.onResponse(it) }

                    } else {
                        podCallback.onFailure("${response.message()}${response.code()}")
                    }
                }

                override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                    podCallback.onFailure(t.message.toString())
                }

            })
    }
    override fun getEPIC(epicCallback: Callback<List<EarthEpicServerResponseData>>) {
        getRetrofit().getEPIC(BuildConfig.NASA_API_KEY).enqueue(epicCallback)
    }

    override fun getMarsPictureByDate(earthDate: String, marsCallbackByDate: Callback<MarsPhotosServerResponseData>) {
        getRetrofit().getMarsImageByDate(earthDate, BuildConfig.NASA_API_KEY).enqueue(marsCallbackByDate)
    }


}
