package com.example.signlanguagetranslator

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.core.CameraSelector
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.video.AudioConfig
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.signlanguagetranslator.ui.screens.CameraScreen
import com.example.signlanguagetranslator.ui.screens.HomeScreen
import com.example.signlanguagetranslator.ui.theme.SignLanguageTranslatorTheme
import com.example.signlanguagetranslator.ui.viewmodels.HomeViewModel
import com.example.signlanguagetranslator.utils.Routes
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.gesturerecognizer.GestureRecognizer
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var recording: Recording? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }

        setContent {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val sModifier = Modifier.fillMaxSize()
            val navController = rememberNavController()
            val cameraController = remember {
                LifecycleCameraController(applicationContext).apply {
                    setEnabledUseCases(
                        CameraController.VIDEO_CAPTURE
                    )
                }
            }
            var isCameraRecording by remember {
                mutableStateOf(false)
            }

            SignLanguageTranslatorTheme {
                NavHost(
                    navController = navController,
                    startDestination = Routes.Home.route
                ) {
                    composable(route = Routes.Home.route) {
                        HomeScreen(
                            modifier = sModifier,
                            viewModel = homeViewModel,
                            onCameraNavigate = {
                                navController.navigate(Routes.Camera.route)
                            },
                            onProcessImage = {

                            }
                        )
                    }

                    composable(route = Routes.Camera.route) {
                        CameraScreen(
                            modifier = sModifier,
                            controller = cameraController,
                            isRecording = isCameraRecording,
                            onCameraFlipClick = {
                                cameraController.cameraSelector =
                                    if (cameraController.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                        CameraSelector.DEFAULT_FRONT_CAMERA
                                    } else CameraSelector.DEFAULT_BACK_CAMERA
                            },
                            onCameraClick = {
                                recordVideo(
                                    controller = cameraController,
                                    viewModel = homeViewModel,
                                    cameraRecording = { isRecording ->
                                        isCameraRecording = isRecording
                                    }
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun recordVideo(
        controller: LifecycleCameraController,
        viewModel: HomeViewModel,
        cameraRecording: (Boolean) -> Unit
    ) {
        if (recording != null) {
            recording?.stop()
            recording = null
            return
        }

        if (!hasRequiredPermissions()) {
            return
        }

        val outputFile = File(filesDir, "my-recording.mp4")
        recording = controller.startRecording(
            FileOutputOptions.Builder(outputFile).build(),
            AudioConfig.create(true),
            ContextCompat.getMainExecutor(applicationContext),
        ) { event ->
            when (event) {
                is VideoRecordEvent.Start -> {
                    cameraRecording(true)
                }

                is VideoRecordEvent.Finalize -> {
                    cameraRecording(false)

                    if (event.hasError()) {
                        recording?.close()
                        recording = null

                        Toast.makeText(
                            applicationContext,
                            "Video capture failed",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        val videoPath = outputFile.absolutePath
                        viewModel.updateVideoUri(videoPath)

                        Toast.makeText(
                            applicationContext,
                            "Video capture succeeded. saved to ${videoPath}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO
        )
    }
}
