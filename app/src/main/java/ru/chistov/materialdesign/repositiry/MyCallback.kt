package ru.chistov.materialdesign.repositiry

interface MyCallback {
    fun onFailure(error: String)

    fun onResponse(picture: PictureOfTheDayResponseData)
}