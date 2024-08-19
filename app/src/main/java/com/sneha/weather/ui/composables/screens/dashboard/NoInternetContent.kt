package com.sneha.weather.ui.composables.screens.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sneha.weather.R
import com.sneha.weather.ui.theme.CardBackground
import com.sneha.weather.ui.theme.NoInternetBackground
import com.sneha.weather.viewmodels.DashboardViewModel

/**
 * Created by Sneha on 17-08-2024.
 */
@Composable
fun NoInternetContent(modifier: Modifier = Modifier, viewModel: DashboardViewModel) {
    Card(
        border = BorderStroke(0.5.dp, Color.LightGray),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentHeight(Alignment.CenterVertically),
        colors = CardColors(
            containerColor = NoInternetBackground,
            contentColor = Color.DarkGray,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.Gray
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                painter = painterResource(id = R.drawable.ic_no_internet),
                contentDescription = "No internet",
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                text = "${stringResource(id = R.string.last_updated_error)} ${viewModel.weatherInfoModel.localTime}"
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNoInternetContent() {
    NoInternetContent(viewModel = DashboardViewModel())
}