package com.clinic.raamish.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clinic.raamish.models.Patient
import com.clinic.raamish.repository.PatientRepository
import com.clinic.raamish.utility.AppPreferences
import com.clinic.raamish.utility.toIsoString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatientViewModel @Inject constructor(
    var patientRepository: PatientRepository,
    val appPref: AppPreferences
) : ViewModel() {
    var patientListFLow: Flow<List<Patient>> = patientRepository.patientListMergeFlow
    var searchTextFlow = MutableStateFlow("")
    val isSearchBarVisible = MutableStateFlow<Boolean>(false)
    var isSearchButtonClicked: Boolean = false
    var selectPatient = Patient()
    val lastAddedPatientId = appPref.getPatientId()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = -1
        )

    init {
        patientRepository.getAllPatientList()
        subscribeLastAddedPatientId()
    }

    val searchResults: StateFlow<List<Patient>> =
        searchTextFlow
            .combine(patientListFLow) { searchTxt, patientLst ->
                when {
                    searchTxt.isNotEmpty() -> patientLst.filter { patient ->
                        patient.name.contains(searchTxt, ignoreCase = true)
                                || patient.desc.contains(searchTxt, ignoreCase = true)
                                || patient.address.contains(searchTxt, ignoreCase = true)
//                            || patient.age.contains(searchTxt, ignoreCase = true)
                                || patient.mobileNo.contains(searchTxt, ignoreCase = true)
                    }

                    else -> patientLst
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    fun onSearchEmitFlow(newQuery: String) {
        searchTextFlow.value = newQuery
    }

    fun clearSearch() {
        searchTextFlow.value = ""
    }

    fun showSearchBar() {
        isSearchBarVisible.value = true
    }

    fun hideSearchBar() {
        isSearchBarVisible.value = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updatePatient(patient: Patient) {
        try {
            patient.modifiedDate = Date().toIsoString()
            patient.modifiedDateObj = LocalDateTime.now()
            patientRepository.updatePatient(patient)
        } catch (exc: Exception) {
            println()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addPatient(patient: Patient) {
        var lastId = lastAddedPatientId.value
        patient.id = ++lastId
        patient.date = Date().toIsoString()
        patient.modifiedDateObj = LocalDateTime.now()
        patientRepository.addPatient(patient)
        saveLastAddedPatientId(lastId)
    }

    fun saveLastAddedPatientId(lastId: Int) {
        viewModelScope.launch {
            try {
                appPref.savePatientId(lastId)
            } catch (exc: Exception) {
                println()
            }
        }
    }

    private fun subscribeLastAddedPatientId() {
        viewModelScope.launch {
            patientRepository.lastAddedPatientId.collect {
                saveLastAddedPatientId(it)
            }
        }
    }
}