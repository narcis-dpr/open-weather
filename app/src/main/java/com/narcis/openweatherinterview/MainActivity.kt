 package com.narcis.openweatherinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.narcis.openweatherinterview.ui.viewModel.ForecastViewModel
import com.narcis.openweatherinterview.ui.viewModel.WeatherViewModel
import com.narcis.openweatherinterview.ui.viewModel.WeeklyViewModel
import com.narcis.openweatherinterview.ui.places.weather.WeatherContent
import com.narcis.openweatherinterview.ui.theme.OpenWeatherInterviewTheme
import dagger.hilt.android.AndroidEntryPoint


 @AndroidEntryPoint
 class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OpenWeatherInterviewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PlacesCategoriesDestination()
//                    WeatherContent()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

 @ExperimentalPermissionsApi
 @Composable fun PlacesCategoriesDestination() {
     val weatherViewModel: WeatherViewModel = hiltViewModel()
     val forecastViewModel : ForecastViewModel = hiltViewModel()
     val forecastWeeklyViewModel : WeeklyViewModel = hiltViewModel()
     weatherViewModel.getWeatherByLat()
     forecastViewModel.getForecastByLat()
     forecastWeeklyViewModel.getWeeklyByLocation()
     WeatherContent(weatherViewModel, forecastViewModel, forecastWeeklyViewModel)
 }
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OpenWeatherInterviewTheme {
        val viewModel: WeatherViewModel = hiltViewModel()
        val forecastViewModel : ForecastViewModel = hiltViewModel()
        val forecastWeeklyViewModel : WeeklyViewModel = hiltViewModel()
        WeatherContent(viewModel, forecastViewModel, forecastWeeklyViewModel)
    }
}