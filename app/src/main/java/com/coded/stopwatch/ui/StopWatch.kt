package com.coded.stopwatch.ui

import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class StopWatch {

    var formattedTime by mutableStateOf("00:00:00")
    var isActive by mutableStateOf(false)
    var isZero by mutableStateOf(true)
    private var timeMillis = 0L
    private var lastTimestamp = 0L

    suspend fun start() {
        if(isActive) return
        lastTimestamp = System.currentTimeMillis()
        isActive = true
        isZero = false
        while (isActive) {
            delay(10L)
            timeMillis = timeMillis + System.currentTimeMillis() - lastTimestamp
            lastTimestamp = System.currentTimeMillis()
            formattedTime = formatTime(timeMillis)
            Log.d("Util", "time - $formattedTime")
        }
    }

    private fun formatTime(time: Long): String {
        val localDateTime: LocalDateTime
        val formatter: DateTimeFormatter
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(time),
                ZoneId.systemDefault()
            )
            formatter = DateTimeFormatter.ofPattern(
                "mm:ss:SS",
                Locale.getDefault()
            )
            return localDateTime.format(formatter)
        }
        return ""
    }

    fun pause() {
        isActive = false
        isZero = false
    }

    fun reset() {
        timeMillis = 0L
        lastTimestamp = 0L
        formattedTime = "00:00.00"
        isActive = false
        isZero = true
    }
}