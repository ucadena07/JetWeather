package com.example.jetweatherforecast.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDecimals
import com.example.jetweatherforecast.widgets.HumidityWindPressureRow
import com.example.jetweatherforecast.widgets.SunsetSunRiseRow
import com.example.jetweatherforecast.widgets.WeatherAppBar
import com.example.jetweatherforecast.widgets.WeatherDetailRow
import com.example.jetweatherforecast.widgets.WeatherStateImage


@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel){
    val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(
        initialValue = DataOrException(loading = true)){
        value = mainViewModel.getWeatherData("La Crosse, US")
    }.value
    if(weatherData.loading == true){
        CircularProgressIndicator()
    }else if(weatherData.data != null){
        MainScaffold(weather = weatherData.data!!,navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(title = "${weather.city.name}, ${weather.city.country}",
            navController = navController, onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
            })
    }) {innerPadding ->
        MainContent(innerPadding, data = weather)
    }
}

@Composable
fun MainContent(padding: PaddingValues, data: Weather){
    val  imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"
    Column(modifier = Modifier
        .padding(padding)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = formatDate(data.list[0].dt),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        Surface(modifier = Modifier
            .padding(4.dp)
            .size(150.dp), shape = CircleShape, color = Color(0xFFFFC400)
        ) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(text = formatDecimals(data.list[0].temp.day) + "°", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold)
                Text(text = data.list[0].weather[0].main,  fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressureRow(weather = data.list[0])
        Divider()
        SunsetSunRiseRow(weather = data.list[0])
        Text(text = "This Week", style = MaterialTheme.typography.headlineSmall)
        Surface(modifier = Modifier.fillMaxSize(), color= Color(0xFFEEF1EF), shape = RoundedCornerShape(size = 14.dp)) {
            LazyColumn(modifier = Modifier.padding(4.dp), contentPadding = PaddingValues(1.dp)){
                items(items = data.list){
                    WeatherDetailRow(it)
                }
            }
        }
    }
}
