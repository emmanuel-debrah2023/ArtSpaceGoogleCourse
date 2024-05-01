package com.example.artspacegooglecourse.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artspacegooglecourse.R
import com.example.artspacegooglecourse.components.GalleryButton
import com.example.artspacegooglecourse.components.NavTopBar
import com.example.artspacegooglecourse.network.NetworkImageData
import com.example.artspacegooglecourse.ui.ArtScreenUiState
import com.example.artspacegooglecourse.ui.model.ImageData
import com.example.artspacegooglecourse.ui.theme.ArtSpaceGoogleCourseTheme
import com.example.artspacegooglecourse.utils.linkBuilder


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtScreen(
    artScreenUiState: ArtScreenUiState,
    onNavigateToGallery: () -> Unit,
    id: String?
) {
    when(artScreenUiState){
        is ArtScreenUiState.Loading -> ArtworkApiLoadingScreen()
        is ArtScreenUiState.Error -> ArtworkApiErrorScreen()
        is ArtScreenUiState.Success -> ArtworkApiScreen(
            onNavigateToGallery = { onNavigateToGallery()},
            id = id,
            imageDetails = artScreenUiState.details
        )
    }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtworkApiScreen(
    onNavigateToGallery: () -> Unit,
    id: String?,
    modifier: Modifier = Modifier,
    imageDetails: ImageData,
) {
    Scaffold(
        topBar = {
            NavTopBar(iconBtn = { GalleryButton(
                onGalleryClick = {onNavigateToGallery()}
            )})
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
                            linkBuilder(id = "$id" , "https://www.artic.edu/iiif/2")
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
                    .padding(horizontal = dimensionResource(R.dimen.small_value), vertical = dimensionResource(
                        R.dimen.x_small_value
                    )),
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
                val description = if(imageDetails.shortDescription.isNullOrEmpty()) imageDetails.description else imageDetails.shortDescription
                Text(text = description.replace("<p>",""),
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
){
    Image(modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}
@Composable
fun ArtworkApiErrorScreen(
    modifier: Modifier = Modifier
){
    Image(painter = painterResource(id = R.drawable.ic_connection_error),
        contentDescription = stringResource(R.string.loading_failed)
    )
}


@Composable
fun ArtImage(
    @DrawableRes artImage: Int
) {
    Image(
        painter = painterResource(artImage),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}


@Composable
fun ArtworkDetails (
    artTitle: String,
    artistName: String,
    artistYear: String,
    artDescription: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.small_value)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_vertical_space))

    )
    {
        Text(
            artTitle,
            style = MaterialTheme.typography.displayLarge
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.x_small_value))
        ) {
            Text(
                artistName,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = artistYear,
                style = MaterialTheme.typography.displayMedium
            )
        }
        Text(
            artDescription,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_3A)
@Composable
fun ArtScreenPreview(){
    ArtSpaceGoogleCourseTheme{
        ArtScreen(
            onNavigateToGallery = {},
            id="1",
            artScreenUiState = ArtScreenUiState.Success(
            details = ImageData(
                id = 1,
                title = "Mona Lisa",
                imageId = "1234",
                shortDescription = "Lady",
                description = "Lady on canvas",
                completionDate = 1999,
                placeOfOrigin = "France"
            )
        ))
    }
}

