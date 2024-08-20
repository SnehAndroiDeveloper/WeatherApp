package com.sneha.weather.ui.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import com.google.android.gms.maps.model.LatLng
import com.sneha.weather.R
import com.sneha.weather.data.models.WeatherInfoModel
import com.sneha.weather.events.LocationPopUpClickEvents
import com.sneha.weather.ui.theme.BlueBackground

/**
 * Created by Sneha on 17-08-2024.
 */
@Composable
fun LocationPopUpContent(
    weatherInfoModel: WeatherInfoModel,
    onClick: (LocationPopUpClickEvents) -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        val (location, region, temperature, addBtn) = createRefs()
        Text(
            text = weatherInfoModel.location,
            modifier = Modifier.constrainAs(location) {
                linkTo(
                    top = parent.top,
                    start = parent.start,
                    end = temperature.start,
                    bottom = region.top,
                    endMargin = 5.dp,
                    verticalBias = 0.0f,
                    horizontalBias = 0.0f
                )
                width = Dimension.fillToConstraints
            },
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black
        )
        val regionText =
            if (weatherInfoModel.region.isNotEmpty() && weatherInfoModel.country.isNotEmpty()) {
                "${weatherInfoModel.region}, ${weatherInfoModel.country}"
            } else if (weatherInfoModel.region.isNotEmpty()) {
                weatherInfoModel.region
            } else if (weatherInfoModel.country.isNotEmpty()) {
                weatherInfoModel.country
            } else {
                ""
            }
        Text(
            text = regionText,
            modifier = Modifier.constrainAs(region) {
                linkTo(
                    top = location.bottom,
                    topMargin = 5.dp,
                    endMargin = 5.dp,
                    start = parent.start,
                    end = location.end,
                    bottom = addBtn.top,
                    verticalBias = 0.0f,
                    horizontalBias = 0.0f
                )
                visibility = if (regionText.isEmpty()) Visibility.Invisible else Visibility.Visible
                width = Dimension.fillToConstraints
            },
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color.Gray
        )
        Text(
            text = "${weatherInfoModel.currentTemperature}${stringResource(id = R.string.degree_centigrade)}",
            modifier = Modifier.constrainAs(temperature) {
                linkTo(
                    top = parent.top,
                    start = location.end,
                    end = parent.end,
                    bottom = addBtn.top,
                    verticalBias = 0.0f,
                    horizontalBias = 0.7f
                )
                height = Dimension.fillToConstraints
            },
            fontSize = 35.sp,
            color = BlueBackground
        )
        Button(onClick = {
            onClick(
                LocationPopUpClickEvents.OnAddLocation(
                    LatLng(
                        weatherInfoModel.latitude.toDouble(),
                        weatherInfoModel.longitude.toDouble()
                    )
                )
            )
        }, modifier = Modifier.constrainAs(addBtn) {
            linkTo(
                top = temperature.bottom,
                start = temperature.start,
                end = parent.end,
                bottom = parent.bottom,
                verticalBias = 0.0f,
                horizontalBias = 1.0f
            )
        }) {
            Text(text = "Add")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLocationPopUpContent() {
    LocationPopUpContent(
        weatherInfoModel = WeatherInfoModel(
            location = "New Delhi",
            region = "Delhi",
            country = "India",
            currentTemperature = 25
        )
    )
}