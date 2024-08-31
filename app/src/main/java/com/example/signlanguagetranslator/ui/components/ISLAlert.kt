package com.example.signlanguagetranslator.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.signlanguagetranslator.R
import kotlinx.coroutines.delay

@Composable
fun ISLAlert(
    modifier: Modifier = Modifier,
    value: String,
    onDismissAlert: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    val images = listOf(
        R.drawable.praying,
        R.drawable.holding,
        R.drawable.praying,
        R.drawable.holding,
        R.drawable.praying,
    )
    var index by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = index) {
        if (index < images.size - 1) {
            delay(2000)
            index++
        }
    }

    val annotatedText = buildAnnotatedString {
        val words = value.split(" ")

        words.forEachIndexed { i, word ->
            if (i == index) {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                ) {
                    append(word)
                }
            } else {
                append(word)
            }

            append(" ")
        }
    }


    AlertDialog(
        modifier = modifier,
        containerColor = Color.White,
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
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = annotatedText,
                    color = Color.DarkGray
                )

                Box(
                    modifier = modifier
                        .clip(shape = shape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedContent(
                        modifier = modifier
                            .padding(vertical = 16.dp),
                        targetState = index,
                        label = "Scroll Animation"
                    ) { index ->
                        Image(
                            modifier = Modifier.size(200.dp),
                            painter = painterResource(id = images[index]),
                            contentDescription = "ISL Image 2"
                        )
                    }
                }
            }
        },
        onDismissRequest = onDismissAlert,
        dismissButton = {
            IconButton(
                onClick = { index = 0 },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Replay,
                    contentDescription = null
                )
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = Color.Black
                ),
                onClick = onDismissAlert
            ) {
                Text(text = "Done")
            }
        }
    )
}