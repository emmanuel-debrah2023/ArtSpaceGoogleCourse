package com.example.artspacegooglecourse.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.artspacegooglecourse.R

data class ArtModel(
    @DrawableRes val imageResourceID: Int,
    @StringRes val name: Int,
    @StringRes val artist: Int,
    @StringRes val year: Int,
    @StringRes val description: Int
)

const val INDEX_INCREASE = 1
const val INDEX_DECREASE = 1

val art = listOf(
    ArtModel(R.drawable.artwork_1,R.string.art_name_1,R.string.artist_name_1,R.string.year_1,R.string.art_description_1 ),
    ArtModel(R.drawable.artwork_2,R.string.art_name_2,R.string.artist_name_2,R.string.year_2,R.string.art_description_2 ),
    ArtModel(R.drawable.artwork_3,R.string.art_name_3,R.string.artist_name_3,R.string.year_3,R.string.art_description_3 ),
)
