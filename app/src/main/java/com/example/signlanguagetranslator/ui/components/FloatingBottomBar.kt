package com.example.signlanguagetranslator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

@Composable
fun FloatingBottomBar(
    modifier: Modifier = Modifier,
    color: Color,
    value: String,
    onPromptChange: (String) -> Unit,
    onPromptSend: () -> Unit,
    onVideoTranslate: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val textFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        focusedContainerColor = color,
        unfocusedContainerColor = color,
        cursorColor = Color.Black
    )

    BottomAppBar(
        modifier = modifier,
        containerColor = Color.Transparent
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                modifier = Modifier,
                shape = RoundedCornerShape(30.dp),
                colors = textFieldColors,
                value = value,
                placeholder = {
                    Text(
                        text = "translate"
                    )
                },
                trailingIcon = {
                    IconButton(
                        modifier = Modifier
                            .size(TextFieldDefaults.MinHeight),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        ),
                        onClick = {
                            if (value.isNotEmpty() && value.isNotBlank()) {
                                onPromptSend()
                            }

                            keyboardController?.hide()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Translate,
                            contentDescription = "Send prompt"
                        )
                    }
                },
                onValueChange = onPromptChange
            )

            Spacer(modifier = Modifier.width(5.dp))

            IconButton(
                modifier = Modifier
                    .size(TextFieldDefaults.MinHeight),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                onClick = onVideoTranslate
            ) {
                Icon(
                    imageVector = Icons.Outlined.Videocam,
                    contentDescription = null
                )
            }
        }
    }
}