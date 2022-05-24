package com.lnight.networkcompose.ui.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.lnight.networkcompose.ui.mainscreen.viewmodel.MainViewModel
import com.lnight.networkcompose.ui.theme.Black

@Composable
fun MainScreen() {
    DogList()
}

@Composable
fun DogList(
    viewModel: MainViewModel = hiltViewModel()
) {
    val pokemonList by remember { viewModel.dogList }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember {viewModel.isSearching }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        val itemCount = if (pokemonList.size % 2 == 0) {
            pokemonList.size / 2
        } else {
            pokemonList.size / 2 + 1
        }
        items(itemCount) {
            if (it >= itemCount - 1 && !isLoading) {
                LaunchedEffect(key1 = true) {
                    viewModel.loadDogs()
                }
            }
            Card(
                modifier = Modifier.padding(15.dp),
                elevation = 5.dp,
                shape = RoundedCornerShape(10.dp)
            ) {


                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(pokemonList[it].message)
                                .crossfade(true)
                                .build(),
                            loading = {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colors.primary,
                                    modifier = Modifier
                                        .scale(0.3f)
                                )
                            },
                            contentDescription = "dog",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Black
                                        ),
                                        startY = 50f
                                    )
                                ),
                            contentScale = ContentScale.Crop,
                            alignment = Center
                        )
                    }

        }
    }
    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isSearching) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadDogs()
            }
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}