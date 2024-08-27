package com.example.signlanguagetranslator.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PhotoCameraBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.signlanguagetranslator.ui.components.FloatingBottomBar
import com.example.signlanguagetranslator.ui.components.TextPrompt
import com.example.signlanguagetranslator.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsState()
    val lightPurple = Color(0xFFECE9F3)
    val mediumPurple = Color(0xFFCFC1EC)

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Translate") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.PhotoCameraBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            FloatingBottomBar(
                modifier = Modifier.fillMaxWidth(),
                color = mediumPurple,
                value = state.textQuery,
                onPromptChange = viewModel::onQueryChange,
                onPromptSend = viewModel::sendPrompt
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
                    TextPrompt(
                        modifier= Modifier.fillMaxWidth(),
                        value = chat,
                        color = lightPurple
                    )

                    Spacer(modifier = Modifier.height(10.dp))
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