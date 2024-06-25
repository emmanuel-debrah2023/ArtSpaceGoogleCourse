package com.example.artspacegooglecourse

import com.example.artspacegooglecourse.data.Repository
import com.example.artspacegooglecourse.data.db.ImageDataEntity
import com.example.artspacegooglecourse.ui.PhotoGridScreenUiState
import com.example.artspacegooglecourse.ui.PhotoGridScreenViewModel
import com.example.artspacegooglecourse.ui.model.ImageData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response


class PhotoGridScreenViewModelTest {
    private val mockRepository = mockk<Repository>(relaxed = true)
    private lateinit var viewModel: PhotoGridScreenViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = PhotoGridScreenViewModel(mockRepository)
    }

    @Test
    fun `PhotoGridScreenViewModel successful api call`() = runTest {
        val expectedState = PhotoGridScreenUiState.Success(
            mutableListOf(
                ImageData(2, "1", "lorem ipsum latin", "lorem ipsum", "Mona Lisa", 1999, "Spain")
            )
        )

        coEvery { mockRepository.getSavedImagesDataList() } returns mutableListOf(
            ImageDataEntity(
                apiId = 2,
                imageId = "1",
                apiLink = "html.com",
                isBoosted = true,
                title = "Mona Lisa",
                apiModel = "1",
                description = "lorem ipsum latin",
                shortDescription = "lorem ipsum",
                completionDate = 1999,
                placeOfOrigin = "Spain"
            )
        )

        viewModel.getPhotoGridData()
        assertEquals(expectedState, viewModel.artworkUiState)
    }

    @Test
    fun `PhotoGridScreenViewModel error on api call`() = runTest {
        val expectedState = PhotoGridScreenUiState.Error

        coEvery { mockRepository.getSavedImagesDataList() } throws HttpException(
            Response.error<Any>(
                404, "".toResponseBody(null)
            )
        )

        viewModel.getPhotoGridData()
        assertEquals(expectedState, viewModel.artworkUiState)
    }
}