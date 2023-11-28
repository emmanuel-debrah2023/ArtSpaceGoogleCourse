package com.example.artspacegooglecourse

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspacegooglecourse.ui.theme.ArtSpaceGoogleCourseTheme
import data.art

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceGoogleCourseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArtSpaceApp() {
    Scaffold (topBar = {
        ArtSpaceTopBar()
    }
    ){
        ArtworkScreen()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtSpaceTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row {
                Image(
                modifier = Modifier
                    .size(128.dp)
                    .padding(8.dp),
                painter = painterResource(R.drawable.artspace_logo),
                contentDescription = null
                )

            }
                },
        modifier = modifier,
        navigationIcon = {
            GalleryButton()
        }
    )
}

@Composable
fun GalleryButton() {
    val locContext = LocalContext.current
    TextButton(
        onClick = {
                    locContext.startActivity(Intent(locContext, GalleryActivity::class.java))
                  },
        colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
    ) {
        Icon(
            Icons.Filled.KeyboardArrowLeft,
            contentDescription = "Gallery"
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(stringResource(R.string.gallery_button_text))
    }
}

@Composable
fun ArtworkScreen(
    modifier: Modifier = Modifier,
) {
    var current by remember {
        mutableStateOf(1)
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ){


        Box(
            modifier = modifier
                .height(400.dp)
                .width(300.dp)
        ) {
            ArtImage((art[current]).imageResourceID)
        }

            Row(
                horizontalArrangement = Arrangement.spacedBy(100.dp)
            ){
                PreviousButton(
                    current,
                    decrementState = {decrementState -> current = decrementState}
                )
                NextButton(
                    current,
                    incrementState = {incrementState -> current = incrementState}
                )
            }
            ArtworkDescriptor(
                (art[current]).name,
                (art[current]).artist,
                (art[current]).year,
                (art[current]).description,
            )
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
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)

    )
    {
        Text(
            stringResource(artTitle),
            style = MaterialTheme.typography.displayLarge
        )
        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                stringResource(artistName),
                style = MaterialTheme.typography.displayMedium
            )
            Text(
               text = "(${stringResource(artistYear)})" ,
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

@Composable
fun NextButton(currentState: Int, incrementState: (Int) -> Unit) {
    IconButton(onClick ={
        incrementState(currentState + 1)
    },
        enabled = (currentState != art.size-1)
    ) {
        Icon(Icons.Filled.ArrowForward, contentDescription = "" )
    }
}

@Composable
fun PreviousButton(currentState: Int, decrementState: (Int) -> Unit) {
    IconButton(onClick = {
        decrementState(currentState - 1)
    },
        enabled = (currentState != 0)) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "" )
    }
}



@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceGoogleCourseTheme {
        ArtSpaceApp()
    }
}