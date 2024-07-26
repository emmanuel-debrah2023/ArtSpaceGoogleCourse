package com.example.artspacegooglecourse.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.artspacegooglecourse.AppContainerInstance
import com.example.artspacegooglecourse.data.Repository
import com.example.artspacegooglecourse.data.db.uiModelMapper
import com.example.artspacegooglecourse.ui.model.ImageData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class PhotoGridScreenUiState<out T> {
    data class Success<out T>(val photos: List<ImageData>) : PhotoGridScreenUiState<T>()
    data class Error(val message: String) : PhotoGridScreenUiState<Nothing>()
    data object Loading : PhotoGridScreenUiState<Nothing>()
}

class PhotoGridScreenViewModel(private val repository: Repository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    val _photoGridUiState = MutableStateFlow<PhotoGridScreenUiState<List<ImageData>>>(PhotoGridScreenUiState.Loading)
    val photoGridScreenUiState = _photoGridUiState.asStateFlow()

    /**
     * Call getPhotoGridData() on init so we can display the gallery immediately.
     */
    init {
        getPhotoGridData()
    }

    fun getPhotoGridData() {
        viewModelScope.launch {
            try {
                repository.fetchImageDataList()
                val data = repository.getSavedImagesDataList().map { it.uiModelMapper() }
                _photoGridUiState.value = PhotoGridScreenUiState.Success(data)
            } catch (e: Exception) {
                _photoGridUiState.value = PhotoGridScreenUiState.Error(e.toString())
            }
        }
    }


    /**
     * Factory for [PhotoGridScreenViewModel] that takes [repository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AppContainerInstance)
                val repository = application.container.repository
                PhotoGridScreenViewModel(repository = repository)
            }
        }
    }
}