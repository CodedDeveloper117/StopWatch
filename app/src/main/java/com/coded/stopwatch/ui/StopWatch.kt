package com.coded.stopwatch.ui

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object StopWatch {

    private var formattedTime by mutableStateOf("00:00:00")
    private var isActive by mutableStateOf(false)
    private var timeMillis = 0L
    private var scope = CoroutineScope(Dispatchers.Main)
    private var lastTimestamp = 0L

    fun start() {
        if(isActive) return
        scope.launch {
            lastTimestamp = System.currentTimeMillis()
            this@StopWatch.isActive = true
            while (isActive) {
                delay(10L)
                timeMillis = timeMillis + System.currentTimeMillis() - lastTimestamp
                lastTimestamp = System.currentTimeMillis()
                formattedTime = formatTime(timeMillis)
            }
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
    }

    fun reset() {
        scope.cancel()
        scope = CoroutineScope(Dispatchers.Main)
        timeMillis = 0L
        lastTimestamp = 0L
        formattedTime = "00:00.00"
        isActive = false
    }
}