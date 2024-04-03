package com.example.artspacegooglecourse

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.artspacegooglecourse.components.GalleryGrid
import com.example.artspacegooglecourse.components.TopBar
import com.example.artspacegooglecourse.data.art
import com.example.artspacegooglecourse.ui.theme.ArtSpaceGoogleCourseTheme

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
        navController: NavHostController,
        modifier: Modifier = Modifier

) {

    Scaffold (
        topBar = { TopBar() }
    ){ innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
        ) {
            GalleryGrid(
                art,
                navController = navController
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryScreenPreview() {
    ArtSpaceGoogleCourseTheme {
        GalleryScreen(navController = rememberNavController())
    }
}