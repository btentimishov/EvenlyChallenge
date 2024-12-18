package com.baktyiar.evenlychallenge.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.baktyiar.evenlychallenge.presentation.ui.navigation.EvenlyNavGraph
import com.baktyiar.evenlychallenge.presentation.ui.theme.EvenlyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EvenlyTheme {
                val navController = rememberNavController()
                EvenlyNavGraph(
                    navController = navController)
            }
        }
    }
}
