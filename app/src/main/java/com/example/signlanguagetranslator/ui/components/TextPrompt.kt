package com.example.signlanguagetranslator.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
fun TextPrompt(
    modifier: Modifier = Modifier,
    color: Color,
    value: String
) {
    val shape = RoundedCornerShape(30.dp)
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
                        fontWeight = FontWeight.Black,
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

    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = color,
            contentColor = Color.Black
        ),
        shape = shape
    ) {
        Column(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Default.Translate,
                    contentDescription = null
                )

//            Text(
//                text = value
//            )

                Text(
                    text = annotatedText
                )
            }

            Box(
                modifier = modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = index,
                    label = "Scroll Animation"
                ) { index ->
                    Image(
                        modifier = Modifier.size(200.dp),
                        painter = painterResource(id = images[index]),
                        contentDescription = "ISL Image 2"
                    )
                }

                IconButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onClick = { index = 0 }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Replay,
                        contentDescription = null
                    )
                }
            }
        }
    }
}