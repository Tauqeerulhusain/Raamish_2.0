package com.clinic.raamish.utility

import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow

val PATIENTID_KEY = intPreferencesKey("patient_id")
interface AppPreferences {
    fun getPatientId(): Flow<Int>

    suspend fun savePatientId(id: Int)
}