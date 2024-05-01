package com.example.artspacegooglecourse.ui.test

import com.example.artspacegooglecourse.ui.ArtworkUiState
import com.example.artspacegooglecourse.ui.ArtworkViewModel
import com.example.artspacegooglecourse.data.ArtworkRepository
import com.example.artspacegooglecourse.network.NetworkImageData
import com.example.artspacegooglecourse.ui.model.ImageData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response


class GalleryScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    @Test
    fun getGalleryScreenPhotoData_UiState_Success() = runTest{
        val expectedState =  ArtworkUiState.Success(
            mutableListOf(
                ImageData(2,"1","art piece 1", "lorem ipsum", 1999,"france")
            )
        )
        val mockArtworkRepository = ArtScreenViewModelTest.mockRepository()
        val viewModel = ArtworkViewModel(mockArtworkRepository)


        advanceUntilIdle()
        assertEquals(expectedState, viewModel.artworkUiState)
    }

    @Test
    fun getGalleryScreenPhotoData_UiState_Error() = runTest{
        val expectedState = ArtworkUiState.Error
        val mockArtworkRepository = mockk<ArtworkRepository>(relaxed = true)
        val viewModel = ArtworkViewModel(mockArtworkRepository)

       coEvery { mockArtworkRepository.getArtworkPhotosData()} throws HttpException(
            Response.error<Any>(404, ResponseBody.create(null, ""))
        )
        viewModel.getArtworkPhotosData()


        assertEquals(expectedState, viewModel.artworkUiState)
    }
}