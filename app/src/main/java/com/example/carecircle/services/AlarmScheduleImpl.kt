package com.example.carecircle.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.net.toUri
import com.example.carecircle.data.Medication
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar

class AlarmScheduleImpl(private val context: Context) : AlarmScheduler {
    override fun schedule(medication: Medication) {
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarm.canScheduleExactAlarms()) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = "package:${context.packageName}".toUri()
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            return
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("name", medication.name)
            putExtra("dosage", medication.dosage)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            medication.id.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        try {
            val triggerTime = xyz(medication.time)
            alarm.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
            Log.d("AlarmScheduler", "Alarm scheduled at $triggerTime")
        } catch (e: Exception) {
            Log.e("AlarmScheduler", "Failed to schedule alarm", e)
        }
    }


    override fun cancel(medication: Medication) {
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("name", medication.name)
            putExtra("dosage", medication.dosage)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            medication.id.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarm.cancel(pendingIntent)

    }
}
fun getMillisFromTimeString(time: String): Long {
    val parts = time.split(":")
    if (parts.size != 2) {
        Log.e("TimeParse", "Invalid time format: $time")
        return System.currentTimeMillis() + 60_000 // fallback
    }

    val hour = parts[0].toIntOrNull()
    val minute = parts[1].toIntOrNull()

    if (hour == null || minute == null) {
        Log.e("TimeParse", "Invalid numbers in time: $time")
        return System.currentTimeMillis() + 60_000
    }

    val now = Calendar.getInstance()
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)

        if (before(now)) {
            add(Calendar.DATE, 1)
        }
    }

    Log.d("TimeParse", "Final scheduled millis: ${calendar.timeInMillis}")
    return calendar.timeInMillis
}
fun xyz(time: String, timeZone: ZoneId = ZoneId.systemDefault()): Long {
    try { val parsedTime = LocalTime.parse(time)
        val now = ZonedDateTime.now(timeZone)
        var scheduledDateTime = now.with(parsedTime)
        if (scheduledDateTime.isBefore(now)) {
            scheduledDateTime = scheduledDateTime.plusDays(1)
        }

        Log.d("TimeParse", "Final scheduled millis: ${scheduledDateTime.toInstant().toEpochMilli()}")
        return scheduledDateTime.toInstant().toEpochMilli()

    } catch (e: Exception) {
        Log.e("TimeParse", "Invalid time format or number: $time", e)
        return System.currentTimeMillis() + 60_000 // Fallback
    }
}
