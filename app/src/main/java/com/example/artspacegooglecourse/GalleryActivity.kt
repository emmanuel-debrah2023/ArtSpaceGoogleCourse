package com.example.artspacegooglecourse

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.artspacegooglecourse.components.GalleryGrid
import com.example.artspacegooglecourse.components.TopBar
import data.art


class GalleryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content() {
    Scaffold (
        topBar = {TopBar()}
    ){ innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            GalleryGrid(art)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Content()
}