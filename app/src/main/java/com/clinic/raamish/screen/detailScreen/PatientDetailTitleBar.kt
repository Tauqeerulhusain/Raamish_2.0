package com.clinic.raamish.screen.detailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clinic.raamish.models.Patient
import com.clinic.raamish.ui.theme.urduFontFamily

@Composable
fun PatientDetailTitleBar(patient: Patient) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp, top = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            patient.name,
            fontFamily = urduFontFamily,
            fontSize = 17.sp,
        )
        Text(
            patient.address,
            fontFamily = urduFontFamily,
            fontSize = 11.sp,
            modifier = Modifier.padding(top = 12.dp),
            color = Color.DarkGray
        )
    }
}