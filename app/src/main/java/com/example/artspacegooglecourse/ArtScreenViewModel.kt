package com.example.artspacegooglecourse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.artspacegooglecourse.data.ArtworkRepository
import com.example.artspacegooglecourse.data.ImageData
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface ArtScreenUiState {

    data class Success(val details: ImageData) : ArtScreenUiState
    object Error : ArtScreenUiState
    object Loading : ArtScreenUiState
}

class ArtScreenViewModel(private val artworkRepository: ArtworkRepository) : ViewModel(){

    var artScreenUiState: ArtScreenUiState by mutableStateOf(ArtScreenUiState.Loading)
        private set

    private val _selectedArt = mutableStateOf("")
    val selectedArt : String
        get() = _selectedArt.value

    fun setSelectedArtId(newId: String){
        _selectedArt.value = newId
    }

    fun getCurrentArtworkDetails(){
        viewModelScope.launch {
            artScreenUiState = ArtScreenUiState.Loading
            artScreenUiState = try {
               ArtScreenUiState.Success(artworkRepository.getSpecificArtworkData(selectedArt))
            } catch (e:HttpException){
                ArtScreenUiState.Error
            }

        }
    }

    init{
        getCurrentArtworkDetails()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ArtworkApplication)
                val artworkRepository = application.container.artworkRepository
                ArtScreenViewModel(artworkRepository = artworkRepository)
            }
        }
    }
}

