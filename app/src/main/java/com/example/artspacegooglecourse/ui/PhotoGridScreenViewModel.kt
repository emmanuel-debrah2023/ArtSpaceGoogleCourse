package com.example.artspacegooglecourse.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch

sealed interface PhotoGridScreenUiState {
    data class Success(val photos: List<ImageData>) : PhotoGridScreenUiState
    data object Error : PhotoGridScreenUiState
    data object Loading : PhotoGridScreenUiState
}


class PhotoGridScreenViewModel(private val repository: Repository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var artworkUiState: PhotoGridScreenUiState by mutableStateOf(PhotoGridScreenUiState.Loading)
        private set


    /**
     * Call getPhotoGridData() on init so we can display the gallery immediately.
     */
    init {
        getPhotoGridData()
    }

    fun getPhotoGridData() {
        viewModelScope.launch {
            artworkUiState = PhotoGridScreenUiState.Loading
            artworkUiState = try {
                repository.fetchImageDataList()
                PhotoGridScreenUiState.Success(
                    repository.getSavedImagesDataList().map { it.uiModelMapper() })
            } catch (e: Exception) {
                PhotoGridScreenUiState.Error
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