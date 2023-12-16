package com.example.jetweatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetweatherforecast.R

import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDateTime
import com.example.jetweatherforecast.utils.formatDecimals



@Composable
fun WeatherDetailRow(weatherItem: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp),shape = RoundedCornerShape(size = 14.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),  verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = formatDate(weatherItem.dt).split(",")[0], modifier = Modifier.padding(start = 5.dp))
            WeatherStateImage(imageUrl = imageUrl)
            Surface(modifier = Modifier.padding(0.dp), shape = CircleShape, color = Color(0xFFFFC400)) {
                Text(text = weatherItem.weather[0].description, modifier = Modifier.padding(4.dp), style = MaterialTheme.typography.titleSmall)
            }
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f), fontWeight = FontWeight.SemiBold
                )
                ){
                    append(formatDecimals(weatherItem.temp.max) + "°")
                }
                withStyle(style = SpanStyle(
                    color = Color.LightGray, fontWeight = FontWeight.SemiBold
                )
                ){
                    append(formatDecimals(weatherItem.temp.min) + "°")
                }

            })
        }

    }
}

@Composable
fun SunsetSunRiseRow(weather: WeatherItem) {
    Row(modifier = Modifier
        .padding(top = 15.dp, bottom = 6.dp)
        .fillMaxWidth(),  verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
            Image(painter = painterResource(id = R.drawable.sunrise), contentDescription = "sunrise",modifier = Modifier.size(30.dp))
            Text(text = formatDateTime(weather.sunrise), style = MaterialTheme.typography.titleMedium)
        }
        Row {
            Text(text = formatDateTime(weather.sunset), style = MaterialTheme.typography.titleMedium)
            Image(painter = painterResource(id = R.drawable.sunset), contentDescription = "sunrise",modifier = Modifier.size(30.dp))

        }
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
        modifier = Modifier.size(75.dp)
    )
}