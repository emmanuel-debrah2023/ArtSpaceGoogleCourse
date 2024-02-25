package com.example.artspacegooglecourse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.artspacegooglecourse.data.ImageApiModel
import com.example.artspacegooglecourse.data.ImageData
import com.example.artspacegooglecourse.ui.theme.ArtSpaceGoogleCourseTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artspacegooglecourse.data.ArtworkRepository
import com.example.artspacegooglecourse.data.art
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import com.example.artspacegooglecourse.utils.linkBuilder

@Composable
fun ArtworkScreen(
    artworkUiState: ArtworkUiState,
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp)
){
    println("Loading")
    when(artworkUiState) {
        is ArtworkUiState.Loading -> PhotosLoadingScreen(modifier = modifier.fillMaxSize())
        is ArtworkUiState.Success -> PhotosGridScreen(
            artworkUiState.photos,
            contentPadding = contentPadding,
            modifier = modifier
            )
        is ArtworkUiState.Error -> PhotosErrorScreen(retryAction)
    }
}

@Composable
fun PhotosGridScreen(
    photos: List<ImageData>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
    ) {
    print("Success")
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = contentPadding,

    ){
        items(items = photos, key = {photo -> photo.id}) { photo ->
            ArtPhotoCard(
                photo,
                modifier = modifier
            )

        }
    }
}

@Composable
fun ArtPhotoCard(
    photo: ImageData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(244.dp)
            .padding(4.dp)
        ,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ){
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
fun PhotosErrorScreen(retryAction: () -> Unit,modifier: Modifier = Modifier) {
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
                apiModel = "google.com",
                apiLink = "Google.com/",
                isBoosted = false,
                title = "MonaLisa",
                altTitles = null,
                imageId = "id-here"
            )
        }
        ArtworkScreen(
            ArtworkUiState.Success(mockData),
            retryAction = {}
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
                apiModel = "google.com",
                apiLink = "Google.com/",
                isBoosted = false,
                title = "MonaLisa",
                altTitles = null,
                imageId = "image-id-2"
                )
        }
        PhotosGridScreen(mockData)
    }
}