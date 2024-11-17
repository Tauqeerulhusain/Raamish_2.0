package com.clinic.raamish.api

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.clinic.raamish.models.Patient
import com.clinic.raamish.models.User
import com.clinic.raamish.utility.getTransformedPatient
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientApi @Inject constructor() {
    // Dev testing database
    var database = Firebase.database("https://raameshtest-default-rtdb.asia-southeast1.firebasedatabase.app/")
    var patientListFLow = MutableStateFlow<List<Patient>>(emptyList())
    var listRef: DatabaseReference
    var lastAddedPatientId = MutableStateFlow<Int>(-1)

    init {
        // Keep transaction remember across app restart in offline mode
        database.setPersistenceEnabled(true)
        listRef = database.getReference("list")
        listRef.keepSynced(true)
    }

    fun getAllPatientList() {
        listRef.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result: ArrayList<Patient> = arrayListOf()
                var patient: Patient?
                var lastPatientId = -1
                try {
                    for (patientSnapshot in dataSnapshot.children) {
                        patient = patientSnapshot.getValue<Patient>()
                        patient?.let {
                            if(it.id != -1) lastPatientId = it.id
                            result.add(getTransformedPatient(it))
                        }
                    }
                    patientListFLow.value = result.sortedByDescending { it.modifiedDateObj }
                    lastAddedPatientId.value = lastPatientId
                } catch (exc: Exception) {
                    println()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun update(patient: Patient) {
        listRef.child(patient.id.toString()).setValue(patient).addOnSuccessListener {
        }.addOnFailureListener {
            Log.d(ContentValues.TAG, "Value is:")
        }
    }

    fun getAllPatient() {
        var patient: Patient?
        listRef.get().addOnSuccessListener {
            if (it.exists()) {
                for (patientSnapshot in it.children) {
                    patient = patientSnapshot.getValue<Patient>()
                }
            }
        }.addOnFailureListener {
        }
    }

    fun insertPatient(patient: Patient) {
        listRef.child(patient.id.toString()).setValue(patient).addOnSuccessListener {
            Log.d(ContentValues.TAG, "Inserted Successfully")
        }.addOnFailureListener {
            Log.d(ContentValues.TAG, "Insert Failed")
        }
    }
}