package com.example.artspacegooglecourse.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artspacegooglecourse.R
import com.example.artspacegooglecourse.components.TopBar
import com.example.artspacegooglecourse.ui.PhotoGridScreenUiState
import com.example.artspacegooglecourse.ui.model.ImageData
import com.example.artspacegooglecourse.ui.theme.ArtSpaceGoogleCourseTheme
import com.example.artspacegooglecourse.utils.linkBuilder

@Composable
fun ArtworkScreen(
    artworkUiState: PhotoGridScreenUiState<List<ImageData>>,
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onArtSelect: (ImageData) -> Unit
) {
    when (artworkUiState) {
        is PhotoGridScreenUiState.Loading -> PhotosLoadingScreen(modifier = modifier.fillMaxSize())
        is PhotoGridScreenUiState.Success -> PhotosGridScreen(
            imageData = artworkUiState.photos,
            onArtSelect = { onArtSelect(it) },
            modifier = modifier
        )

        is PhotoGridScreenUiState.Error -> PhotosErrorScreen(retryAction)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosGridScreen(
    imageData: List<ImageData>,
    modifier: Modifier = Modifier,
    onArtSelect: (ImageData) -> Unit,
) {
    Scaffold(
        topBar = { TopBar() }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = modifier.padding(horizontal = 16.dp),
            contentPadding = it
        ) {
            itemsIndexed(
                items = imageData,
                key = { index, photo -> photo.id }
            ) { index, photo ->
                ArtPhotoCard(
                    photo = photo,
                    onClick = { image ->
                        onArtSelect(image)
                    },
                    modifier = Modifier.testTag("grid item $index")
                )
            }
        }
    }


}

@Composable
fun ArtPhotoCard(
    photo: ImageData,
    onClick: (ImageData) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable {
                onClick(photo)
            }
            .height(244.dp)
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(
                    linkBuilder(id = photo.imageId, "https://www.artic.edu/iiif/2")
                ).crossfade(true).build(),
            error = painterResource(R.drawable.ic_connection_error),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = "Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PhotosLoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun PhotosErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}


@Preview
@Composable
fun ArtworkScreenPreview() {
    ArtSpaceGoogleCourseTheme {
        val mockData = List(10) {
            ImageData(
                id = 1,
                title = "MonaLisa",
                imageId = "id-here",
                shortDescription = "Art",
                description = "Piece of art",
                completionDate = 1999,
                placeOfOrigin = "France"
            )
        }
        ArtworkScreen(
            PhotoGridScreenUiState.Success(mockData),
            retryAction = {},
            onArtSelect = {},
        )
    }
}

@Preview
@Composable
fun PhotoGridScreenPreview() {
    ArtSpaceGoogleCourseTheme {
        val mockData = List(10) {
            ImageData(
                id = 1,
                title = "MonaLisa",
                imageId = "id-here",
                shortDescription = "Art",
                description = "Piece of art",
                completionDate = 1999,
                placeOfOrigin = "France"
            )
        }
        PhotosGridScreen(imageData = mockData, onArtSelect = {})
    }
}