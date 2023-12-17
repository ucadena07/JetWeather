package com.example.jetweatherforecast.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController){
    Scaffold(topBar = {
        WeatherAppBar(navController = navController,icon = Icons.Default.ArrowBack, isMainScreen = false, title = "About"){
            navController.popBackStack()
        }
    }) {innerPadding ->
        Surface(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.about_app), style = MaterialTheme.typography.headlineMedium)
                Text(text = stringResource(id = R.string.api_used), style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}