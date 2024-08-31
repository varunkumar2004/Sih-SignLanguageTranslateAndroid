package com.example.signlanguagetranslator.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.signlanguagetranslator.ui.components.FloatingBottomBar
import com.example.signlanguagetranslator.ui.components.ISLAlert
import com.example.signlanguagetranslator.ui.components.IndianSignLanguageToText
import com.example.signlanguagetranslator.ui.components.TextToIndianSignLanguage
import com.example.signlanguagetranslator.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onCameraNavigate: () -> Unit,
    onProcessImage: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var showAlert by remember { mutableStateOf(false) }

    var selectedAlertText by remember {
        mutableStateOf("")
    }

    if (showAlert) {
        ISLAlert(
            modifier = Modifier.fillMaxWidth(),
            value = selectedAlertText,
            onDismissAlert = {
                showAlert = false
            }
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Translate") }
            )
        },
        bottomBar = {
            FloatingBottomBar(
                modifier = Modifier.fillMaxWidth(),
                value = state.textQuery,
                onPromptChange = viewModel::onQueryChange,
                onPromptSend = viewModel::sendPrompt,
                onVideoTranslate = onCameraNavigate
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn {
                items(state.chat) { chat ->
                    if (chat.signToText) {
                        IndianSignLanguageToText(
                            modifier = Modifier.fillMaxWidth(),
                            value = chat.value
                        )
                    } else {
                        TextToIndianSignLanguage(
                            modifier = Modifier.fillMaxWidth(),
                            value = chat.value,
                            onTranslate = {
                                showAlert = true
                                selectedAlertText = chat.value
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePrev() {
//    HomeScreen(
//        Modifier.fillMaxSize()
//    )
}