package com.clinic.raamish.utility

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AppDataPreferences(private val dataStore: DataStore<Preferences>) : AppPreferences {
    override fun getPatientId(): Flow<Int> {
        return dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                it[PATIENTID_KEY] ?: -1
            }
    }

    override suspend fun savePatientId(id: Int) {
        dataStore.edit {
            it[PATIENTID_KEY] = id
        }
    }
}