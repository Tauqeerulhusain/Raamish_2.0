package com.clinic.raamish.screen.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clinic.raamish.R
import com.clinic.raamish.ui.theme.LogoDarkGreen
import com.clinic.raamish.ui.theme.LogoLightGreen


@Composable
fun TitleBar(){
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        Image(
            painter = painterResource(id = R.drawable.ayurvedic_logo_transparent_dark),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 5.dp)
                .size(60.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
        ) {

            Image(
                painter = painterResource(id = R.drawable.r_logo_transparent),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
            )

            val gradientColors = listOf(LogoDarkGreen, LogoLightGreen)
            Text(
                "aamish Clinic",
                modifier = Modifier
                    .padding(top = 17.dp, start = 42.dp),
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    )
                ),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}