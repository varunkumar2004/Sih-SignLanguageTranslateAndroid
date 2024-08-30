package com.example.signlanguagetranslator

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageProxy
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.video.AudioConfig
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
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
import com.google.mediapipe.formats.proto.LandmarkProto
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker
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
        cameraRecording: (Boolean) -> Unit
    ) {
        if(recording != null) {
            recording?.stop()
            recording = null
            return
        }

        if(!hasRequiredPermissions()) {
            return
        }

        val outputFile = File(filesDir, "my-recording.mp4")
        recording = controller.startRecording(
            FileOutputOptions.Builder(outputFile).build(),
            AudioConfig.create(true),
            ContextCompat.getMainExecutor(applicationContext),
        ) { event ->
            when(event) {
                is VideoRecordEvent.Start -> {
                    cameraRecording(true)
                }

                is VideoRecordEvent.Finalize -> {
                    cameraRecording(false)

                    if(event.hasError()) {
                        recording?.close()
                        recording = null

                        Toast.makeText(
                            applicationContext,
                            "Video capture failed",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Video capture succeeded",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

//    private fun setupHandLandmarker() {
//        // Set options for HandLandmarker
//        val options = HandLandmarkerOptions.builder()
//            .setBaseOptions(HandLandmarkerOptions.BaseOptions.builder()
//                .setModelAssetPath("hand_landmarker.task") // Your MediaPipe hand landmark model
//                .build())
//            .setRunningMode(RunningMode.LIVE_STREAM)
//            .build()
//
//        // Initialize Hand Landmarker
//        val handLandmarker = HandLandmarker.createFromOptions(this, options)
//    }
//
//    private fun processImage(imageProxy: ImageProxy) {
//        // Convert ImageProxy to Bitmap or other format required by MediaPipe
//        val bitmap = imageProxy.toBitmap()  // Implement this conversion method
//
//        // Run the hand landmark detection on the bitmap
//        val results = handLandmarker.detect(bitmap)
//        // Handle the results (landmarks, etc.)
//        handleHandLandmarkerResult(results)
//
//        imageProxy.close()
//    }

//    private fun setupHolistic() {
//        val options = HolisticOptions.builder()
//            .setMinDetectionConfidence(0.75f)
//            .setMinTrackingConfidence(0.75f)
//            .build()
//
//        holistic = Holistic(this, options)
//        holistic.setResultListener { result: HolisticResult? ->
//            if (result != null) {
//                handleHolisticResult(result)
//            }
//        }
//    }
//
//    private fun handleHolisticResult(result: HolisticResult) {
//        // Extract landmarks and perform prediction logic
//        val landmarks: List<LandmarkProto.NormalizedLandmarkList> = result.multiHandLandmarks()
//        // Implement your prediction logic here using the extracted landmarks.
//    }

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
