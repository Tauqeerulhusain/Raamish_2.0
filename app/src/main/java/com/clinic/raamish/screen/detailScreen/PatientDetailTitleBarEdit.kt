package com.clinic.raamish.screen.detailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clinic.raamish.bottomBorder
import com.clinic.raamish.ui.theme.TextFieldBorderDarkYellow
import com.clinic.raamish.ui.theme.urduFontFamily
import com.clinic.raamish.viewmodels.PatientViewModel

@Composable
fun PatientDetailTitleBarEdit(patientViewModel: PatientViewModel) {
    var name by remember { mutableStateOf(patientViewModel.selectPatient.name) }
    var address by remember { mutableStateOf(patientViewModel.selectPatient.address) }
    val focusRequester = remember { FocusRequester() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp, top = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            BasicTextField(
                value = name,
                onValueChange = {
                    name = it
                    patientViewModel.selectPatient.name = it
                },
                modifier = Modifier
                    .width(200.dp)
                    .bottomBorder(1.dp, TextFieldBorderDarkYellow)
                    .focusRequester(focusRequester),
                textStyle = TextStyle(
                    fontSize = 17.sp,
                    fontFamily = urduFontFamily,
                ),
                singleLine = true,
            )
            if (name.isEmpty()) {
                Icon(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(17.dp),
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Phone",
                    tint = Color.Gray
                )
            }
        }
        Box {
            BasicTextField(
                value = address,
                onValueChange = {
                    address = it
                    patientViewModel.selectPatient.address = it
                },
                modifier = Modifier
                    .width(100.dp)
                    .padding(top = 14.dp)
                    .bottomBorder(1.dp, TextFieldBorderDarkYellow),
                textStyle = TextStyle(
                    fontSize = 11.sp,
                    fontFamily = urduFontFamily,
                ),
                singleLine = true,
            )
            if (address.isEmpty()) {
                Icon(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(15.dp),
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Phone",
                    tint = Color.Gray
                )
            }

        }
    }

    if (patientViewModel.selectPatient.id < 0) {
        var change = 0
        LaunchedEffect(key1 = change) {
            try {
                focusRequester.requestFocus()
                ++change
            } catch (exc: Exception) {
                println()
            }
        }
    }
}