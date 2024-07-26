package com.example.artspacegooglecourse

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.artspacegooglecourse.ui.model.ImageData
import com.example.artspacegooglecourse.ui.screens.PhotosGridScreen
import com.example.artspacegooglecourse.ui.theme.ArtSpaceGoogleCourseTheme
import org.junit.Rule
import org.junit.Test

class PhotosGridScreenUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun gridItem_isClicked_Successfully(){

        val imageData = List(size = 5){
            ImageData(
                id = it ,
                imageId = "1234",
                title = "Odyssey",
                description = "Beautiful painting",
                placeOfOrigin = "Italy",
                completionDate = 1892,
            )
        }
        composeTestRule.setContent {
            ArtSpaceGoogleCourseTheme {
                PhotosGridScreen(
                    imageData = imageData,
                    onArtSelect = {}
                )
            }
        }
            for(index in 0..4) {
                composeTestRule.onNodeWithTag("grid item $index")
                    .assertIsDisplayed()
                    .assertHasClickAction()
                    .performClick()
            }
    }
}