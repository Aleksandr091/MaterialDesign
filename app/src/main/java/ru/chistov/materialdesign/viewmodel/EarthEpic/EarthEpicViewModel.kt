package ru.chistov.materialdesign.viewmodel.EarthEpic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chistov.materialdesign.repositiry.Repository
import ru.chistov.materialdesign.repositiry.Retrofit2Impl
import ru.chistov.materialdesign.repositiry.dto.EarthEpicServerResponseData
import ru.chistov.materialdesign.utils.UNKNOWN_ERROR


class EarthEpicViewModel(
    private val liveData: MutableLiveData<EarthEpicAppState> = MutableLiveData(),
    private val repository: Repository = Retrofit2Impl()
) : ViewModel() {

    fun getLiveData(): LiveData<EarthEpicAppState> {
        return liveData
    }

    fun sendRequest() {
        liveData.postValue(EarthEpicAppState.Loading(null))
        repository.getEPIC(epicCallback)

    }
    private val epicCallback = object : Callback<List<EarthEpicServerResponseData>> {

        override fun onResponse(
            call: Call<List<EarthEpicServerResponseData>>,
            response: Response<List<EarthEpicServerResponseData>>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveData.postValue(EarthEpicAppState.Success(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveData.postValue(EarthEpicAppState.Error(Throwable(UNKNOWN_ERROR)))
                } else {
                    liveData.postValue(EarthEpicAppState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<List<EarthEpicServerResponseData>>, t: Throwable) {
            liveData.postValue(EarthEpicAppState.Error(t))
        }


    }
}



