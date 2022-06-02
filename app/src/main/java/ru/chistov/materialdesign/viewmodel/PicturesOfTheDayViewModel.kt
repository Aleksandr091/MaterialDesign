package ru.chistov.materialdesign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    fun sendRequest(date: String){
        liveData.postValue(PicturesOfTheDayAppState.Loading(null))
        repository.getPicture(date,object : MyCallback{
            override fun onFailure(message:String) {
                liveData.postValue(PicturesOfTheDayAppState.Error(message))
            }

            override fun onResponse(response: PictureOfTheDayResponseData) {
                liveData.postValue(PicturesOfTheDayAppState.Success(response))
            }

        })
    }

}