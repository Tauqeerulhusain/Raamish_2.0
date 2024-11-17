package com.clinic.raamish.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.clinic.raamish.models.Patient

@Dao
interface PatientDao {
//    @Query("SELECT * FROM Patient")
//    fun getAll(): Flow<List<Patient>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(patients: List<Patient>)

    @Update
    fun update(patient: Patient)
}