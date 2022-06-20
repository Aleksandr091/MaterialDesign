package ru.chistov.materialdesign.viewmodel.MarsPhotos

import ru.chistov.materialdesign.repositiry.dto.MarsPhotosServerResponseData

sealed class MarsPhotosAppState {
    data class Success(val serverResponseData: MarsPhotosServerResponseData) : MarsPhotosAppState()
    data class Error(val message: Throwable) : MarsPhotosAppState()
    data class Loading(val progress: Int?) : MarsPhotosAppState()
}
