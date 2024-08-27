package com.example.signlanguagetranslator.ui.screens

import android.hardware.lights.Light
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    onCameraFlipClick: () -> Unit,
    onCameraClick: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
//                    IconButton(onClick = onCameraFlipClick) {
//                        Icon(
//                            imageVector = Icons.Default.Cameraswitch,
//                            contentDescription = null
//                        )
//                    }

                    OutlinedIconButton(
                        modifier = Modifier.size(50.dp),
                        onClick = onCameraClick
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                        )
                    }

//                    Box(modifier = ) {
//
//                    }
                }
            }
        }
    ) { innerPadding ->
        CameraPreview(
            modifier = modifier
                .background(Color.LightGray)
                .padding(innerPadding),
            controller = controller
        )
    }
}