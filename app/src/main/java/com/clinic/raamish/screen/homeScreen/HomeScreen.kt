package com.clinic.raamish.screen.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clinic.raamish.models.Patient
import com.clinic.raamish.ui.theme.BackgroundLightGray
import com.clinic.raamish.ui.theme.LogoDarkGreen
import com.clinic.raamish.ui.theme.LogoLightGreen
import com.clinic.raamish.viewmodels.PatientViewModel


@Composable
fun HomeScreen(patientViewModel: PatientViewModel, onClick: (patient: Patient) -> Unit) {
    val isSearchBarVisible by patientViewModel.isSearchBarVisible.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var isMenuButtonClicked = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (!isSearchBarVisible) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    HeaderTitle(patientViewModel)
                }
            } else {
                HeaderSearch(patientViewModel, focusRequester) {
                    patientViewModel.hideSearchBar()
                    patientViewModel.clearSearch()
                }
            }
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 33.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                FloatingActionButton(
                    onClick = { onClick(Patient(id = -1)) },
                    shape = CircleShape,
                    contentColor = White,
                    containerColor = LogoDarkGreen
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "fab icon")
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .height(95.dp),
                containerColor = BackgroundLightGray,
            ) {
                Footer(
                    patientViewModel,
                    { isMenuButtonClicked.value = !isMenuButtonClicked.value }) {
                    patientViewModel.isSearchButtonClicked = true
                    keyboardController?.show()
                    patientViewModel.showSearchBar()
                    focusonSearchBar(
                        focusRequester = focusRequester,
                        patientViewModel = patientViewModel
                    )
                }
            }
        }
    ) { innerPadding ->

        val patientList by patientViewModel.searchResults.collectAsState()
        if (isMenuButtonClicked.value) {
            TotalPatientCount(patientList.count().toString())
        } else {
            PatientList(Modifier.padding(innerPadding), patientViewModel, patientList, onClick)
        }
    }
}

fun focusonSearchBar(focusRequester: FocusRequester, patientViewModel: PatientViewModel) {
    try {
        focusRequester.requestFocus()
        patientViewModel.isSearchButtonClicked = false
    } catch (exc: Exception) {
        println()
    }
}

@Composable
fun TotalPatientCount(totalCount: String) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val gradientColors = listOf(LogoDarkGreen, LogoLightGreen)
        Text(
            "Total",
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            ),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            totalCount,
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