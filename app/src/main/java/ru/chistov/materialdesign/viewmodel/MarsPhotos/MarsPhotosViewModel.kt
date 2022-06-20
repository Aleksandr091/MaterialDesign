package ru.chistov.materialdesign.viewmodel.MarsPhotos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.chistov.materialdesign.repositiry.Repository
import ru.chistov.materialdesign.repositiry.Retrofit2Impl
import ru.chistov.materialdesign.repositiry.dto.MarsPhotosServerResponseData
import ru.chistov.materialdesign.utils.UNKNOWN_ERROR
import java.text.SimpleDateFormat
import java.util.*


class MarsPhotosViewModel(
    private val liveData: MutableLiveData<MarsPhotosAppState> = MutableLiveData(),
    private val repository: Repository = Retrofit2Impl()
) : ViewModel() {

    fun getLiveData(): LiveData<MarsPhotosAppState> {
        return liveData
    }

    fun sendRequest() {
        val earthDate = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
        liveData.postValue(MarsPhotosAppState.Loading(null))
        repository.getMarsPictureByDate(earthDate, marsCallback)

    }

    val marsCallback = object : Callback<MarsPhotosServerResponseData> {

        override fun onResponse(
            call: Call<MarsPhotosServerResponseData>,
            response: Response<MarsPhotosServerResponseData>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveData.postValue(MarsPhotosAppState.Success(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveData.postValue(MarsPhotosAppState.Error(Throwable(UNKNOWN_ERROR)))
                } else {
                    liveData.postValue(MarsPhotosAppState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
            liveData.postValue(MarsPhotosAppState.Error(t))
        }
    }


}



