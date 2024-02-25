package com.example.artspacegooglecourse

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.artspacegooglecourse.components.GalleryGrid
import com.example.artspacegooglecourse.components.TopBar
import com.example.artspacegooglecourse.data.INDEX_DECREASE
import com.example.artspacegooglecourse.data.INDEX_INCREASE
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
                retryAction = artworkViewModel::getArtworkPhotosData
                )   }

        composable(
            route="${Screen.Art.name}/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.IntType
            })
            ){backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            ArtScreen(
                id = id,
                onNavigateToGallery = { navController.navigate(Screen.Gallery.name)},
                buttonClickLeft = { it:Int -> navController.navigate("${Screen.Art.name}/${it- INDEX_DECREASE}")},
                buttonClickRight = { it:Int -> navController.navigate("${Screen.Art.name}/${it+ INDEX_INCREASE}")}
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