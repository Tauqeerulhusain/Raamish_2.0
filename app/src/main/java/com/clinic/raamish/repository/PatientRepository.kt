package com.clinic.raamish.repository

import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import com.clinic.raamish.api.PatientApi
import com.clinic.raamish.models.Patient
import com.clinic.raamish.room.AppRoomDatabase
import com.clinic.raamish.utility.firstAppRun
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.channels.FileChannel
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject


class PatientRepository @Inject constructor(
    private val patientApi: PatientApi, private val appRoomDb: AppRoomDatabase
) {
    var job: Job = Job()
//    private var patientListRoomFLow: Flow<List<Patient>> = appRoomDb.patientDao().getAll()

    //    @RequiresApi(Build.VERSION_CODES.O)
//    var patientListRoomFLowTransformed: Flow<List<Patient>> = patientListRoomFLow
//        .map {
//            val patientList: ArrayList<Patient> = arrayListOf()
//            for (patient in it) {
//                patientList.add(getTransformedPatient(patient))
//            }
//            patientList.sortedByDescending { it.modifiedDateObj }
//        }
    var patientListRoomFLowTransformed: Flow<List<Patient>> = flowOf(
        listOf<Patient>(
            Patient(
                12,
                "ندیم بن عقیلہ بانو",
                "گاندھی نگر",
                "۳۴",
                "10/06/2023",
                "2024-07-29T16:04:53.131Z",
                desc = "جریان ، سرعت انزال ،  پاخانہ  برابر ، ظنی دماغی کمزوری ،  نظر بد (گلونائن ) اور استخارہ میں بھی ۲    [     بیلا ، گلونائن   ۵     دانے    ۱  گھنٹہ      ؏     اغ حلوہ   فجرعشاء مع  دودھ   ایک چمچہ     ؏     معجون آردخرما 342 گ ، قسط شیریں ۲۰ گ(تھوڑاپانی )   ایک  چمچہ  بھر کر ظہر   ؏      ش ۲۷ ،   صبح شام      ]",
                mobileNo = "9637931320",
            )
        )
    )

    //    @RequiresApi(Build.VERSION_CODES.O)
//    var patientListMergeFlow: Flow<List<Patient>> =
//        merge(patientListRoomFLowTransformed, patientApi.patientListFLow)
//            .distinctUntilChanged()
    var patientListMergeFlow: Flow<List<Patient>> = patientApi.patientListFLow
        .map {
            it.filter { !it.isDeleted }
        }
    var lastAddedPatientId = patientApi.lastAddedPatientId
    var patientListStateFlow: StateFlow<List<Patient>> = patientListMergeFlow.stateIn(
            scope = CoroutineScope(Job()),
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    fun getAllPatientList() {
        patientApi.getAllPatientList()

        job = CoroutineScope(Dispatchers.IO).launch {
            patientListMergeFlow.collect {
                if(it.isNotEmpty()){
                    saveToRoom(it)
                    cancelJob()
                }
            }
        }
    }

    fun saveToRoom(patientList: List<Patient>) {
        try {
            appRoomDb.patientDao().insertAll(patientList)
        } catch (exc: Exception) {
            print(exc)
        }
    }

    fun updatePatient(patient: Patient) {
        CoroutineScope(Dispatchers.IO).launch {
            patientApi.update(patient)
        }
    }

    fun addPatient(patient: Patient) {
        CoroutineScope(Dispatchers.IO).launch {
            patientApi.insertPatient(patient)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun backupDatabase() {
        copyDataBase()
    }

    fun cancelJob(){
        job.cancel()
    }

    ////////////////////////////////////////////////data/data/com.clinic.raamish
    @RequiresApi(Build.VERSION_CODES.O)
    fun copyDataBase(): Boolean {
        val packageName = "com.clinic.raamish"
        try {
            val sd = Environment.getExternalStorageDirectory()
            val data = Environment.getDataDirectory()
            if (sd.canWrite()) {
                val dateTime: String =
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss"))
                val fileName = "RaamishDB_$dateTime"
                val currentDBPath = "//data//$packageName//databases//patientDB"
                val backupDBPath = "/Raamish"
                val currentDB = File(data, currentDBPath)
                var backupDB = File(sd.toString() + backupDBPath)
                if (!backupDB.exists()) backupDB.mkdir()
                backupDB = File(sd.toString() + backupDBPath + "/database")
                if (!backupDB.exists()) backupDB.mkdir()
                backupDB = File(sd, backupDBPath + "/database/$fileName")
                val src = FileInputStream(currentDB).channel
                val dst: FileChannel = FileOutputStream(backupDB).channel
                dst.transferFrom(src, 0, src.size())
                src.close()
                dst.close()
            }
        } catch (exc: java.lang.Exception) {
            print("")
        }
        return true
    }
}