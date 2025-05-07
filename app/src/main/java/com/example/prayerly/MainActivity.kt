package com.example.prayerly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.prayerly.controllers.PrayerListViewModel
import com.example.prayerly.screens.PrayerListScreen
import com.example.prayerly.ui.theme.PrayerlyTheme

class MainActivity : ComponentActivity() {
    private val viewModel: PrayerListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrayerlyTheme {
                PrayerListScreen(viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrayerlyTheme {
        Column() {
            Column() {
                Greeting("Android")
                Greeting(name = "Dan")
            }
            Row() {
                Greeting("Android")
                Greeting(name = "Dan")
            }
        }
    }
}