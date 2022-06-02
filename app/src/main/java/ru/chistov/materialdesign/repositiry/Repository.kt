package ru.chistov.materialdesign.repositiry

interface Repository {
    fun getPicture(date:String,myCallback: MyCallback)
}