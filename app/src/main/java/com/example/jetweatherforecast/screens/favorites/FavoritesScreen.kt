package com.example.jetweatherforecast.screens.favorites

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.model.Favorite
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController, favoriteViewModel: FavoriteViewModel = hiltViewModel()){
    Scaffold(topBar = {
        WeatherAppBar(navController = navController,icon = Icons.Default.ArrowBack, isMainScreen = false, title = "Favorites Cities"){
            navController.popBackStack()
        }
    }) {innerPadding ->
        Surface(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                val list = favoriteViewModel.favList.collectAsState().value
                LazyColumn(){
                   items(items = list){
                        CityRow(it,navController = navController, favoriteViewModel)
                   }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel
) {
   Surface(modifier = Modifier
       .padding(3.dp)
       .fillMaxWidth()
       .height(50.dp)
       .clickable {
            navController.navigate("${WeatherScreens.MainScreen.name}/${favorite.city}")
       }, shape = CircleShape.copy(topEnd = CornerSize(6.dp)), color = Color(0xFFB2DFDB)){
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            Text(text = favorite.city, modifier = Modifier.padding(start = 4.dp))
            Surface(modifier = Modifier.padding(0.dp), shape = CircleShape,color = Color(0xFFD1E3E1)) {
                Text(text = favorite.country, modifier = Modifier.padding(4.dp), style = MaterialTheme.typography.titleMedium)
            }
            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "delete icon", tint = Color.Red.copy(0.3f), modifier = Modifier.clickable {
                favoriteViewModel.deleteFavorite(favorite)
            })
        }
   }
}
