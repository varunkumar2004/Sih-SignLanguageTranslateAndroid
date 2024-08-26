package com.example.signlanguagetranslator.ui.viewmodels

import androidx.lifecycle.ViewModel
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
            it.copy(
                chat = it.chat + it.textQuery,
                textQuery = "",
            )
        }
    }
}

data class HomeState(
    val textQuery: String = "",
    var chat: List<String> = emptyList()
)