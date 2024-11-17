package com.clinic.raamish.screen.detailScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clinic.raamish.models.Patient
import com.clinic.raamish.ui.theme.HomePageBodyBrightYellow
import com.clinic.raamish.utility.Constant
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PatientDetailContent(modifier: Modifier, patient: Patient) {
    val formattedModifiedDate = if (patient.modifiedDateObj != null) DateTimeFormatter.ofPattern(Constant.DATE_TIME_FORMAT).format(patient.modifiedDateObj) else ""
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HomePageBodyBrightYellow)
            .padding(horizontal = 5.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(
                patient.mobileNo,
                fontSize = 13.sp,
                color = Color.DarkGray,
                fontFamily = FontFamily.Serif
            )
            Text(
                patient.age,
                fontSize = 13.sp,
                color = Color.DarkGray,
                fontFamily = FontFamily.Serif
            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Text(
                    formattedModifiedDate,
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    fontFamily = FontFamily.Serif
                )
            }
        }

        Text(
            modifier = Modifier
                .fillMaxSize(),
            text = patient.desc,
            lineHeight = 33.sp,
            fontSize = 15.sp
        )
    }
}