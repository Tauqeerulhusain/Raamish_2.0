package com.clinic.raamish.utility

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.clinic.raamish.models.Patient
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date


var firstAppRun: Boolean = false
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@RequiresApi(Build.VERSION_CODES.O)
fun getTransformedPatient(patient: Patient): Patient {
//    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formatterUtc = DateTimeFormatter.ofPattern(Constant.ISO_TO_DATE_FORMAT)
    if (patient.modifiedDate.isBlank()) {
        patient.modifiedDateObj =
            LocalDateTime.parse(patient.date, formatterUtc)
//        patient.modifiedDateObj =
//            LocalDate.parse(patient.date, formatter).atStartOfDay()
    } else {
        patient.modifiedDateObj =
            LocalDateTime.parse(patient.modifiedDate, formatterUtc)
    }
    return patient
}

private const val ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

@JvmName("toIsoStringNullable")
fun Date?.toIsoString(): String? {
    return this?.toIsoString()
}

fun Date.toIsoString(): String {
    val dateFormat: DateFormat = SimpleDateFormat(Constant.DATE_TO_ISO_FORMAT)
    return dateFormat.format(this)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTimeAgoFromLocalDateTime(localDateTime: LocalDateTime): String {
    val date = convertLocalDateTimeToDate(localDateTime)
    val diff = Calendar.getInstance().time.time - date.time

    val oneSec = 1000L
    val oneMin: Long = 60 * oneSec
    val oneHour: Long = 60 * oneMin
    val oneDay: Long = 24 * oneHour
    val oneMonth: Long = 30 * oneDay
    val oneYear: Long = 365 * oneDay

    val diffMin: Long = diff / oneMin
    val diffHours: Long = diff / oneHour
    val diffDays: Long = diff / oneDay
    val diffMonths: Long = diff / oneMonth
    val diffYears: Long = diff / oneYear

    var result = ""
    when {
        diffYears > 0 -> {
            result += "$diffYears years ago"
        }

        diffMonths > 0 && diffYears < 1 -> {
            result += "${(diffMonths - diffYears / 12)} months ago "
        }

        diffDays > 0 && diffMonths < 1 -> {
            result += "${(diffDays - diffMonths / 30)} days ago "
        }

        diffHours > 0 && diffDays < 1 -> {
            result += "${(diffHours - diffDays * 24)} hours ago "
        }

        diffMin > 0 && diffHours < 1 -> {
            result += "${(diffMin - diffHours * 60)} min ago "
        }

        diffMin < 1 -> {
            result += "just now"
        }
    }

    return result
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertLocalDateTimeToDate(dateToConvert: LocalDateTime): Date {
    return Date
        .from(
            dateToConvert.atZone(ZoneId.systemDefault())
                .toInstant()
        )
}
