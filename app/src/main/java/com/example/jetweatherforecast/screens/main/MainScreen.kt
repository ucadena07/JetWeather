package com.example.jetweatherforecast.screens.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.Weather
import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDecimals
import com.example.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel){
    val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(
        initialValue = DataOrException(loading = true)){
        value = mainViewModel.getWeatherData("Seattle")
    }.value
    if(weatherData.loading == true){
        CircularProgressIndicator()
    }else if(weatherData.data != null){
        MainScaffold(weather = weatherData.data!!,navController)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather,navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(title = "${weather.city.name}, ${weather.city.country}",
            navController = navController)
    }) {innerPadding ->
        MainContent(innerPadding, data = weather)
    }
}

@Composable
fun MainContent(padding: PaddingValues,data: Weather){
    var imageUrl = "https://openweathermap.org/img/wn/${data!!.list[0].weather[0].icon}.png"
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
            .size(200.dp), shape = CircleShape, color = Color(0xFFFFFC400)
        ) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(text = formatDecimals(data.list[0].temp.day) + "°", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold)
                Text(text = data.list[0].weather[0].main,  fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressureRow(weather = data.list[0])
        Divider()
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
   Row(modifier = Modifier
       .padding(12.dp)
       .fillMaxWidth(),
       verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
       Row(modifier = Modifier.padding(4.dp)) {
           Icon(painter = painterResource(id = R.drawable.humidity), contentDescription = "humidity",modifier = Modifier.size(20.dp))
           Text(text = "${weather.humidity}%", style = MaterialTheme.typography.titleMedium)
       }
       Row(modifier = Modifier.padding(4.dp)) {
           Icon(painter = painterResource(id = R.drawable.pressure), contentDescription = "pressure",modifier = Modifier.size(20.dp))
           Text(text = "${weather.pressure} psi", style = MaterialTheme.typography.titleMedium)
       }
       Row(modifier = Modifier.padding(4.dp)) {
           Icon(painter = painterResource(id = R.drawable.wind), contentDescription = "wind",modifier = Modifier.size(20.dp))
           Text(text = "${weather.speed} mph", style = MaterialTheme.typography.titleMedium)
       }
   }
}

@Composable
fun WeatherStateImage(imageUrl: String){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = "poster",
        modifier = Modifier.size(80.dp)
    )
}
