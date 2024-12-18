package com.baktyiar.evenlychallenge.presentation.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baktyiar.evenlychallenge.domain.model.Coordinates
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@SuppressLint("UnrememberedMutableState")
@Composable
fun MapView(name: String, location: Coordinates) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(location.latitude, location.longitude), 15f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = true,
            compassEnabled = false,
            myLocationButtonEnabled = false
        )
    ) {
        Marker(
            state = MarkerState(position = LatLng(location.latitude, location.longitude)),
            title = name,
            draggable = true
        )
    }
}