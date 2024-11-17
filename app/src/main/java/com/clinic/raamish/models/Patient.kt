package com.clinic.raamish.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude
import java.time.LocalDateTime

@Suppress("ANNOTATION_TARGETS_NON_EXISTENT_ACCESSOR")
@Entity
data class Patient(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var address: String = "",
    var age: String = "",
    var date: String = "",
    var modifiedDate: String = "",
    var appointmentDate: String = "",
    var appointmentNote: String = "",
    var desc: String = "",
    var disease: String = "",
    var mobileNo: String = "",
    @field:JvmField
    var isDeleted: Boolean = false,
){
    @Ignore
    private var _modifiedDateObj: LocalDateTime? = null
    var modifiedDateObj: LocalDateTime?
        @Exclude get() { return _modifiedDateObj }
        set(value) { _modifiedDateObj = value }
}
