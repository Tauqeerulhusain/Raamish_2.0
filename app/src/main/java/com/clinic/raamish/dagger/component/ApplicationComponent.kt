package com.clinic.raamish.dagger.component

import android.content.Context
import com.clinic.raamish.MainActivity
import com.clinic.raamish.dagger.module.AppModule
import com.clinic.raamish.dagger.module.RoomDbModule
import com.clinic.raamish.utility.BackupWorker
import com.clinic.raamish.utility.trueCaller.IncomingCallService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomDbModule::class, AppModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainActivity: BackupWorker)
    fun inject(service: IncomingCallService)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}