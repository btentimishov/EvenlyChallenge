package com.baktyiar.evenlychallenge.presentation.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.baktyiar.evenlychallenge.R
import com.baktyiar.evenlychallenge.domain.model.Poi
import com.baktyiar.evenlychallenge.presentation.ui.MainActivity
import com.baktyiar.evenlychallenge.presentation.ui.components.CustomLoadingIndicator
import com.baktyiar.evenlychallenge.presentation.ui.components.PoiMarkerIcon
import com.baktyiar.evenlychallenge.presentation.ui.theme.Orange
import com.baktyiar.evenlychallenge.presentation.viewmodel.PoiUiState
import com.baktyiar.evenlychallenge.presentation.viewmodel.PoiViewModel
import com.baktyiar.evenlychallenge.utils.shareLink
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

private val DEFAULT_EVENLY_LOCATION = LatLng(52.500342, 13.425170)
private const val DEFAULT_ZOOM = 17f
private val MAP_STYLE_JSON = """
    [
        {
            "featureType": "poi",
            "elementType": "all",
            "stylers": [
                { "visibility": "off" }
            ]
        }
    ]
""".trimIndent()

@Composable
fun PoisScreen(
    onPoiSelected: (Poi) -> Unit = {},
    onEvenlyMarkerClick: () -> Unit = {}
) {
    val viewModel: PoiViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadPois()
    }

    Scaffold(
        topBar = { PoisScreenTopBar(title = "POIs near Evenly HQ") }
    ) { paddingValues ->

        when (val state = uiState) {
            is PoiUiState.Success -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    MapScreen(
                        onShowDetail = onPoiSelected,
                        onEvenlyMarkerClick = onEvenlyMarkerClick,
                        pois = state.pois
                    )
                }
            }

            is PoiUiState.Error -> {
                LaunchedEffect(state.message) {
                    Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_LONG).show()
                }
            }

            is PoiUiState.Loading -> CustomLoadingIndicator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PoisScreenTopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.evenly_logo_vec),
                contentDescription = "evenly logo",
                modifier = Modifier.padding(8.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Orange)
    )
}

@SuppressLint("PotentialBehaviorOverride", "UnrememberedMutableState")
@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapScreen(
    onShowDetail: (Poi) -> Unit,
    onEvenlyMarkerClick: () -> Unit,
    pois: List<Poi> = emptyList()
) {
    var selectedPoi by remember { mutableStateOf<Poi?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val evenlyIcon = remember {
        BitmapDescriptorFactory.fromResource(R.drawable.image)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(DEFAULT_EVENLY_LOCATION, DEFAULT_ZOOM)
    }

    PoiDetailsDialog(
        showDialog = showDialog,
        selectedPoi = selectedPoi,
        onClose = { showDialog = false },
        onShowDetail = onShowDetail
    )

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = true),
        onMapLoaded = {}
    ) {
        MapEffect { map ->
            map.setMapStyle(MapStyleOptions(MAP_STYLE_JSON))
        }

        Marker(
            state = MarkerState(position = DEFAULT_EVENLY_LOCATION),
            title = "Evenly HQ",
            icon = evenlyIcon,
            onClick = {
                onEvenlyMarkerClick()
                true
            }
        )

        pois.forEach { poi ->
            val iconUrl = poi.categories.firstOrNull()?.iconUrl
                ?: "https://ss3.4sqi.net/img/categories_v2/arts_entertainment/themepark_bg_88.png"

            PoiMarkerIcon(
                url = iconUrl,
                position = LatLng(poi.location.latitude, poi.location.longitude),
                title = poi.name,
                snippet = poi.categories.firstOrNull()?.name ?: "Unknown Category",
                onMarkerClick = {
                    selectedPoi = poi
                    showDialog = true
                    true
                }
            )
        }
    }
}

@Composable
fun PoiDetailsDialog(
    showDialog: Boolean,
    selectedPoi: Poi?,
    onClose: () -> Unit,
    onShowDetail: (Poi) -> Unit
) {
    if (!showDialog || selectedPoi == null) return

    val activity = LocalContext.current as? MainActivity

    AlertDialog(
        onDismissRequest = { onClose() },
        confirmButton = {},
        title = null,
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape)
                ) {
                    val iconUrl = selectedPoi.categories.firstOrNull()?.iconUrl
                    if (iconUrl != null) {
                        AsyncImage(
                            model = iconUrl,
                            contentDescription = "Category Icon",
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Place,
                            contentDescription = "Placeholder Icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = selectedPoi.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )

                Text(
                    text = "Category: ${selectedPoi.categories.firstOrNull()?.name ?: "Unknown"}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                selectedPoi.distance?.let {
                    Text(
                        text = "Distance: $it meters",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 4.dp)
                    )
                }

                selectedPoi.formattedAddress?.let {
                    Text(
                        text = "Address: $it",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onClose) {
                        Text("Close")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = { shareLink(link = selectedPoi.link, activity = activity) },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Share")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onClose()
                        onShowDetail(selectedPoi)
                    }) {
                        Text("Details")
                    }
                }
            }
        }
    )
}
