package com.example.artspacegooglecourse.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Composable
fun ButtonWithIcon(
    icon: ImageVector,
    color: Color,
    @StringRes text: Int,
    onClick: () -> Unit
){
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(contentColor = color)
    ) {
        Icon(
            icon,
            contentDescription = ""
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(stringResource(text))
    }
}
