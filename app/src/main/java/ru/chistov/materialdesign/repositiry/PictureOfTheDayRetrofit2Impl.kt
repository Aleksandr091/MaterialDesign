package ru.chistov.materialdesign.repositiry


import android.app.Application
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.chistov.materialdesign.utils.DOMAIN

class PictureOfTheDayRetrofit2Impl : Application() {

    companion object {

        private var pictureOfTheDayRetrofit2: Retrofit? = null

        fun getRetrofit(): PicturesOfTheDayApi {
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
    }
}