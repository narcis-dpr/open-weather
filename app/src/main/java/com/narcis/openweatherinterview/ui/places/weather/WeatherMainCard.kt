package com.narcis.openweatherinterview.ui.places.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.narcis.openweatherinterview.R
import com.narcis.openweatherinterview.data.model.WeatherItem
import com.narcis.openweatherinterview.data.model.WeeklyItem
import com.narcis.openweatherinterview.ui.places.viewModel.ForecastViewModel
import com.narcis.openweatherinterview.ui.places.viewModel.WeatherViewModel
import com.narcis.openweatherinterview.ui.places.viewModel.WeeklyViewModel
import com.narcis.openweatherinterview.ui.viewUtiles.LoadingContent
import com.narcis.openweatherinterview.ui.viewUtiles.verticalGradientScrim
import com.narcis.openweatherinterview.ui.widgets.AnimationSun
import com.narcis.openweatherinterview.ui.widgets.Sun

@Preview
@Composable
fun mat() {
//    Surface(modifier = Modifier.fillMaxSize(),
//    color = Color.Black) {
//    WeatherContent()
//    }

}

@Composable
fun WeatherContent(weatherViewModel: WeatherViewModel, forecastViewModel: ForecastViewModel,
                   foreCastWeeklyViewModel: WeeklyViewModel) {
    val weatherList by weatherViewModel.weatherResultsByLocation.collectAsState()
    val isLoading by weatherViewModel.isLoading.collectAsState()

    val forecastList by forecastViewModel.forecastResultByLocation.collectAsState()
    val forecastLoading by forecastViewModel.isLoading.collectAsState()

    val weeklyForecastList by foreCastWeeklyViewModel.weeklyResultByLocation.collectAsState()
    val weeklyLoading by foreCastWeeklyViewModel.isLoading.collectAsState()


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalGradientScrim(
                color = MaterialTheme.colors.primary.copy(alpha = 0.50f),
                startYPercentage = 1f,
                endYPercentage = 0f
            )
            .systemBarsPadding()
            .padding(horizontal = 8.dp)
    ) {
        LoadingContent(loading = weeklyLoading) {
            // We dynamically theme this sub-section of the layout to match the selected
            // 'top podcast'
            val (mainCard, weekCard) = createRefs()
            createVerticalChain(mainCard, weekCard, chainStyle = ChainStyle.Packed)
            LazyColumn(modifier = Modifier.constrainAs(mainCard) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(weekCard.top, margin = 4.dp)
            }) {

                item {


                    if (weatherList != null)
                        MainCard(weatherList!!.description,
                            weatherList!!.temp, weatherList!!.temp_max,
                            weatherList!!.temp_min, weatherList!!.name)
                }
                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                }
                item {
                    if (forecastList.isNotEmpty())
                        WeatherDaily(forecastList)
                }

            }

            Column(modifier = Modifier.constrainAs(weekCard) {
                top.linkTo(mainCard.bottom)
                bottom.linkTo(parent.bottom)
            }) {
//                val weeku = WeeklyItem(22.2F, 33.3F, "eeee")
//                val weather = WeatherItem(22, "&&", "77", 2.2, 2.2, 5.5, "fff")
//                val lst: List<WeeklyItem> =
//                    listOf(weeku, weeku, weeku, weeku, weeku, weeku, weeku)

                if (weeklyForecastList.isNotEmpty())
                    ForecastWeekly( weeklyForecastList)
            }
        }
    }
}


@Composable
fun MainCard(description: String, temp: Double, tempMax: Double, tempMin: Double, name : String) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(horizontal = 8.dp)
    ) {
        val (mainTemp, otherTemp, locationIcon, location, image) = createRefs()
        createVerticalChain( mainTemp, otherTemp, locationIcon, chainStyle = ChainStyle.Packed)
        Text(
            text = temp.toString(),
            style = MaterialTheme.typography.h3,
            color = Color.White,
            modifier = Modifier.constrainAs(mainTemp) {
                top.linkTo(parent.top, margin = 4.dp)
                start.linkTo(parent.start)

            }

        )
        Text(
            text = "$tempMax/$tempMin",
            style = MaterialTheme.typography.body1,
            color = Color.White.copy(alpha = (0.7f)),
            modifier = Modifier.constrainAs(otherTemp) {
                start.linkTo(mainTemp.end)
                end.linkTo(locationIcon.start)


            }
        )

        Card(
            modifier = Modifier
                .constrainAs(locationIcon) {},
            elevation = 4.dp,
            backgroundColor = Color.White.copy(alpha = 0.2f),
        ) {
            Row(modifier = Modifier.padding(2.dp)) {



                Image(
                    painter = painterResource(
                        id = R.drawable.ic_location
                    ),
                    contentDescription = "location",
                )

                Text(
                    text = name,
                    style = MaterialTheme.typography.body1,
                    color = Color.White,
                )

            }
        }

        AnimationSun(Modifier.size(200.dp).constrainAs(image) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)

        }
        )
    }


}

@Composable
fun TopBar(onBackPress: () -> Unit) {
    Row(modifier =  Modifier.fillMaxWidth()) {
        IconButton(onClick = onBackPress) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back" )
        }
        Spacer(Modifier.weight(1f))

        IconButton(onClick = { /* TODO */ }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }
    }
}

