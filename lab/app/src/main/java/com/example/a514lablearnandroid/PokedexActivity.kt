package com.example.a514lablearnandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

class PokedexActivity : ComponentActivity() {

    private val viewModel: PokemonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Lifecycle", "PokedexActivity : onCreate")
        viewModel.fetchPokemon()
        enableEdgeToEdge()
        setContent {
            ListScreen(viewModel)
        }
    }
}

@Composable
fun ListScreen(viewModel: PokemonViewModel) {

    val pokemonList by viewModel.pokemonList.collectAsState()

    // ท่าง่าย
    Column(modifier = Modifier.fillMaxSize().background(Color.Red).padding(16.dp)) {
        Column(modifier = Modifier.fillMaxSize().background(Color.Gray).padding(16.dp)) {
            LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp)) {
                items(pokemonList) { item ->
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center) {
                        Text(text= item.entry_number.toString())
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text= item.pokemon_species.name)

                        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${item.entry_number}.png"

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .listener(
                                    onStart = {
                                        Log.d("AsyncImage", "Start loading: $imageUrl")
                                    },
                                    onError = { _, result ->
                                        Log.e("AsyncImage", "Error loading: $imageUrl", result.throwable)
                                    },
                                    onSuccess = { _, _ ->
                                        Log.d("AsyncImage", "Success loading: $imageUrl")
                                    }
                                )
                                .build(),
                            contentDescription = "Sprite of ${item.pokemon_species.name}",
                            modifier = Modifier.size(64.dp),
                            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                            error = painterResource(id = R.drawable.ic_launcher_background)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    ListScreen(PokemonViewModel())
}