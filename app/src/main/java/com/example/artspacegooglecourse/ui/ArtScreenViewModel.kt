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
import com.example.artspacegooglecourse.data.ArtworkRepository
import com.example.artspacegooglecourse.network.NetworkImageData
import com.example.artspacegooglecourse.network.mapper
import com.example.artspacegooglecourse.ui.model.ImageData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed interface ArtScreenUiState {

    data class Success(val details: ImageData) : ArtScreenUiState
    object Error : ArtScreenUiState
    object Loading : ArtScreenUiState
}

class ArtScreenViewModel(private val artworkRepository: ArtworkRepository) : ViewModel(){

    //private val _uiState = MutableStateFlow(ScreenUiState())
    //val uiState : StateFlow<ScreenUiState> = _uiState.asStateFlow()

    var artScreenUiState: ArtScreenUiState by mutableStateOf(ArtScreenUiState.Loading)//MaybeChange to request state ?
        private set

    private val _selectedArt = mutableStateOf("")
    val selectedArt : String
        get() = _selectedArt.value

    fun setSelectedArtId(newId: String){

        _selectedArt.value = newId
        println("new id selected is:$newId")
        println("state value is:$selectedArt")
    }
    fun getSelectedArtId() = selectedArt


    fun getCurrentArtworkDetails(){
        viewModelScope.launch {
            artScreenUiState = ArtScreenUiState.Loading
            artScreenUiState = try {
                ArtScreenUiState.Success(artworkRepository.getSpecificArtworkData(selectedArt).mapper())
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
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AppContainerInstance)
                val artworkRepository = application.container.artworkRepository
                ArtScreenViewModel(artworkRepository = artworkRepository)
            }
        }
    }
}

