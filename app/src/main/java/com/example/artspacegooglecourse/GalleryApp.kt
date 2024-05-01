package com.example.artspacegooglecourse

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.artspacegooglecourse.ui.ArtScreenViewModel
import com.example.artspacegooglecourse.ui.ArtworkViewModel
import com.example.artspacegooglecourse.ui.screens.ArtScreen
import com.example.artspacegooglecourse.ui.screens.ArtworkScreen

@Composable
fun GalleryApp(
    navController: NavHostController = rememberNavController()
){
    NavHost(navController = navController,
        startDestination = Screen.Gallery.name
    ) {

        composable(Screen.Gallery.name){
            val artworkViewModel: ArtworkViewModel = viewModel(factory = ArtworkViewModel.Factory)
            ArtworkScreen(
                artworkUiState = artworkViewModel.artworkUiState,
                retryAction = artworkViewModel::getArtworkPhotosData,
                onArtSelect = {navController.navigate("${Screen.Art.name}/${it.imageId}")}
            )
        }

        composable(
            route="${Screen.Art.name}/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            })
            ){

            backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val artScreenViewModel: ArtScreenViewModel = viewModel(factory = ArtScreenViewModel.Factory)
            if (id != null) {
                artScreenViewModel.setSelectedArtId(id)
            }
            ArtScreen(
                id = id,
                onNavigateToGallery = { navController.navigate(Screen.Gallery.name)},
                artScreenUiState = artScreenViewModel.artScreenUiState
        )

        }
    }
}

