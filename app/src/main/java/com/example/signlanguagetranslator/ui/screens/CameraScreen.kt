package com.example.signlanguagetranslator.ui.screens

import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.signlanguagetranslator.ui.components.CameraPreview

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    controller: LifecycleCameraController,
    isRecording: Boolean,
    onCameraClick: () -> Unit
) {
    var selectedTimerDuration by remember {
        mutableStateOf<TimerDuration>(TimerDuration.TenSeconds)
    }

    Box(
        modifier = modifier
    ) {
        CameraPreview(
            modifier = modifier
                .background(Color.LightGray),
            controller = controller
        )

        Row(
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 10.dp)
                .align(Alignment.TopEnd)
                .clip(shape = RoundedCornerShape(40.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(start = 10.dp, end = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Timer,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(5.dp))

            AnimatedContent(targetState = selectedTimerDuration, label = "") { duration ->
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor =
                            if (duration == TimerDuration.TenSeconds) MaterialTheme.colorScheme.secondary
                            else Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.tertiary
                        ),
                        onClick = { selectedTimerDuration = TimerDuration.TenSeconds }
                    ) {
                        Text(text = "10s")
                    }

                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor =
                            if (duration == TimerDuration.FifteenSeconds) MaterialTheme.colorScheme.secondary
                            else Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.tertiary
                        ),
                        onClick = { selectedTimerDuration = TimerDuration.FifteenSeconds }
                    ) {
                        Text(text = "20s")
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(30.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                modifier = Modifier
                    .size(50.dp)
                    .border(1.dp, Color.Red, CircleShape),
                onClick = onCameraClick
            ) {
                AnimatedContent(
                    targetState = isRecording,
                    label = "Video Button"
                ) { recording ->
                    Box(
                        modifier = Modifier
                            .size(if (recording) 35.dp else 40.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                    )
                }
            }
        }
    }
}

sealed interface TimerDuration {
    data object TenSeconds : TimerDuration
    data object FifteenSeconds : TimerDuration
}