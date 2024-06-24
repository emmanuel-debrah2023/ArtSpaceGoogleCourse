package com.example.artspacegooglecourse.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artspacegooglecourse.R
import com.example.artspacegooglecourse.components.GalleryButton
import com.example.artspacegooglecourse.components.NavTopBar
import com.example.artspacegooglecourse.ui.ArtScreenUiState
import com.example.artspacegooglecourse.ui.ArtScreenViewModel
import com.example.artspacegooglecourse.ui.model.ImageData
import com.example.artspacegooglecourse.utils.linkBuilder


@Composable
fun ArtScreen(
    onNavigateToGallery: () -> Unit,
    id: Int,
    imageId: String
) {
    val artScreenViewModel: ArtScreenViewModel = viewModel(factory = ArtScreenViewModel.Factory)
    val screenUiState = artScreenViewModel.artScreenUiState
    if (id != null) {
        Log.d("VM", "GalleryApp: Calling VM :$id")
        LaunchedEffect(Unit) {
            artScreenViewModel.getCurrentArtworkDetails(id)
        }

    }
    when (screenUiState) {
        is  ArtScreenUiState.Loading -> ArtworkApiLoadingScreen()
        is ArtScreenUiState.Error -> ArtworkApiErrorScreen()
        is ArtScreenUiState.Success -> ArtworkApiScreen(
            onNavigateToGallery = { onNavigateToGallery() },
            imageId = imageId,
            imageDetails = screenUiState.details
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworkApiScreen(
    onNavigateToGallery: () -> Unit,
    imageId: String,
    modifier: Modifier = Modifier,
    imageDetails: ImageData,
) {
    Scaffold(
        topBar = {
            NavTopBar(iconBtn = {
                GalleryButton(
                    onGalleryClick = { onNavigateToGallery() },
                    modifier = modifier.testTag("gallery_button")
                )
            })
        }
    )
    { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {

            Box(
                modifier = modifier
                    .height(dimensionResource(id = R.dimen.image_height))
                    .width(dimensionResource(id = R.dimen.image_width))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(
                            linkBuilder(id = imageId, "https://www.artic.edu/iiif/2")
                        ).crossfade(true).build(),
                    error = painterResource(R.drawable.ic_connection_error),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = "Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(
                modifier = modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.small_value),
                        vertical = dimensionResource(
                            R.dimen.x_small_value
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_vertical_space))

            ) {//Extract to its own composable
                println(imageDetails.imageId)
                Text(
                    text = imageDetails.title,
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = "${imageDetails.completionDate}",
                    style = MaterialTheme.typography.displayMedium
                )
                val description = imageDetails.shortDescription.ifEmpty { imageDetails.description }
                Text(
                    text = description.replace("<p>", ""),
                    style = MaterialTheme.typography.displayMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ArtworkApiLoadingScreen(
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ArtworkApiErrorScreen() {
    Image(
        painter = painterResource(id = R.drawable.ic_connection_error),
        contentDescription = stringResource(R.string.loading_failed)
    )
}