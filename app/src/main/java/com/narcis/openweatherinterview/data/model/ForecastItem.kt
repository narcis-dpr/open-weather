package com.narcis.openweatherinterview.data.model

data class ForecastItem(
    val temp : Double,
    val temp_min : Double,
    val temp_max : Double,
    val description : String,
)