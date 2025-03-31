package com.elan.media.server.android.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elan.media.server.android.data.model.ThumbnailDTO
import com.elan.media.server.android.data.repository.MediaServiceRepository
import kotlinx.coroutines.launch

class ThumbnailViewModel : ViewModel() {
    private val repository = MediaServiceRepository()

    // Use MutableStateMap to ensure recompositions
    private val _thumbnails = mutableStateMapOf<String, ThumbnailDTO>()
    val thumbnails: Map<String, ThumbnailDTO> get() = _thumbnails

    fun getThumbnail(id: String) {
        viewModelScope.launch {
            try {
                Log.d("ThumbnailViewModel", "Fetching thumbnail for ID: $id")
                val thumbnail = repository.getThumbnail(id)
                Log.d("ThumbnailViewModel", "Fetched thumbnail for ID: $id")

                _thumbnails[id] = thumbnail // Update map (triggers recomposition)
            } catch (e: Exception) {
                Log.e("ThumbnailViewModel", "fetchFiles: ", e)
            }
        }
    }
}
