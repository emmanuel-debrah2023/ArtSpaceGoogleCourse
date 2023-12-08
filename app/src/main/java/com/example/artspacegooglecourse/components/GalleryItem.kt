package com.example.artspacegooglecourse.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.artspacegooglecourse.R
import data.art

@Composable
fun GalleryItem(
    @DrawableRes artwork: Int,
    @StringRes artTitle: Int,
    clickEvent: () -> Unit,
){
        Box(modifier = Modifier
            .clickable{
                clickEvent()
            }
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(artwork),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.small_value))
                )
                Text(
                    text = stringResource(artTitle),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    GalleryItem(
        art[0].imageResourceID,
        art[0].name,
        {}
    )
}