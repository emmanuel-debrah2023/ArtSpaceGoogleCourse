package com.example.artspacegooglecourse.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ImageDataEntity::class],
    version = 1
)
abstract class Database : RoomDatabase(){
    abstract fun getImageDao(): ImagesDao
}