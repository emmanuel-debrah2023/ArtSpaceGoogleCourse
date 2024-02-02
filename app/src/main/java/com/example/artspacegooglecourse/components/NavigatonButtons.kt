package com.example.artspacegooglecourse.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.artspacegooglecourse.R
import com.example.artspacegooglecourse.data.art

@Composable
fun NextButton(currentState: Int, incrementState: (Int) -> Unit) {
    IconButton(
        onClick = {
        incrementState(currentState)
    },
        enabled = (currentState != art.size-1)
    ) {
        Icon(Icons.Filled.ArrowForward, contentDescription = "" )
    }
}

@Composable
fun PreviousButton(currentState: Int, decrementState: (Int) -> Unit) {
    IconButton(onClick = {
        decrementState(currentState)
    },
        enabled = (currentState != 0)) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "" )
    }
}

@Composable
fun GalleryButton(onGalleryClick: ()->Unit) {

    ButtonWithIcon(
        icon = Icons.Filled.KeyboardArrowLeft,
        color = MaterialTheme.colorScheme.scrim,
        text = R.string.gallery_button_text,
        onClick = {onGalleryClick()}
    )
}