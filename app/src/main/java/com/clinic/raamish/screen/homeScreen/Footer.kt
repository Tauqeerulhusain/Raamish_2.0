package com.clinic.raamish.screen.homeScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clinic.raamish.ui.theme.IconGray
import com.clinic.raamish.viewmodels.PatientViewModel


@Composable
fun Footer(patientViewModel: PatientViewModel, onMenuButtonClicked: () -> Unit, showSearchBar: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        IconButton(onClick = onMenuButtonClicked) {
            Icon(
                modifier = Modifier
                    .size(30.dp),
                imageVector = Icons.Filled.Menu,
                contentDescription = "Menu",
                tint = IconGray
            )
        }
        IconButton(onClick = showSearchBar) {
            Icon(
                modifier = Modifier
                    .size(30.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = "Search...",
                tint = IconGray
            )
        }
    }
}