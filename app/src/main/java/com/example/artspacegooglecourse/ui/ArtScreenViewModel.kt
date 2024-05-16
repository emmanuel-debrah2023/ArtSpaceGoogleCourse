package com.example.artspacegooglecourse.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.artspacegooglecourse.AppContainerInstance
import com.example.artspacegooglecourse.data.Repository
import com.example.artspacegooglecourse.network.mapper
import com.example.artspacegooglecourse.ui.model.ImageData
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface ArtScreenUiState {

    data class Success(val details: ImageData) : ArtScreenUiState
    object Error : ArtScreenUiState
    object Loading : ArtScreenUiState
}

class ArtScreenViewModel(private val repository: Repository) : ViewModel() {
    var artScreenUiState: ArtScreenUiState by mutableStateOf(ArtScreenUiState.Loading)
        //MaybeChange to request state ?
        private set

    private val _selectedArt = mutableStateOf("")
    private val selectedArt: String
        get() = _selectedArt.value

    fun setSelectedArtId(newId: String) {

        _selectedArt.value = newId
        println("new id selected is:$newId")
        println("state value is:$selectedArt")
    }

    fun getSelectedArtId() = selectedArt


    fun getCurrentArtworkDetails(id: Int?) {
        viewModelScope.launch {
            artScreenUiState = ArtScreenUiState.Loading
            artScreenUiState = try {
                ArtScreenUiState.Success(repository.getImageData(id).mapper())
            } catch (e: HttpException) {
                ArtScreenUiState.Error
            }

        }
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

