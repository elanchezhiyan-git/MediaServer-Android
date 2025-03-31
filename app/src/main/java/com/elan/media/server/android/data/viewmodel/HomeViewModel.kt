package com.elan.media.server.android.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elan.media.server.android.data.constants.Category
import com.elan.media.server.android.data.model.FileDto
import com.elan.media.server.android.data.model.ThumbnailDTO
import com.elan.media.server.android.data.repository.MediaServiceRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = MediaServiceRepository()

    private val _recentlyAdded = MutableLiveData<List<FileDto>>()
    val recentlyAdded: LiveData<List<FileDto>> = _recentlyAdded

    private val _movieFiles = MutableLiveData<List<FileDto>>()
    val movieFiles: LiveData<List<FileDto>> = _movieFiles

    private val _musicFiles = MutableLiveData<List<FileDto>>()
    val musicFiles: LiveData<List<FileDto>> = _musicFiles

    private val _photoFiles = MutableLiveData<List<FileDto>>()
    val photoFiles: LiveData<List<FileDto>> = _photoFiles

    private val _thumbnail = MutableLiveData<ThumbnailDTO>()
    val thumbnail: LiveData<ThumbnailDTO> = _thumbnail

    fun getFiles(category: Category) {
        viewModelScope.launch {
            try {
                val filesFromAPi = repository.getFiles(category)
                when(category) {
                    Category.RECENTLY_ADDED -> _recentlyAdded.value = filesFromAPi
                    Category.MOVIES -> _movieFiles.value = filesFromAPi
                    Category.MUSIC -> _musicFiles.value = filesFromAPi
                    Category.PHOTOS -> _photoFiles.value = filesFromAPi
                    else -> {}
                }
            } catch (e : Exception) {
                Log.e("Tag", "fetchFiles: ", e)
            }
        }
    }


    fun getThumbnail(id : String) {
        viewModelScope.launch {
            try {
                _thumbnail.value = repository.getThumbnail(id)
            } catch (e: Exception) {
                Log.e("Tag", "fetchFiles: ", e)
            }
        }
    }
}