package ru.chistov.materialdesign.repositiry

import retrofit2.Callback
import ru.chistov.materialdesign.repositiry.dto.EarthEpicServerResponseData
import ru.chistov.materialdesign.repositiry.dto.MarsPhotosServerResponseData

interface Repository {
    fun getPicture(date:String, podCallback: PodCallback)
    fun getEPIC( epicCallback: Callback<List<EarthEpicServerResponseData>>)
    fun getMarsPictureByDate(earthDate: String, marsCallbackByDate: Callback<MarsPhotosServerResponseData>)

}