package com.coded.stopwatch

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import com.coded.stopwatch.ui.StopWatch
import com.coded.stopwatch.ui.StopWatchScreen
import com.coded.stopwatch.ui.theme.StopWatchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val watch = StopWatch()

        setContent {
            StopWatchTheme {
                var state by remember {
                    mutableStateOf(StopWatchState.Stopped)
                }

                LaunchedEffect(key1 = state) {
                    when(state) {
                        StopWatchState.Running -> {
                            watch.start()
                            Log.d("Screen", "Running ${watch.formattedTime}")
                        }
                        StopWatchState.Paused -> {
                            Log.d("Screen", "Paused")
                            watch.pause()
                        }
                        StopWatchState.Stopped -> {
                            Log.d("Screen", "Stopped")
                            watch.reset()
                        }
                     }
                }

                val color = MaterialTheme.colors.surface

                LaunchedEffect(key1 = isSystemInDarkTheme()) {
                    window.apply {
                        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        statusBarColor = color.toArgb()
                    }
                }

                StopWatchScreen(
                    time = watch.formattedTime,
                    isActive = watch.isActive,
                    isZero = watch.isZero,
                    onStart = {
                        state = StopWatchState.Running
                    },
                    onPause = {
                        state = StopWatchState.Paused
                    },
                    onReset = {
                        state = StopWatchState.Stopped
                    },
                )
            }
        }
    }

    private enum class StopWatchState {
        Running, Paused, Stopped
    }
}