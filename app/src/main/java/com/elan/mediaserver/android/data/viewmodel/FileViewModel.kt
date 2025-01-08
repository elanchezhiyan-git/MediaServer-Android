package com.elan.mediaserver.android.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elan.mediaserver.android.data.constants.Category
import com.elan.mediaserver.android.data.model.FileDto
import com.elan.mediaserver.android.data.repository.MediaServiceRepository
import kotlinx.coroutines.launch

class FileViewModel : ViewModel() {
    private val repository = MediaServiceRepository()

    private val _files = MutableLiveData<List<FileDto>>()
    val files: LiveData<List<FileDto>> = _files

    fun getRecentlyAdded() {
        viewModelScope.launch {
            try {
                val filesFromAPi = repository.getFiles(Category.RECENTLY_ADDED)
                _files.value = filesFromAPi
            } catch (e : Exception) {
                Log.e("Tag", "Error in retrieving recently added: ", e)
            }
        }
    }

    fun getFiles(category: Category) {
        viewModelScope.launch {
            try {
                val filesFromAPi = repository.getFiles(category)
                _files.value = filesFromAPi
            } catch (e : Exception) {
                Log.e("Tag", "fetchFiles: ", e)
            }
        }
    }
}