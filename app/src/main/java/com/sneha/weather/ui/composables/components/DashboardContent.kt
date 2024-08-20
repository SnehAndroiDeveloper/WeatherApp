package com.sneha.weather.ui.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sneha.weather.R
import com.sneha.weather.ui.theme.BlackBackground
import com.sneha.weather.ui.theme.BlueBackground
import com.sneha.weather.viewmodels.DashboardViewModel

/**
 * Created by Sneha on 16-08-2024.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashboardContent(paddingValues: PaddingValues, viewModel: DashboardViewModel) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(if (viewModel.weatherInfoModel.isDay) BlueBackground else BlackBackground)
            .padding(top = 30.dp)
    ) {
        item {
            TemperatureInfoContent(
                modifier = Modifier.padding(15.dp),
                iconURL = viewModel.weatherInfoModel.weatherIconURL,
                currentTemperature = viewModel.weatherInfoModel.currentTemperature,
                temperatureUnit = stringResource(id = R.string.degree_centigrade),
                weatherDescription = viewModel.weatherInfoModel.weatherDescription,
                feelsLikeTemperature = viewModel.weatherInfoModel.feelsLikeTemperature
            )
        }
        item {
            FlowRow(
                maxItemsInEachRow = 4,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SunInfoContent(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    title = stringResource(id = R.string.sunrise),
                    iconId = R.drawable.ic_sunrise,
                    value = viewModel.weatherInfoModel.sunRiseTime
                )
                SunInfoContent(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    title = stringResource(id = R.string.sunset),
                    iconId = R.drawable.ic_sunset,
                    value = viewModel.weatherInfoModel.sunSetTime
                )
                SunInfoContent(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    title = stringResource(id = R.string.humidity),
                    iconId = R.drawable.ic_humidity,
                    value = "${viewModel.weatherInfoModel.humidity}${stringResource(id = R.string.percentage)}"
                )
                SunInfoContent(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    title = stringResource(id = R.string.uv_index),
                    iconId = R.drawable.ic_uv_index,
                    value = viewModel.weatherInfoModel.uvIndex.toString()
                )
                SunInfoContent(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    title = stringResource(id = R.string.wind),
                    iconId = R.drawable.ic_wind,
                    value = "${viewModel.weatherInfoModel.windSpeed} ${stringResource(id = R.string.wind_speed)}"
                )
                SunInfoContent(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    title = stringResource(id = R.string.pressure),
                    iconId = R.drawable.ic_pressure,
                    value = "${viewModel.weatherInfoModel.pressure} ${stringResource(id = R.string.pressure_unit)}"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDashboardContent() {
    DashboardContent(paddingValues = PaddingValues(), viewModel = DashboardViewModel())
}