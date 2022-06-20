package ru.chistov.materialdesign.viewmodel.PicturesOfTheDay

import ru.chistov.materialdesign.repositiry.dto.PictureOfTheDayResponseData

sealed class PicturesOfTheDayAppState{
    data class Success(val pictureOfTheDayResponseData: PictureOfTheDayResponseData): PicturesOfTheDayAppState()
    data class Error(val message: String): PicturesOfTheDayAppState()
    data class Loading(val progress: Int?): PicturesOfTheDayAppState()
}
