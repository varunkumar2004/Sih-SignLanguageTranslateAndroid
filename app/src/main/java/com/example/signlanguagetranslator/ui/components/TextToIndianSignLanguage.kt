package com.example.signlanguagetranslator.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextToIndianSignLanguage(
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray,
    value: String,
    onTranslate: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)

    Column(
        modifier = modifier
            .clip(shape)
            .border(1.dp, Color.LightGray, shape)
            .clickable { onTranslate() }
            .background(color)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Translate,
                contentDescription = null
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
        }

        Text(
            text = "Click to translate to Indian Sign Language.",
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TextSignPrev() {
    TextToIndianSignLanguage(
        modifier = Modifier.fillMaxWidth(),
        value = "nothing was ever same this is what i thoadskfh adkhf ladhlaksdfh not ralskdjfh jhsdfsfg a,sdbf ,masb",
        onTranslate = {

        }
    )
}