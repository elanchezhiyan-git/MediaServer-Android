package com.elan.mediaserver.android.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elan.mediaserver.android.data.model.FileDto
import com.elan.mediaserver.android.data.repository.MediaServiceRepository
import kotlinx.coroutines.launch

class FileViewModel : ViewModel() {
    private val repository = MediaServiceRepository()

    private val _files = MutableLiveData<List<FileDto>>()
    val files: LiveData<List<FileDto>> = _files

    fun fetchFiles() {
        viewModelScope.launch {
            try {
                val filesFromAPi = repository.getFiles()
                _files.value = filesFromAPi
            } catch (_: Exception) {

            }
        }
    }
}