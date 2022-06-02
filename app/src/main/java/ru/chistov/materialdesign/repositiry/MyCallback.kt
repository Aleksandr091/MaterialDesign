package ru.chistov.materialdesign.repositiry

interface MyCallback {
    fun onFailure(error: Throwable?,message:String?)

    fun onResponse(picture: PictureOfTheDayResponseData)
}