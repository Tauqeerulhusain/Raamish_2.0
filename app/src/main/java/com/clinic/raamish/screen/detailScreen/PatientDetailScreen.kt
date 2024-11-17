package com.clinic.raamish.screen.detailScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.clinic.raamish.ui.theme.TitleBarDarkYellow
import com.clinic.raamish.viewmodels.PatientViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDetailScreen(patientViewModel: PatientViewModel, onDoubleTap: () -> Unit){

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(90.dp),
                title = {
                    PatientDetailTitleBar(patientViewModel.selectPatient)
                },
                colors = TopAppBarDefaults.topAppBarColors(TitleBarDarkYellow),
            )
        },
    ) { innerPadding ->
        PatientDetailContent(
            Modifier
                .padding(innerPadding)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            onDoubleTap()
                        }
                    )
                },
            patientViewModel.selectPatient
        )
    }
}