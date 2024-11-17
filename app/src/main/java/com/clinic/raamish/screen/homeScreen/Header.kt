package com.clinic.raamish.screen.homeScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.clinic.raamish.headerBottomBorder
import com.clinic.raamish.ui.theme.BackgroundLightGray
import com.clinic.raamish.viewmodels.PatientViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderSearch(
    patientViewModel: PatientViewModel,
    focusRequester: FocusRequester,
    onBackSwipe: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .headerBottomBorder(1.dp, Color.LightGray),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(onBackSwipe, patientViewModel, focusRequester)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(BackgroundLightGray),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderTitle(patientViewModel: PatientViewModel) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .headerBottomBorder(17.dp, Color.Green),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TitleBar()
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(BackgroundLightGray),
    )
}