package com.clinic.raamish.utility.trueCaller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.telecom.Call
import android.telecom.CallScreeningService
import androidx.annotation.RequiresApi
import com.clinic.raamish.RaamishApplication
import com.clinic.raamish.api.PatientApi
import com.clinic.raamish.dagger.component.DaggerApplicationComponent
import com.clinic.raamish.models.Patient
import com.clinic.raamish.viewmodels.PatientViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.N)
class IncomingCallService : CallScreeningService() {
    @Inject
    lateinit var patientApi: PatientApi

    companion object {
        @SuppressLint("StaticFieldLeak")
        private val incomingCallAlert = IncomingCallAlert()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        incomingCallAlert.closeWindow()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onScreenCall(callDetails: Call.Details) {
        try {
            var appComponent = (application as RaamishApplication).applicationComponent
            appComponent.inject(this)

            patientApi.getAllPatientList()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (callDetails.callDirection == Call.Details.DIRECTION_INCOMING) {
                    val phoneNumber = callDetails.handle.schemeSpecificPart
                    phoneNumber?.let {
                        var cutMob = it.trim().substring(3)
                        var _context = this
                        CoroutineScope(Dispatchers.Main).launch {
                            patientApi.patientListFLow.collect {
                                if (it.isNotEmpty()) {
                                    var patient = it.firstOrNull { patient -> patient.mobileNo == cutMob }
                                    incomingCallAlert.showWindow(
                                        _context,
                                        patient!!.mobileNo,
                                        patient
                                    )
                                }
                            }
                        }
                    }
                }
                respondToCall(callDetails, CallResponse.Builder().build())
            }

        } catch (exc: Exception) {
            println()
        }
    }
}