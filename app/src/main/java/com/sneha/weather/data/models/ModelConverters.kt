package com.sneha.weather.data.models

import com.sneha.local_db.models.WeatherEntity
import com.sneha.weather.data.datasource.network.models.response.weather_info.WeatherInfoResponseModel
import com.sneha.weather.data.enums.DayOrNightEnum

/**
 * Created by Sneha on 16-08-2024.
 */

fun WeatherInfoResponseModel.convertToWeatherInfoModel(): WeatherInfoModel {
    val weatherInfoModel = WeatherInfoModel(
        location = this.location?.name.orEmpty(),
        region = this.location?.region.orEmpty(),
        country = this.location?.country.orEmpty(),
        isDay = this.current?.is_day?.lowercase() == DayOrNightEnum.Day.value.lowercase(),
        currentTemperature = this.current?.temperature ?: 0,
        feelsLikeTemperature = this.current?.feelslike ?: 0,
        weatherDescription = this.current?.weather_descriptions?.firstOrNull().orEmpty(),
        humidity = this.current?.humidity ?: 0,
        pressure = this.current?.pressure ?: 0,
        uvIndex = this.current?.uv_index ?: 0,
        windSpeed = this.current?.wind_speed ?: 0,
        localTime = this.location?.localtime.orEmpty(),
        weatherIconURL = this.current?.weather_icons?.firstOrNull().orEmpty(),
        sunSetTime = this.sunSet,
        sunRiseTime = this.sunRise,
        latitude = this.location?.lat.orEmpty(),
        longitude = this.location?.lon.orEmpty()
    )
    return weatherInfoModel
}

fun WeatherInfoResponseModel.convertToWeatherEntity(): WeatherEntity {
    val weatherEntity = WeatherEntity(
        latitude = this.location?.lat.orEmpty(),
        longitude = this.location?.lon.orEmpty(),
        localtime = this.location?.localtime.orEmpty(),
        location = this.location?.name.orEmpty(),
        region = this.location?.region.orEmpty(),
        country = this.location?.country.orEmpty(),
        isDay = this.current?.is_day?.lowercase() == DayOrNightEnum.Day.value.lowercase(),
        temperature = this.current?.temperature ?: 0,
        feelsLike = this.current?.feelslike ?: 0,
        weatherDescription = this.current?.weather_descriptions?.firstOrNull().orEmpty(),
        humidity = this.current?.humidity ?: 0,
        pressure = this.current?.pressure ?: 0,
        uvIndex = this.current?.uv_index ?: 0,
        windSpeed = this.current?.wind_speed ?: 0,
        weatherIcon = this.current?.weather_icons?.firstOrNull().orEmpty(),
        sunset = this.sunSet,
        sunrise = this.sunRise
    )
    return weatherEntity
}

fun WeatherEntity.convertToWeatherInfoModel(): WeatherInfoModel {
    val weatherInfoModel = WeatherInfoModel(
        location = this.location,
        region = this.region,
        country = this.country,
        isDay = this.isDay,
        currentTemperature = this.temperature,
        feelsLikeTemperature = this.feelsLike,
        weatherDescription = this.weatherDescription,
        humidity = this.humidity,
        pressure = this.pressure,
        uvIndex = this.uvIndex,
        windSpeed = this.windSpeed,
        localTime = this.localtime,
        weatherIconURL = this.weatherIcon,
        sunSetTime = this.sunset,
        sunRiseTime = this.sunrise,
        latitude = this.latitude,
        longitude = this.longitude
    )
    return weatherInfoModel
}

fun WeatherInfoModel.convertToWeatherEntity(): WeatherEntity {
    val weatherEntity = WeatherEntity(
        location = this.location,
        region = this.region,
        country = this.country,
        isDay = this.isDay,
        temperature = this.currentTemperature,
        feelsLike = this.feelsLikeTemperature,
        weatherDescription = this.weatherDescription,
        humidity = this.humidity,
        pressure = this.pressure,
        uvIndex = this.uvIndex,
        windSpeed = this.windSpeed,
        localtime = this.localTime,
        weatherIcon = this.weatherIconURL,
        latitude = latitude,
        longitude = longitude,
        sunset = this.sunSetTime,
        sunrise = this.sunRiseTime
    )
    return weatherEntity
}