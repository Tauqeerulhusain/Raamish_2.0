package com.clinic.raamish

import android.Manifest
import android.app.role.RoleManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.clinic.raamish.screen.PermissionsScreen
import com.clinic.raamish.ui.theme.RaamishTheme
import com.clinic.raamish.viewmodels.PatientViewModel
import merail.tools.permissions.SettingsSnackbar
import merail.tools.permissions.role.RoleRequester
import merail.tools.permissions.role.RoleState
import merail.tools.permissions.runtime.RuntimePermissionRequester
import merail.tools.permissions.runtime.RuntimePermissionState
import merail.tools.permissions.special.SpecialPermissionRequester
import merail.tools.permissions.special.SpecialPermissionState
import javax.inject.Inject


class MainActivity : ComponentActivity() {
    @Inject
    lateinit var patientViewModel: PatientViewModel

    //--------Requesters--------//
    private lateinit var specialPermissionRequester: SpecialPermissionRequester
    private lateinit var runtimePermissionRequester: RuntimePermissionRequester
    private lateinit var roleRequester: RoleRequester
    //--------------------------//

    //--------Permissions--------//
    private val specialPermission = Manifest.permission.SYSTEM_ALERT_WINDOW
    private val runtimePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALL_LOG,
        )
    } else {
        arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG,
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private val rolePermission = RoleManager.ROLE_CALL_SCREENING
    //--------------------------//

    //--------Buttons visibility--------//
    private lateinit var isSpecialPermissionButtonVisible: MutableState<Boolean>
    private lateinit var isRuntimePermissionsButtonVisible: MutableState<Boolean>
    private lateinit var rolePermissionButtonVisible: MutableState<Boolean>
    //---------------------------------//

    //--------Request listeners--------//
    private val onSpecialPermissionClick = {
        specialPermissionRequester.requestPermission {
            isSpecialPermissionButtonVisible.value = it.second == SpecialPermissionState.DENIED
        }
    }
    private val onRuntimePermissionsClick = {
        runtimePermissionRequester.requestPermissions {
            isRuntimePermissionsButtonVisible.value =
                runtimePermissionRequester.areAllPermissionsGranted().not()
            if (it.containsValue(RuntimePermissionState.PERMANENTLY_DENIED)) {
                val settingsOpeningSnackbar = SettingsSnackbar(
                    activity = this,
                    view = window.decorView,
                )
                settingsOpeningSnackbar.showSnackbar(
                    text = "You must grant permissions in Settings!",
                    actionName = "Settings",
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private val rolePermissionClick = {
        roleRequester.requestRole {
            rolePermissionButtonVisible.value = it.second == RoleState.DENIED
        }
    }
    //---------------------------------//

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (application as RaamishApplication).applicationComponent
        appComponent.inject(this)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Environment.isExternalStorageManager()
            } else {
                TODO("VERSION.SDK_INT < R")
            }
        ) {
            // The app has been granted the MANAGE_EXTERNAL_STORAGE permission
        } else {
            // The app has not been granted the MANAGE_EXTERNAL_STORAGE permission
            // Request the permission from the user
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = Uri.parse("package:" + packageName)
            startActivity(intent)
        }

        enableEdgeToEdge()

        setContent {
            RaamishTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PermissionInitializer()
                }
            }
        }

        initPermissions()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Suppress("NonSkippableComposable")
    @Composable
    private fun PermissionInitializer() {
        isSpecialPermissionButtonVisible = remember {
            mutableStateOf(
                specialPermissionRequester.isPermissionGranted().not()
            )
        }
        isRuntimePermissionsButtonVisible = remember {
            mutableStateOf(
                runtimePermissionRequester.areAllPermissionsGranted().not()
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            rolePermissionButtonVisible = remember {
                mutableStateOf(
                    roleRequester.isRoleGranted().not()
                )
            }
        }

        val areAllPermissionsGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            specialPermissionRequester.isPermissionGranted()
                    && runtimePermissionRequester.areAllPermissionsGranted()
                    && roleRequester.isRoleGranted()
        } else {
            specialPermissionRequester.isPermissionGranted()
                    && runtimePermissionRequester.areAllPermissionsGranted()
        }

        if (areAllPermissionsGranted) {
            NavigationController(patientViewModel)
        } else {
            PermissionsScreen(
                areAllPermissionsGranted = areAllPermissionsGranted,
                onSpecialPermissionClick = onSpecialPermissionClick,
                isSpecialPermissionButtonVisible = isSpecialPermissionButtonVisible.value,
                onRuntimePermissionsClick = onRuntimePermissionsClick,
                isRuntimePermissionsButtonVisible = isRuntimePermissionsButtonVisible.value,
                rolePermissionClick = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    rolePermissionClick
                } else {
                    null
                },
                rolePermissionButtonVisible = rolePermissionButtonVisible.value,
            )
        }
    }

    private fun initPermissions() {
        specialPermissionRequester = SpecialPermissionRequester(
            activity = this,
            requestedPermission = specialPermission,
        )
        runtimePermissionRequester = RuntimePermissionRequester(
            activity = this,
            requestedPermissions = runtimePermissions,
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            roleRequester = RoleRequester(
                activity = this,
                requestedRole = rolePermission,
            )
        }
    }
}