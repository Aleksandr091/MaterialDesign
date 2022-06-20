package ru.chistov.materialdesign.viewmodel.PicturesOfTheDay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.chistov.materialdesign.repositiry.PodCallback
import ru.chistov.materialdesign.repositiry.Repository
import ru.chistov.materialdesign.repositiry.Retrofit2Impl
import ru.chistov.materialdesign.repositiry.dto.PictureOfTheDayResponseData


class PicturesOfTheDayViewModel(
    private val liveData: MutableLiveData<PicturesOfTheDayAppState> = MutableLiveData(),
    private val repository: Repository = Retrofit2Impl()
) : ViewModel() {

    fun getLiveData(): LiveData<PicturesOfTheDayAppState> {
        return liveData
    }

    fun sendRequest(date: String){
        liveData.postValue(PicturesOfTheDayAppState.Loading(null))
        repository.getPicture(date,object : PodCallback{
            override fun onFailure(message:String) {
                liveData.postValue(PicturesOfTheDayAppState.Error(message))
            }

            override fun onResponse(picture: PictureOfTheDayResponseData) {
                liveData.postValue(PicturesOfTheDayAppState.Success(picture))
            }

        })
    }

}