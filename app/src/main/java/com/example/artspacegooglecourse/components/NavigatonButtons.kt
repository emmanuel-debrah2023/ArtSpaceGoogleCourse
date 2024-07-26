package com.example.artspacegooglecourse.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.example.artspacegooglecourse.R



@Composable
fun GalleryButton(modifier: Modifier = Modifier,
                  onGalleryClick: ()->Unit
) {

    ButtonWithIcon(
        icon = Icons.Filled.KeyboardArrowLeft,
        color = MaterialTheme.colorScheme.scrim,
        text = R.string.gallery_button_text,
        onClick = {onGalleryClick()},
        modifier = modifier.semantics{ testTag = "GalleryButton"}
    )
}