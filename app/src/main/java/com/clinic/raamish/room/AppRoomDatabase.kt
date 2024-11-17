package com.clinic.raamish.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.clinic.raamish.models.Patient

@Database(entities = [Patient::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
}