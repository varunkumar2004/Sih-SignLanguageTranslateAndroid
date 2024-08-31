package com.example.signlanguagetranslator.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PhotoCameraBack
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.signlanguagetranslator.ui.components.FloatingBottomBar
import com.example.signlanguagetranslator.ui.components.IndianSignLanguageToText
import com.example.signlanguagetranslator.ui.components.TextPrompt
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
    val lightPurple = Color(0xFFECE9F3)
    val mediumPurple = Color(0xFFCFC1EC)
    var showAlert by remember { mutableStateOf(false) }

    var selectedAlertText by remember {
        mutableStateOf("")
    }

    if (showAlert) {
        AlertDialog(
            icon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Outlined.Translate,
                        contentDescription = null
                    )

                    Text(
                        text = "Indian Sign Language",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            },
            text = {
                Text(
                    text = selectedAlertText,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            onDismissRequest = { showAlert = false },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mediumPurple,
                        contentColor = Color.Black
                    ),
                    onClick = {
                        showAlert = false
                    }
                ) {
                    Text(text = "Done")
                }
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
                color = mediumPurple,
                value = state.textQuery,
                onPromptChange = viewModel::onQueryChange,
                onPromptSend = viewModel::sendPrompt,
                onVideoTranslate = {
//                    showAlert = true
                }
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
                            color = lightPurple,
                            value = chat.value
                        )
                    } else {
                        TextToIndianSignLanguage(
                            modifier = Modifier.fillMaxWidth(),
                            value = chat.value,
                            color = lightPurple,
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