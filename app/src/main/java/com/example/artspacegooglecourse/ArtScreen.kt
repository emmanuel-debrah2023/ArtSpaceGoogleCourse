package com.example.artspacegooglecourse

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.artspacegooglecourse.components.GalleryButton
import com.example.artspacegooglecourse.components.NavTopBar
import com.example.artspacegooglecourse.components.NextButton
import com.example.artspacegooglecourse.components.PreviousButton
import com.example.artspacegooglecourse.data.art
import com.example.artspacegooglecourse.ui.theme.ArtSpaceGoogleCourseTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtScreen(
    onNavigateToGallery: ()-> Unit,
    buttonClickLeft: (Int) -> Unit,
    buttonClickRight: (Int) -> Unit,
    id: Int,
    modifier: Modifier = Modifier
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
                modifier = modifier.padding(paddingValues)
            ) {

                Box(
                    modifier = modifier
                        .height(dimensionResource(id = R.dimen.image_height))
                        .width(dimensionResource(id = R.dimen.image_width))
                ) {
                    ArtImage((art[id]).imageResourceID)
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.button_space_between))
                ) {
                    PreviousButton(
                        id,
                        decrementState = { buttonClickLeft(id) }
                    )
                    NextButton(
                        id,
                        incrementState = { buttonClickRight(id) }
                    )
                }
                ArtworkDetails(
                    stringResource((art[id]).name),
                    stringResource((art[id]).artist),
                    stringResource((art[id]).year),
                    stringResource((art[id]).description),
                )
            }
        }
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
        ArtScreen(onNavigateToGallery = {}, buttonClickLeft = {}, buttonClickRight ={} , id = 0)
    }
}

