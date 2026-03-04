package com.didan.jetpack.compose.jetpackmovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.didan.jetpack.compose.jetpackmovieapp.repository.MovieRepository
import com.didan.jetpack.compose.jetpackmovieapp.screens.MovieScreen
import com.didan.jetpack.compose.jetpackmovieapp.ui.theme.JetpackMovieAppTheme
import com.didan.jetpack.compose.jetpackmovieapp.viewmodel.MovieViewModel
import com.didan.jetpack.compose.jetpackmovieapp.viewmodel.MovieViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // repository
        val repository = MovieRepository(applicationContext)
        // ViewModelFactory
        val viewModelFactory = MovieViewModelFactory(repository)

        // viewModel
        val movieViewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[MovieViewModel::class.java]

        setContent {
            JetpackMovieAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier.padding(innerPadding)
                    ) {
                        HeaderComposable()
                        MovieScreen(movieViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderComposable() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "The Movie App",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Get the latest movies and TV shows",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}