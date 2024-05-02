package com.example.artspacegooglecourse.ui.test

import com.example.artspacegooglecourse.ui.ArtworkUiState
import com.example.artspacegooglecourse.ui.PhotoGridScreenViewModel
import com.example.artspacegooglecourse.data.Repository
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


class PhotoGridScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    @Test
    fun getGalleryScreenPhotoData_UiState_Success() = runTest{
        val expectedState =  ArtworkUiState.Success(
            mutableListOf(
                ImageData(2,"1","lorem ipsum latin", "lorem ipsum","Mona Lisa", 1999,"Spain")
            )
        )
        val mockArtworkRepository = ArtScreenViewModelTest.mockRepository()
        val viewModel = PhotoGridScreenViewModel(mockArtworkRepository)


        advanceUntilIdle()
        assertEquals(expectedState, viewModel.artworkUiState)
    }

    @Test
    fun getGalleryScreenPhotoData_UiState_Error() = runTest{
        val expectedState = ArtworkUiState.Error
        val mockRepository = mockk<Repository>(relaxed = true)
        val viewModel = PhotoGridScreenViewModel(mockRepository)

       coEvery { mockRepository.getArtworkPhotosData()} throws HttpException(
            Response.error<Any>(404, ResponseBody.create(null, ""))
        )
        viewModel.getArtworkPhotosData()


        assertEquals(expectedState, viewModel.artworkUiState)
    }
}