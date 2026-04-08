package com.aentrena.escalasrhb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.aentrena.escalasrhb.presentation.HomeScreen
import com.aentrena.escalasrhb.presentation.navigation.AppNavGraph
import com.aentrena.escalasrhb.presentation.theme.EscalasRhbTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EscalasRhbTheme {
                AppNavGraph()
            }
        }
    }
}

@Composable
fun HomeScreen() {

}

@Preview
@Composable
private fun HomeScreen_Preview() {
    HomeScreen()
}

