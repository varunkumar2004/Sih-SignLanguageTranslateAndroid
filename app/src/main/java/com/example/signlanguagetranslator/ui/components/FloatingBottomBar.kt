package com.example.signlanguagetranslator.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FloatingBottomBar(
    modifier: Modifier = Modifier,
    color: Color,
    value: String,
    onPromptChange: (String) -> Unit,
    onPromptSend: () -> Unit
) {
    BottomAppBar(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        containerColor = Color.Transparent
    ) {
        TextField(
            modifier = modifier
                .shadow(
                    elevation = 50.dp,
                    shape = RoundedCornerShape(30.dp)
                ),
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = color,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedContainerColor = color
            ),
            value = value,
            placeholder = {
                Text(text = "translate")
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(end = 5.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    onClick = onPromptSend
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Send prompt"
                    )
                }
            },
            onValueChange = onPromptChange
        )
    }
}