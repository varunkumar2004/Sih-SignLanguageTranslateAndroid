package com.example.signlanguagetranslator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.signlanguagetranslator.ui.screens.HomeScreen
import com.example.signlanguagetranslator.ui.theme.SignLanguageTranslatorTheme
import com.example.signlanguagetranslator.ui.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val sModifier = Modifier.fillMaxSize()

            SignLanguageTranslatorTheme {
                HomeScreen(
                    modifier = sModifier,
                    viewModel = homeViewModel
                )
            }
        }
    }
}
