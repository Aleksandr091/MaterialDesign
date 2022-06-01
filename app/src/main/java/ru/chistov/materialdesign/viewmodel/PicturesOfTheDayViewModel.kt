package ru.chistov.materialdesign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chistov.materialdesign.BuildConfig
import ru.chistov.materialdesign.repositiry.*

import ru.chistov.materialdesign.repositiry.PictureOfTheDayRetrofit2Impl.Companion.getRetrofit

class PicturesOfTheDayViewModel(
    private val liveData: MutableLiveData<PicturesOfTheDayAppState> = MutableLiveData(),
    private val repository: Repository = PictureOfTheDayRetrofit2Impl()
) : ViewModel() {

    fun getLiveData(): LiveData<PicturesOfTheDayAppState> {
        return liveData
    }

    fun sendRequest(){
        liveData.postValue(PicturesOfTheDayAppState.Loading(null))
        repository.getPicture(object : MyCallback{
            override fun onFailure(error: String) {
                liveData.postValue(PicturesOfTheDayAppState.Error(error))
            }

            override fun onResponse(picture: PictureOfTheDayResponseData) {
                liveData.postValue(PicturesOfTheDayAppState.Success(picture))
            }

        })
    }

    /*private val callback = object : Callback<PictureOfTheDayResponseData>{
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

    }*/
}