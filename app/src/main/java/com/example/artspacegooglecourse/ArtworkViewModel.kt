package com.example.artspacegooglecourse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.artspacegooglecourse.data.ArtworkRepository
import com.example.artspacegooglecourse.data.ImageData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ArtworkUiState {
    data class Success(val photos: List<ImageData>) : ArtworkUiState
    object Error : ArtworkUiState
    object Loading : ArtworkUiState
}


class ArtworkViewModel(private val artworkRepository: ArtworkRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var artworkUiState: ArtworkUiState by mutableStateOf(ArtworkUiState.Loading)
        private set


    /**
     * Call getArtworkPhotos() on init so we can display status immediately.
     */
    init {
        getArtworkPhotosData()
    }

    fun getArtworkPhotosData() {
        viewModelScope.launch {
            artworkUiState = ArtworkUiState.Loading
            artworkUiState = try {
                ArtworkUiState.Success(artworkRepository.getArtworkPhotosData())
            } catch (e: IOException) {
                ArtworkUiState.Error
            } catch (e: HttpException) {
                ArtworkUiState.Error
            }
        }
    }


    /**
     * Factory for [ArtworkViewModel] that takes [artworkRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ArtworkApplication)
                val artworkRepository = application.container.artworkRepository
                ArtworkViewModel(artworkRepository = artworkRepository)
            }
        }
    }
}