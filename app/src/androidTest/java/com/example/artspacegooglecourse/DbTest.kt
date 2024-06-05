package com.example.artspacegooglecourse

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.artspacegooglecourse.data.db.AppDatabase
import com.example.artspacegooglecourse.data.db.ImageDataEntity
import com.example.artspacegooglecourse.data.db.ImagesDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DaoTest {
    private lateinit var userDao: ImagesDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        userDao = db.getImageDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getImageById() = runBlocking {
        val testImage = ImageDataEntity(
            apiId = 1,
            imageId = "1234-1234",
            apiLink = "www.api.com",
            isBoosted = false,
            apiModel = "",
            completionDate = 1999,
            description = "",
            shortDescription = "",
            placeOfOrigin = "",
            title = ""
        )

        userDao.upsert(testImage)
        val expectedImage = userDao.getImageById(1)
        Assert.assertEquals(testImage, expectedImage)
    }

    @Test
    fun getImageList() = runBlocking {
        val testImage = ImageDataEntity(
            apiId = 1,
            imageId = "1234-1234",
            apiLink = "www.api.com",
            isBoosted = false,
            apiModel = "",
            completionDate = 1999,
            description = "",
            shortDescription = "",
            placeOfOrigin = "",
            title = ""
        )
        val testImageList = listOf(testImage)

        userDao.upsert(testImage)
        val expectedImage = userDao.getImages()
        Assert.assertEquals(testImageList, expectedImage)
    }

    @Test
    fun upsertImage() = runBlocking {
        val testImage = ImageDataEntity(
            apiId = 1,
            imageId = "1234-1234",
            apiLink = "www.api.com",
            isBoosted = false,
            apiModel = "",
            completionDate = 1999,
            description = "",
            shortDescription = "",
            placeOfOrigin = "",
            title = ""
        )

        userDao.upsert(testImage)
        Assert.assertEquals(userDao.getImageById(1), testImage)
    }

}