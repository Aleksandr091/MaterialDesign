package ru.chistov.materialdesign.repositiry

import ru.chistov.materialdesign.repositiry.dto.PictureOfTheDayResponseData

interface PodCallback {
    fun onFailure(message:String)
    fun onResponse(picture: PictureOfTheDayResponseData)
}