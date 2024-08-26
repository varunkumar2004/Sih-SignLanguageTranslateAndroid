package com.example.signlanguagetranslator.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.signlanguagetranslator.R

@Composable
fun TextPrompt(
    modifier: Modifier = Modifier,
    color: Color,
    value: String
) {
    val shape = RoundedCornerShape(30.dp)
    val count = 5

    Column(
        modifier = modifier
            .clip(shape)
            .background(color)
            .border(2.dp, color, shape)
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

            Text(
                text = value
            )
        }


        ListItem(
            modifier = modifier.clip(shape),
            leadingContent = {
                Icon(
                    modifier = Modifier
                        .clickable {

                        },
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play/Pause"
                )
            },
            headlineContent = {
                LazyRow(
                    modifier = modifier
                ) {
                    items(count) {
                        Image(
                            modifier = Modifier
                                .size(100.dp),
                            painter = painterResource(id = R.drawable.praying),
                            contentDescription = null
                        )
                    }
                }
            },
        )
    }
}