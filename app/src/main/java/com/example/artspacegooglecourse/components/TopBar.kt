package com.example.artspacegooglecourse.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.artspacegooglecourse.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavTopBar(
    iconBtn: @Composable () -> Unit,
){
    CenterAlignedTopAppBar(
        title = {
            Row {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.logo_size))
                        .padding(dimensionResource(R.dimen.x_small_value)),
                    painter = painterResource(R.drawable.artspace_logo),
                    contentDescription = null
                )

            }
        },
        navigationIcon = {iconBtn()}
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Row {
                Image(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.logo_size))
                        .padding(dimensionResource(R.dimen.x_small_value)),
                    painter = painterResource(R.drawable.artspace_logo),
                    contentDescription = null
                )

            }
        }
    )

}