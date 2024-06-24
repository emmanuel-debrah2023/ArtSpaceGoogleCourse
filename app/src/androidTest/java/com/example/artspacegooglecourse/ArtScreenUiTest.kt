package com.example.artspacegooglecourse

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.artspacegooglecourse.ui.model.ImageData
import com.example.artspacegooglecourse.ui.screens.ArtworkApiScreen
import com.example.artspacegooglecourse.ui.theme.ArtSpaceGoogleCourseTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtScreenUiTest{
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun galleryButton_isClicked_successfully(){
        composeTestRule.setContent {
            ArtSpaceGoogleCourseTheme {
                ArtworkApiScreen(onNavigateToGallery = {},
                    imageId = "1234-1234" ,
                    imageDetails = ImageData(
                    id = 1,
                    imageId = "1234-1234",
                    placeOfOrigin = "France",
                    description = "Work of beautiful art",
                    shortDescription = "Work of art",
                    title = "Mona Lisa"
                    )
                )
            }
        }

        composeTestRule.onNodeWithText("Gallery")//Investigate why this passed with text but not tag
            .assertIsDisplayed()
            .assertHasClickAction()
            .performClick()
    }

    @Test
    fun `artScreen_isDisplayed_successfully`(){
        composeTestRule.setContent {
            ArtSpaceGoogleCourseTheme {
                ArtworkApiScreen(onNavigateToGallery = {},
                    imageId = "1234-1234" ,
                    imageDetails = ImageData(
                        id = 1,
                        imageId = "1234-1234",
                        placeOfOrigin = "France",
                        description = "Work of beautiful art",
                        shortDescription = "Work of art",
                        title = "Mona Lisa",
                        completionDate = 1999
                    )
                )
            }
        }

        composeTestRule.onNodeWithText("Mona Lisa").assertIsDisplayed()
        composeTestRule.onNodeWithText("Work of art").assertIsDisplayed()
        composeTestRule.onNodeWithText("1999").assertIsDisplayed()
    }


}