package com.example.signlanguagetranslator.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.signlanguagetranslator.data.SignText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onQueryChange(msg: String) {
        _state.update { it.copy(textQuery = msg) }
    }

    fun sendPrompt() {
        _state.update {
            val newChat = SignText(
                value = it.textQuery,
                signToText = false
            )

            it.copy(
                chat = it.chat + newChat,
                textQuery = "",
            )
        }
    }

    fun updateVideoUri(uri: String) {
        _state.update { it.copy(videoUrl = uri) }
    }
}

data class HomeState(
    val textQuery: String = "",
    var chat: List<SignText> = listOf(
        SignText(
            value = "Hello World",
            signToText = true
        )
    ),
    var isCameraRecording: Boolean = false,
    val videoUrl: String = ""
)