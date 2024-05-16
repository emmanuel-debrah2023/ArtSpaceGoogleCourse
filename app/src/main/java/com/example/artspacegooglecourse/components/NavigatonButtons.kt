package com.example.artspacegooglecourse.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.artspacegooglecourse.R



@Composable
fun GalleryButton(onGalleryClick: ()->Unit) {

    ButtonWithIcon(
        icon = Icons.Filled.KeyboardArrowLeft,
        color = MaterialTheme.colorScheme.scrim,
        text = R.string.gallery_button_text,
        onClick = {onGalleryClick()}
    )
}