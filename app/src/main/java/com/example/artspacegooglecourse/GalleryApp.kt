package com.example.artspacegooglecourse

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.artspacegooglecourse.ui.ArtScreenViewModel
import com.example.artspacegooglecourse.ui.PhotoGridScreenViewModel
import com.example.artspacegooglecourse.ui.screens.ArtScreen
import com.example.artspacegooglecourse.ui.screens.ArtworkScreen

@Composable
fun GalleryApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Gallery.name
    ) {

        composable(Screen.Gallery.name) {
            Log.d("VM", "GalleryApp: GalleryVM is called ")
            val photoGridScreenViewModel: PhotoGridScreenViewModel =
                viewModel(factory = PhotoGridScreenViewModel.Factory)
            ArtworkScreen(
                artworkUiState = photoGridScreenViewModel.artworkUiState,
                retryAction = photoGridScreenViewModel::getArtworkPhotosData,
                onArtSelect = { navController.navigate("${Screen.Art.name}/${it.id}/${it.imageId}") }
            )
        }

        composable(
            route = "${Screen.Art.name}/{id}/{imageId}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            }, navArgument("imageId") {
                type = NavType.StringType
            })
        ) {

                backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            val imageId = backStackEntry.arguments?.getString("imageId")

            if (id != null && !imageId.isNullOrEmpty()) {
                ArtScreen(
                    id = id,
                    imageId = imageId,
                    onNavigateToGallery = { navController.navigate(Screen.Gallery.name) },
                    //artScreenUiState = artScreenViewModel.artScreenUiState
                )
            }
        }
    }
}

