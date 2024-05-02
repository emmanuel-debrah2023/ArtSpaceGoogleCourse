package com.example.artspacegooglecourse.ui.test

import com.example.artspacegooglecourse.ui.ArtScreenUiState
import com.example.artspacegooglecourse.ui.ArtScreenViewModel
import com.example.artspacegooglecourse.data.Repository
import com.example.artspacegooglecourse.network.NetworkImageData
import com.example.artspacegooglecourse.ui.model.ImageData
import org.junit.Assert.assertEquals
import org.junit.Test
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Rule
import retrofit2.HttpException
import retrofit2.Response

// Reusable JUnit4 TestRule to override the Main dispatcher

val exampleObj = ImageData(2,"1","lorem ipsum latin","lorem ipsum","Mona Lisa",1999, "Spain")

class ArtScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    @MockK
    class mockRepository: Repository {

        override suspend fun getArtworkPhotosData(): List<NetworkImageData> {
            val mockListData: List<NetworkImageData> = mutableListOf(
                NetworkImageData(2,"1","html.com",true,"Mona Lisa", "1","lorem ipsum latin", "lorem ipsum",1999, "Spain")
            )
            return mockListData
        }

        override suspend fun getSpecificArtworkData(id: Int?): NetworkImageData {
            val mockPhotoData: NetworkImageData = NetworkImageData(2,"1","html.com",true,"Mona Lisa", "1","lorem ipsum latin", "lorem ipsum",1999,"Spain")
            return mockPhotoData
        }
    }





    @Test
    fun getArtScreenDetails_UiState_Success() = runTest{
        val expectedState = ArtScreenUiState.Success(
            exampleObj
        )
        val mockArtworkRepository = mockRepository()
        val viewModel = ArtScreenViewModel(mockArtworkRepository)

        advanceUntilIdle()
        val checkVal = viewModel.artScreenUiState
        assertEquals(expectedState, checkVal)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getArtScreenDetails_UiState_Error() = runTest{
        val expectedState = ArtScreenUiState.Error
        val mockRepository = mockk<Repository>(relaxed = true)
        val viewModel = ArtScreenViewModel(mockRepository)

        coEvery { mockRepository.getSpecificArtworkData(any())} throws HttpException(
                Response.error<Any>(404, ResponseBody.create(null, ""))
        )
        viewModel.getCurrentArtworkDetails(id)


        assertEquals(expectedState, viewModel.artScreenUiState)
    }

    @Test
    fun selectedArt_Updated_Successfully() = runTest{
        val selectedArtValue = "2"
        val mockRepository = mockk<Repository>(relaxed = true)
        val viewModel = ArtScreenViewModel(mockRepository)
        viewModel.setSelectedArtId("2")

        advanceUntilIdle()
        assertEquals(selectedArtValue,viewModel.getSelectedArtId())
    }
}