package com.example.artspacegooglecourse.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

sealed class ArtScreenUiState<out T> {
    data class Success<out T>(val details: ImageData) : ArtScreenUiState<T>()
    data class Error(val message: String) : ArtScreenUiState<Nothing>()
    data object Loading : ArtScreenUiState<Nothing>()
}



class ArtScreenViewModel(private val repository: Repository) : ViewModel() {
    private val _artScreenUiState = MutableStateFlow<ArtScreenUiState<ImageData>>(ArtScreenUiState.Loading)
    val artScreenUiState = _artScreenUiState.asStateFlow()

//    var artScreenUiState: ArtScreenUiState by mutableStateOf(ArtScreenUiState.Loading)
//        private set

    private val _selectedArt = mutableStateOf("")
    private val selectedArt: String
        get() = _selectedArt.value

    fun setSelectedArtId(newId: String) {
        _selectedArt.value = newId
    }

    fun getSelectedArtId() = selectedArt

    fun getCurrentArtworkDetails(id: Int) {
        viewModelScope.launch {
           try {
               val data = repository.getSavedImageById(id).uiModelMapper()
                _artScreenUiState.value = ArtScreenUiState.Success(data)
            } catch (e: Exception) {
                _artScreenUiState.value = ArtScreenUiState.Error("There was an issue retrieving the artwork details")
            }
        }
    }

    fun getArtScreenUiState() {

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AppContainerInstance)
                val repository = application.container.repository
                ArtScreenViewModel(repository = repository)
            }
        }
    }
}

