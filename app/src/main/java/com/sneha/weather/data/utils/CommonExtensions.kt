package com.sneha.weather.data.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * Created by Sneha on 20-08-2024.
 */
fun Context.goToAppSetting() {
    val i = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    startActivity(i)
}