package com.sneha.weather.ui.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.sneha.weather.R
import com.sneha.weather.events.LocationSelectionClickEvents
import com.sneha.weather.ui.theme.BackgroundColor
import com.sneha.weather.ui.theme.BlueBackground
import com.sneha.weather.ui.theme.Typography

/**
 * Created by Sneha on 20-08-2024.
 */
@Composable
fun MapToolbar(
    isLocationPermissionEnabled: Boolean,
    askLocationPermission: () -> Unit,
    onClick: (LocationSelectionClickEvents) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BackgroundColor
            )
            .padding(top = 7.dp, bottom = 7.dp)
    ) {
        val (back, title, subTitle) = createRefs()
        IconButton(modifier = Modifier.constrainAs(back) {
            linkTo(
                top = parent.top, bottom = parent.bottom
            )
            start.linkTo(parent.start)
            end.linkTo(title.start, margin = 5.dp)
        }, onClick = {
            onClick(LocationSelectionClickEvents.OnBackClick)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "Back",
                tint = BlueBackground
            )
        }
        Text(
            modifier = Modifier.constrainAs(title) {
                linkTo(
                    top = parent.top,
                    bottom = if (isLocationPermissionEnabled) parent.bottom else subTitle.top
                )
                start.linkTo(back.end)
                end.linkTo(parent.end, margin = 10.dp)
                width = Dimension.fillToConstraints
            },
            text = stringResource(id = R.string.select_location),
            style = Typography.titleLarge.merge(fontWeight = FontWeight.Medium),
            color = BlueBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        if (!isLocationPermissionEnabled) {
            Text(modifier = Modifier
                .constrainAs(subTitle) {
                    linkTo(
                        start = title.start, end = title.end
                    )
                    top.linkTo(title.bottom, margin = 5.dp)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.preferredWrapContent
                }
                .clickable {
                    askLocationPermission()
                },
                text = stringResource(id = R.string.location_service_is_off),
                style = Typography.labelSmall.merge(
                    color = BlueBackground
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMapToolbar() {
    MapToolbar(isLocationPermissionEnabled = false, askLocationPermission = {}) {}
}