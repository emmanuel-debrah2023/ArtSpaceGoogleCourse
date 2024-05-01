package com.example.artspacegooglecourse.ui.test

import com.example.artspacegooglecourse.ui.ArtScreenUiState
import com.example.artspacegooglecourse.ui.ArtScreenViewModel
import com.example.artspacegooglecourse.data.ArtworkRepository
import com.example.artspacegooglecourse.network.NetworkImageData
import com.example.artspacegooglecourse.ui.model.ImageData
import org.junit.Assert.assertEquals
import org.junit.Test
import io.mockk.coEvery
import io.mockk.core.ValueClassSupport.boxedValue
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Rule
import retrofit2.HttpException
import retrofit2.Response

// Reusable JUnit4 TestRule to override the Main dispatcher

val exampleObj = ImageData(2,"1","art piece 1", "lorem ipsum", 1999,"france")

class ArtScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    @MockK
    class mockRepository: ArtworkRepository {

        override suspend fun getArtworkPhotosData(): List<NetworkImageData> {
            val mockListData: List<NetworkImageData> = mutableListOf(
                NetworkImageData(2,"1","html.com",true,"art piece 1", "1","lorem ipsum", 1999,"france")
            )
            return mockListData
        }

        override suspend fun getSpecificArtworkData(id: String): NetworkImageData {
            val mockPhotoData: NetworkImageData = NetworkImageData(2,"1","html.com",true,"art piece 1", "1","lorem ipsum", 1999,"france")
            return mockPhotoData
        }
    }





    @Test
    fun getArtScreenDetails_UiState_Success() = runTest{
        val expectedState = ArtScreenUiState.Success(
            ImageData(2,"1","html.com","This piece",1999, "1")
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
        val mockArtworkRepository = mockk<ArtworkRepository>(relaxed = true)
        val viewModel = ArtScreenViewModel(mockArtworkRepository)

        coEvery { mockArtworkRepository.getSpecificArtworkData(any())} throws HttpException(
                Response.error<Any>(404, ResponseBody.create(null, ""))
        )
        viewModel.getCurrentArtworkDetails()


        assertEquals(expectedState, viewModel.artScreenUiState)
    }

    @Test
    fun selectedArt_Updated_Successfully() = runTest{
        val selectedArtValue = "2"
        val mockArtworkRepository = mockk<ArtworkRepository>(relaxed = true)
        val viewModel = ArtScreenViewModel(mockArtworkRepository)
        viewModel.setSelectedArtId("2")

        advanceUntilIdle()
        assertEquals(selectedArtValue,viewModel.getSelectedArtId())
    }
}