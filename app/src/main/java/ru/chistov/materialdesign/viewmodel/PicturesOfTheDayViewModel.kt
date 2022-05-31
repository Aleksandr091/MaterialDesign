package ru.chistov.materialdesign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chistov.materialdesign.BuildConfig

import ru.chistov.materialdesign.repositiry.PictureOfTheDayResponseData
import ru.chistov.materialdesign.repositiry.PictureOfTheDayRetrofit2Impl
import ru.chistov.materialdesign.repositiry.PictureOfTheDayRetrofit2Impl.Companion.getRetrofit

class PicturesOfTheDayViewModel(
    private val liveData: MutableLiveData<PicturesOfTheDayAppState> = MutableLiveData(),
    private val pictureOfTheDayRetrofit2Impl: PictureOfTheDayRetrofit2Impl = PictureOfTheDayRetrofit2Impl()
) : ViewModel() {

    fun getLiveData(): LiveData<PicturesOfTheDayAppState> {
        return liveData
    }

    fun sendRequest(){
        liveData.postValue(PicturesOfTheDayAppState.Loading(null))
        getRetrofit().getPicturesOfTheDay(BuildConfig.NASA_API_KEY).enqueue(callback)
    }

    private val callback = object : Callback<PictureOfTheDayResponseData>{
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful){
                response.body()?.let { liveData.postValue(PicturesOfTheDayAppState.Success(it)) }

            }else{

            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            TODO("Not yet implemented")
        }

    }
}