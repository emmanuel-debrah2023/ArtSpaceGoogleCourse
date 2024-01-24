package com.example.artspacegooglecourse.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Art(
    @DrawableRes val imageResourceID: Int,
    @StringRes val name: Int,
    @StringRes val artist: Int,
    @StringRes val year: Int,
    @StringRes val description: Int
)
