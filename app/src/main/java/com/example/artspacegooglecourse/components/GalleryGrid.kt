package com.example.artspacegooglecourse.components

import android.content.Intent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import com.example.artspacegooglecourse.MainActivity
import com.example.artspacegooglecourse.R
import data.Art

@Composable
fun GalleryGrid(
    artList: List<Art>
) {
    val locContext = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(dimensionResource(R.dimen.small_value))
    ){
        items(artList){ art ->
            GalleryItem(
                artwork = art.imageResourceID ,
                artTitle = art.name,
                clickEvent = {
                    val intent = Intent(locContext, MainActivity::class.java)
                    intent.putExtra("indexOfArt", artList.indexOf(art))
                    locContext.startActivity(intent)
                }
            )
        }
    }
}
