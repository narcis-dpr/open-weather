package com.narcis.openweatherinterview.data.model


data class WeatherItem(
     val id : Int,
     val main : String,
     val description : String,
     val temp : Double,
     val temp_min : Double,
     val temp_max : Double,
    val name : String
)
