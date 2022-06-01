package ru.chistov.materialdesign.viewmodel

import ru.chistov.materialdesign.repositiry.PictureOfTheDayResponseData

sealed class PicturesOfTheDayAppState{
    data class Success(val pictureOfTheDayResponseData: PictureOfTheDayResponseData):PicturesOfTheDayAppState()
    data class Error(val error: String):PicturesOfTheDayAppState()
    data class Loading(val progress: Int?):PicturesOfTheDayAppState()
}
