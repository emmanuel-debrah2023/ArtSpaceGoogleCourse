package data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.artspacegooglecourse.R

data class Art(
    @DrawableRes val imageResourceID: Int,
    @StringRes val name: Int,
    @StringRes val artist: Int,
    @StringRes val description: Int,
    )

val art = listOf(
    Art(R.drawable.artwork_1,R.string.art_name_1,R.string.artist_name_1,R.string.art_description_1 ),
    Art(R.drawable.artwork_2,R.string.art_name_2,R.string.artist_name_2,R.string.art_description_2 ),
    Art(R.drawable.artwork_3,R.string.art_name_3,R.string.artist_name_3,R.string.art_description_3 ),
)