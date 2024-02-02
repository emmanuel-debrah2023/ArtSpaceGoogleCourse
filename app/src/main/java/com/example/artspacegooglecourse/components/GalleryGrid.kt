package com.example.artspacegooglecourse.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavController
import com.example.artspacegooglecourse.R
import com.example.artspacegooglecourse.data.ArtModel


@Composable
fun GalleryGrid(
    artList: List<ArtModel>,
    navController: NavController
) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(dimensionResource(R.dimen.small_value))
    ){
        items(artList){ art ->
            val artIndex = artList.indexOf(art)
            GalleryItem(
                artwork = art.imageResourceID ,
                artTitle = art.name,
                clickEvent = {
                    navController.navigate("Art/${artIndex}")
                }
            )
        }
    }
}
