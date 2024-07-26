package com.example.artspacegooglecourse

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.artspacegooglecourse.ui.PhotoGridScreenViewModel
import com.example.artspacegooglecourse.ui.screens.ArtScreen
import com.example.artspacegooglecourse.ui.screens.ArtworkScreen

@Composable
fun GalleryApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Gallery
    ) {

        composable<Gallery> {
            Log.d("VM", "GalleryApp: GalleryVM is called ")
            val photoGridScreenViewModel: PhotoGridScreenViewModel =
                viewModel(factory = PhotoGridScreenViewModel.Factory)
            val uiState by photoGridScreenViewModel.photoGridScreenUiState.collectAsState()
            ArtworkScreen(
                artworkUiState = uiState,
                retryAction = photoGridScreenViewModel::getPhotoGridData,
                onArtSelect = {navController.navigate(Art(it.id, it.imageId)) }
            )
        }

        composable<Art>
        {
            backStackEntry ->
            val art: Art = backStackEntry.toRoute()
                ArtScreen(
                    id = art.id,
                    imageId = art.imageId,
                    onNavigateToGallery = { navController.navigate(Gallery) },
                )
            }

        }
}

