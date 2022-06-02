package ru.chistov.materialdesign.repositiry

interface MyCallback {
    fun onFailure(message:String)
    fun onResponse(picture: PictureOfTheDayResponseData)
}