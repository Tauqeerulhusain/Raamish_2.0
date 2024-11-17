package com.clinic.raamish.screen.homeScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clinic.raamish.viewmodels.PatientViewModel


@Composable
fun SearchBar(
    onBackSwipe: () -> Unit,
    patientViewModel: PatientViewModel,
    focusRequester: FocusRequester
) {
    val searchText by patientViewModel.searchTextFlow.collectAsState()
    BackHandler(onBack = onBackSwipe)
    OutlinedTextField(
        value = searchText,
        onValueChange = patientViewModel::onSearchEmitFlow,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .focusRequester(focusRequester),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            focusedPrefixColor = Color.Red
        ),
        textStyle = TextStyle(fontSize = 18.sp),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = patientViewModel::clearSearch) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    )

    if (patientViewModel.isSearchButtonClicked) {
        LaunchedEffect(key1 = Unit) {
            try {
                focusonSearchBar(focusRequester, patientViewModel)
            } catch (exc: Exception) {
                println()
            }
        }
    }
}