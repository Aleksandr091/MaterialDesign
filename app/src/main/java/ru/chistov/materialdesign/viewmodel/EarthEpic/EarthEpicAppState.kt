package ru.chistov.materialdesign.viewmodel.EarthEpic

import ru.chistov.materialdesign.repositiry.dto.EarthEpicServerResponseData

sealed class EarthEpicAppState {
    data class Success(val serverResponseData: List<EarthEpicServerResponseData>) : EarthEpicAppState()
    data class Error(val message: Throwable) : EarthEpicAppState()
    data class Loading(val progress: Int?) : EarthEpicAppState()
}
