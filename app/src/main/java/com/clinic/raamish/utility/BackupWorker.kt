package com.clinic.raamish.utility

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.clinic.raamish.RaamishApplication
import com.clinic.raamish.repository.PatientRepository
import javax.inject.Inject

class BackupWorker(val context: Context, params: WorkerParameters): Worker(context, params) {
    @Inject
    lateinit var patientRepository: PatientRepository
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        var appComponent = (context as RaamishApplication).applicationComponent
        appComponent.inject(this)

        patientRepository.backupDatabase()

        return Result.success()
    }

}