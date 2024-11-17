package com.clinic.raamish.screen.homeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clinic.raamish.bottomBorder
import com.clinic.raamish.models.Patient
import com.clinic.raamish.ui.theme.HomePageBodyBrightYellow
import com.clinic.raamish.utility.getTimeAgoFromLocalDateTime
import com.clinic.raamish.viewmodels.PatientViewModel
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientList(
    modifier: Modifier = Modifier,
    patientViewModel: PatientViewModel,
    searchResults: List<Patient>,
    onClick: (patient: Patient) -> Unit
) {
    if (searchResults.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(HomePageBodyBrightYellow)
                    .bottomBorder(1.dp, Color.LightGray),
            ) {
                items(searchResults) { patient ->

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { onClick(patient) }
                            .padding(horizontal = 11.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            patient.name,
                        )
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            Text(
                                text = getTimeAgoFromLocalDateTime(patient.modifiedDateObj ?: LocalDateTime.now()),
                                fontSize = 12.sp,
                                color = Color.DarkGray,
                                modifier = Modifier
                                    .padding(top = 10.dp)
                            )
                        }
                    }
                    Divider()
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(1f)) {
            Text(text = "Loading...", style = MaterialTheme.typography.titleMedium)
        }
    }
}