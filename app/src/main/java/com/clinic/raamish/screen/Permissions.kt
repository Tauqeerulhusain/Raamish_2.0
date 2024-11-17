package com.clinic.raamish.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PermissionsScreen(
    areAllPermissionsGranted: Boolean,
    onSpecialPermissionClick: () -> Unit,
    isSpecialPermissionButtonVisible: Boolean,
    onRuntimePermissionsClick: () -> Unit,
    isRuntimePermissionsButtonVisible: Boolean,
    rolePermissionClick: (() -> Unit)?,
    rolePermissionButtonVisible: Boolean,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        if (areAllPermissionsGranted) {
            Text(
                text = "All permissions are granted.\nJust use it!",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(24.dp),
            )
        } else {
            Button(
                onClick = onSpecialPermissionClick,
                text = "Get special permission",
                isVisible = isSpecialPermissionButtonVisible,
            )

            Button(
                onClick = onRuntimePermissionsClick,
                text = "Get runtime permissions",
                isVisible = isRuntimePermissionsButtonVisible,
            )

            rolePermissionClick?.let {
                Button(
                    onClick = it,
                    text = "Get role permissions",
                    isVisible = rolePermissionButtonVisible,
                )
            }
        }
    }
}

@Composable
 fun Button(
    onClick: () -> Unit,
    text: String,
    isVisible: Boolean,
) {
    if (isVisible) {
        androidx.compose.material3.Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(
                    minWidth = 72.dp,
                )
                .padding(5.dp),
            contentPadding = PaddingValues(
                vertical = 20.dp,
            ),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
            ),
        ) {
            Text(
                text = text,
                fontSize = 24.sp,
            )
        }
    }
}