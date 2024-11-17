package com.clinic.raamish.dagger.module

import android.content.Context
import androidx.room.Room
import com.clinic.raamish.room.AppRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomDbModule {
    @Volatile
    public var INSTANCE: AppRoomDatabase? = null

    @Singleton
    @Provides
    fun provideAppRoomDatabase(context: Context): AppRoomDatabase {
        if (INSTANCE == null) {
            synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    "patientDB"
                )
                    .build()
            }
        }
        return INSTANCE!!
    }
}