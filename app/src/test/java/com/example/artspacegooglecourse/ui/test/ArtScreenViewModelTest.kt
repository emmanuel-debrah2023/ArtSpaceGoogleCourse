package com.example.artspacegooglecourse.ui.test

import com.example.artspacegooglecourse.data.Repository
import com.example.artspacegooglecourse.network.NetworkImageData
import com.example.artspacegooglecourse.ui.ArtScreenUiState
import com.example.artspacegooglecourse.ui.ArtScreenViewModel
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


val exampleObj = ImageData(2, "1", "lorem ipsum latin", "lorem ipsum", "Mona Lisa", 1999, "Spain")

class ArtScreenViewModelTest {
    private val mockArtworkRepository = mockk<Repository>(relaxed = true)
    private lateinit var viewModel: ArtScreenViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = ArtScreenViewModel(mockArtworkRepository)
    }

    @Test
    fun `getArtScreenDetails successful response`() = runTest {
        val expectedState = ArtScreenUiState.Success(exampleObj)

        coEvery { mockArtworkRepository.getImageData(any()) } returns NetworkImageData(
            2,
            "1",
            "html.com",
            true,
            "Mona Lisa",
            "1",
            "lorem ipsum latin",
            "lorem ipsum",
            1999,
            "Spain"
        )
        viewModel.getCurrentArtworkDetails(2)

        assertEquals(expectedState, viewModel.artScreenUiState)
    }


    @Test
    fun `getArtScreenDetails handling error response`() = runTest {
        val expectedState = ArtScreenUiState.Error

        coEvery { mockArtworkRepository.getImageData(any()) } throws HttpException(
            Response.error<Any>(404, "".toResponseBody(null))
        )

        viewModel.getCurrentArtworkDetails(2)
        assertEquals(expectedState, viewModel.artScreenUiState)
    }


    @Test
    fun `setSelectedArt updates state corrects`() = runTest {
        val selectedArtValue = "2"
        viewModel.setSelectedArtId("2")

        assertEquals(selectedArtValue, viewModel.getSelectedArtId())
    }
}