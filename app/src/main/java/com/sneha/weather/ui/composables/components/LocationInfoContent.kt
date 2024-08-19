package com.sneha.weather.ui.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sneha.weather.ui.theme.BlackBackground

/**
 * Created by Sneha on 15-08-2024.
 */
@Composable
fun LocationInfoContent(
    modifier: Modifier = Modifier,
    location: String,
    region: String,
    country: String
) {
    Column(
        modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            text = location,
            color = Color.White
        )
        val regionText = if (region.isNotEmpty()) {
            "$region,"
        } else {
            ""
        }
        if (!(country.isEmpty() && regionText.isEmpty())) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                text = "$regionText $country"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLocationInfoContent() {
    LocationInfoContent(
        Modifier.background(BlackBackground),
        location = "Kolkata",
        region = "West Bengal",
        country = "India"
    )
}