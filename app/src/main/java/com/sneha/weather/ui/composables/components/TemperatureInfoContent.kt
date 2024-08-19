package com.sneha.weather.ui.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.sneha.weather.R
import com.sneha.weather.ui.theme.BlueBackground

/**
 * Created by Sneha on 16-08-2024.
 */
@Composable
fun TemperatureInfoContent(
    modifier: Modifier = Modifier,
    iconURL: String,
    currentTemperature: Int,
    temperatureUnit: String,
    weatherDescription: String,
    feelsLikeTemperature: Int
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(80.dp),
            model = ImageRequest.Builder(LocalContext.current).data(iconURL).crossfade(true)
                .diskCachePolicy(
                    CachePolicy.ENABLED
                )
                .build(),
            placeholder = painterResource(id = R.drawable.ic_weather_placeholder),
            error = painterResource(id = R.drawable.ic_weather_error),
            contentDescription = "Weather icon"
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 55.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            text = "$currentTemperature$temperatureUnit"
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            text = weatherDescription
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            text = "Feels like : $feelsLikeTemperature$temperatureUnit"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTemperatureInfoContent() {
    TemperatureInfoContent(
        modifier = Modifier.background(BlueBackground),
        iconURL = "",
        currentTemperature = 27,
        temperatureUnit = "°C",
        weatherDescription = "Cloudy",
        feelsLikeTemperature = 32
    )
}