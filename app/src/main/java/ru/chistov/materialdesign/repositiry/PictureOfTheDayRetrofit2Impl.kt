package ru.chistov.materialdesign.repositiry


import android.app.Application
import com.google.gson.GsonBuilder
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import ru.chistov.materialdesign.BuildConfig
import ru.chistov.materialdesign.utils.DOMAIN

class PictureOfTheDayRetrofit2Impl : Application(), Repository {


    private var pictureOfTheDayRetrofit2: Retrofit? = null

    private fun getRetrofit(): PicturesOfTheDayApi {
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
        return pictureOfTheDayRetrofit2!!.create(PicturesOfTheDayApi::class.java)
    }


    override fun getPicture(date: String, myCallback: MyCallback) {

       getRetrofit().getPicturesOfTheDay(date, BuildConfig.NASA_API_KEY)
            .enqueue(object : Callback<PictureOfTheDayResponseData> {
                override fun onResponse(
                    call: Call<PictureOfTheDayResponseData>,
                    response: Response<PictureOfTheDayResponseData>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { myCallback.onResponse(it) }

                    } else {
                        myCallback.onFailure("${response.message()}${response.code()}")
                    }
                }

                override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                    myCallback.onFailure(t.message.toString())
                }

            })


    }


}
