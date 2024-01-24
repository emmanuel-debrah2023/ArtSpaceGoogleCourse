package com.example.artspacegooglecourse

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import com.example.artspacegooglecourse.components.GalleryButton
import com.example.artspacegooglecourse.components.NavTopBar
import com.example.artspacegooglecourse.components.NextButton
import com.example.artspacegooglecourse.components.PreviousButton
import com.example.artspacegooglecourse.data.art


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtScreen(
    onNavigateToGallery: ()-> Unit,
    buttonClickLeft: (Int) -> Unit,
    buttonClickRight: (Int) -> Unit,
    id: Int
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
                modifier = Modifier.padding(paddingValues)
            ) {

                Box(
                    modifier = Modifier
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
                ArtworkDescriptor(
                    (art[id]).name,
                    (art[id]).artist,
                    (art[id]).year,
                    (art[id]).description,
                )
                println(id)
            }
        }
    }

@Composable
fun ArtImage(
    @DrawableRes artImage: Int,
) {
    //Add modifier styling with dimensions
    Image(
        painter = painterResource(artImage),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}


@Composable
fun ArtworkDescriptor (
    @StringRes artTitle: Int,
    @StringRes artistName: Int,
    @StringRes artistYear: Int,
    @StringRes artDescription: Int,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.small_value)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.text_vertical_space))

    )
    {
        Text(
            stringResource(artTitle),
            style = MaterialTheme.typography.displayLarge
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.x_small_value))
        ) {
            Text(
                stringResource(artistName),
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = "(${stringResource(artistYear)})",
                style = MaterialTheme.typography.displayMedium
            )
        }
        Text(
            stringResource(artDescription),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

