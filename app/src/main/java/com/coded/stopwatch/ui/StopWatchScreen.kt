package com.coded.stopwatch.ui

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coded.stopwatch.ui.theme.PrimaryPink
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StopWatchScreen(
    time: String,
    isActive: Boolean,
    isZero: Boolean,
    onStart: () -> Unit,
    onReset: () -> Unit,
    onPause: () -> Unit,
) {

    val infiniteTransition = rememberInfiniteTransition()
    val angleValue by infiniteTransition.animateFloat(
        initialValue = 270f,
        targetValue = 530f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val blurValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "StopWatch",
                    fontSize = 25.sp,
                    color = PrimaryPink,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(125.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(PrimaryPink)
                )
            }
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .clip(CircleShape)
                    .size(340.dp)
                    .background(
                        brush = Brush.radialGradient(
                            listOf(
                                PrimaryPink.copy(alpha = blurValue),
                                Color.Transparent
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {

                        val center = Offset(size.width / 2f, size.height / 2f)
                        /*val beta = (250f * sweepAngle + 145f) * (PI / 180f).toFloat()
                        val radius = size.width / 2f
                        val a = radius * cos(beta)
                        val b = radius * sin(beta)*/
                        drawPoints(
                            listOf(Offset(center.x, 0f)),
                            pointMode = PointMode.Points,
                            color = PrimaryPink,
                            strokeWidth = (30f),
                            cap = StrokeCap.Round
                        )
                        val angle = 270 * (PI / 180)
                        val radius = size.width / 2
                        val a = radius * cos(angle)
                        val b = radius * sin(angle)
                        Log.d(
                            "Screen",
                            "radius - $radius a-$a b-$b angle-$angle cos-${cos(angle)} cos-pi-${
                                cos(270f * ((22f / 7f) / 180f))
                            }"
                        )
                        drawPoints(
                            listOf(Offset(center.x, 0f)),
                            pointMode = PointMode.Points,
                            color = PrimaryPink,
                            strokeWidth = (30f),
                            cap = StrokeCap.Round
                        )
                    }
                    Text(
                        text = time,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                StopWatchButtons(
                    isActive = isActive,
                    isZero = isZero,
                    onStart = onStart,
                    onReset = onReset,
                    onPause = onPause,
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun StopWatchButtons(
    isActive: Boolean,
    isZero: Boolean,
    onStart: () -> Unit,
    onReset: () -> Unit,
    onPause: () -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        if (!isZero) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryPink.copy(alpha = 0.3f))
                    .clickable { onReset() }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Reset",
                    fontSize = 18.sp,
                    color = PrimaryPink,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryPink)
                    .clickable { if (isActive) onPause() else onStart() }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isActive) "Pause" else "Resume",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryPink)
                    .clickable { onStart() }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Start",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
    }

}