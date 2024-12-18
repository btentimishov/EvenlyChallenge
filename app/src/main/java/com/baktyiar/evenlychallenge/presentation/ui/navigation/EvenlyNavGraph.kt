package com.baktyiar.evenlychallenge.presentation.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.baktyiar.evenlychallenge.domain.model.Poi
import com.baktyiar.evenlychallenge.presentation.ui.screens.EvenlyDetailScreen
import com.baktyiar.evenlychallenge.presentation.ui.screens.PoiDetailScreen
import com.baktyiar.evenlychallenge.presentation.ui.screens.PoisScreen
import kotlinx.serialization.json.Json

sealed class Screen(val route: String) {
    object List : Screen("list")
    object EvenlyDetail : Screen("evenlyDetail")
    object PoiDetail : Screen("poiDetail/{poi}")
}

@Composable
fun EvenlyNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.List.route) {
        composable(Screen.List.route) {
            PoisScreen(
                onPoiSelected = { poi ->
                    val json = Uri.encode(Json.encodeToString(Poi.serializer(), poi))
                    navController.navigate("poiDetail/$json")
                },
                onEvenlyMarkerClick = {
                    navController.navigate(Screen.EvenlyDetail.route)
                }
            )
        }

        composable(Screen.EvenlyDetail.route) {
            EvenlyDetailScreen(onBack = { navController.popBackStack() })
        }

        composable(
            route = Screen.PoiDetail.route,
            arguments = listOf(navArgument("poi") { type = NavType.StringType })
        ) { backStackEntry ->
            val poiJson = requireNotNull(backStackEntry.arguments?.getString("poi")) {
                "POI argument is required but not found."
            }
            val poi = Json.decodeFromString(Poi.serializer(), poiJson)
            PoiDetailScreen(poi = poi, onBack = { navController.popBackStack() })
        }
    }
}
