package com.sneha.weather.ui.composables.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sneha.weather.R
import com.sneha.weather.ui.theme.BlueBackground
import com.sneha.weather.ui.theme.CardBackground

/**
 * Created by Sneha on 15-08-2024.
 */
@Composable
fun SunInfoContent(
    modifier: Modifier = Modifier,
    @DrawableRes
    iconId: Int,
    title: String,
    value: String
) {
    Card(
        border = BorderStroke(0.5.dp, Color.LightGray),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentHeight(Alignment.CenterVertically),
        colors = CardColors(
            containerColor = CardBackground,
            contentColor = Color.White,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.Gray
        ),
        shape = RoundedCornerShape(corner = CornerSize(4.dp))
    ) {
        Icon(
            modifier = Modifier
                .size(48.dp)
                .padding(top = 10.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = iconId),
            contentDescription = "icon"
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            text = title.uppercase()
        )
        Text(
            modifier = Modifier
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            text = value
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSunInfoContent() {
    SunInfoContent(
        iconId = R.drawable.ic_error,
        modifier = Modifier.background(BlueBackground),
        title = "Sunrise",
        value = "05:30 AM"
    )
}