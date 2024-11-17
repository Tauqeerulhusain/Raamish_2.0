package com.clinic.raamish

import android.app.Application
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.clinic.raamish.dagger.component.ApplicationComponent
import com.clinic.raamish.dagger.component.DaggerApplicationComponent
import com.clinic.raamish.utility.BackupWorker
import com.clinic.raamish.utility.firstAppRun
import java.util.concurrent.TimeUnit

class RaamishApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        initialize()
        getFirstAppRun()
        setupWorkManager()
    }

    fun initialize(){
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
    fun getFirstAppRun(){
        val PREFS_NAME = "MyPrefsFile"
        val settings = getSharedPreferences(PREFS_NAME, 0)
        if (settings.getBoolean("firstAppRun", true)) {
            firstAppRun = true
            settings.edit().putBoolean("firstAppRun", false).commit()
        }
    }

    private fun setupWorkManager() {
        val workRequest = PeriodicWorkRequest.Builder(BackupWorker::class.java, 5, TimeUnit.DAYS)
            .build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

}