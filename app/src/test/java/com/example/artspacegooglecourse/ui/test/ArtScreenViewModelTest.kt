package com.example.artspacegooglecourse.ui.test

import com.example.artspacegooglecourse.ui.ArtScreenUiState
import com.example.artspacegooglecourse.ui.ArtScreenViewModel
import com.example.artspacegooglecourse.data.Repository
import com.example.artspacegooglecourse.network.NetworkImageData
import com.example.artspacegooglecourse.ui.model.ImageData
import org.junit.Assert.assertEquals
import org.junit.Test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import retrofit2.HttpException
import retrofit2.Response



val exampleObj = ImageData(2, "1", "lorem ipsum latin", "lorem ipsum", "Mona Lisa", 1999, "Spain")

class ArtScreenViewModelTest {
    private  val mockArtworkRepository = mockk<Repository>(relaxed = true)
    private lateinit var viewModel: ArtScreenViewModel


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = ArtScreenViewModel(mockArtworkRepository)
    }

    @MockK
    class mockRepository : Repository {

        override suspend fun getArtworkPhotosData(): List<NetworkImageData> {
            val mockListData: List<NetworkImageData> = mutableListOf(
                NetworkImageData(
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
            )
            return mockListData
        }

        override suspend fun getSpecificArtworkData(id: Int?): NetworkImageData {
            val mockPhotoData = NetworkImageData(
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
            return mockPhotoData
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getArtScreenDetails successful response`() = runTest {
        val expectedState = ArtScreenUiState.Success(exampleObj)

        coEvery { mockArtworkRepository.getSpecificArtworkData(any()) } returns NetworkImageData(
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


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getArtScreenDetails handling error response`() = runTest {
        val expectedState = ArtScreenUiState.Error


        coEvery { mockArtworkRepository.getSpecificArtworkData(any()) } throws HttpException(
            Response.error<Any>(404, ResponseBody.create(null, ""))
        )
        viewModel.getCurrentArtworkDetails(2)


        assertEquals(expectedState, viewModel.artScreenUiState)
    }

    @Test
    fun `setSelectedArt updating state`() = runTest {
        val selectedArtValue = "2"
        viewModel.setSelectedArtId("2")

        advanceUntilIdle()
        assertEquals(selectedArtValue, viewModel.getSelectedArtId())
    }
}