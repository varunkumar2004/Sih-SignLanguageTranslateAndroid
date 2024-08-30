package com.example.signlanguagetranslator.ui.screens

import android.util.Log
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    onCameraFlipClick: () -> Unit,
    onCameraClick: () -> Unit
) {
    LaunchedEffect(isRecording) {
        Log.d("is camera recording", isRecording.toString())
    }

    Box {
        CameraPreview(
            modifier = modifier
                .background(Color.LightGray),
            controller = controller
        )

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