package com.example.dotaheroes.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dotaheroes.R
import com.example.dotaheroes.data.Hero
import com.example.dotaheroes.network.BASE_URL

@Composable
fun HeroesScreen(
    heroesViewModel: HeroesViewModel = viewModel()
) {
    val uiState by heroesViewModel.uiState.collectAsState()
    when(uiState){
        is HeroesUiState.Loading -> LoadingScreen()
        is HeroesUiState.Success -> HeroesList((uiState as HeroesUiState.Success).heroes)
        is HeroesUiState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen() {

}

@Composable
fun HeroesList(
    heroes: List<Hero>
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        columns = GridCells.Fixed(2)
    ){
        items(heroes){ hero ->
            HeroesEntry(hero = hero)
            
        }
    }
}

@Composable
fun HeroesEntry(
    hero: Hero
) {
    val density = LocalDensity.current.density
    val width = remember {mutableStateOf(0F)}
    val height = remember { mutableStateOf(0F)}

    Card(
        modifier = Modifier.padding(6.dp),
        elevation = CardDefaults.cardElevation(8.dp)

    ) {
        Box() {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(BASE_URL+hero.img)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.mhw_placeholder),
            contentDescription = hero.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RectangleShape)
                .onGloballyPositioned {
                    width.value = it.size.width / density
                    height.value = it.size.height / density
                }
        )
        Box(modifier = Modifier.size(
            width = width.value.dp,
            height = height.value.dp
        ).background(
            Brush.verticalGradient(
                listOf(Color.Transparent, Color.Black),
                100F,
                500F
                )
            )
        )
        {
            
        }
        Text(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = hero.name,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White, fontWeight = FontWeight.Bold
            )
        )
        }
    }
}

@Composable
fun ErrorScreen() {

}