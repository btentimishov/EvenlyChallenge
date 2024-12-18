package com.baktyiar.evenlychallenge.presentation.ui.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.baktyiar.evenlychallenge.utils.loadBitmapFromUrl
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState


@SuppressLint("UnrememberedMutableState")
@Composable
fun PoiMarkerIcon(
    url: String,
    position: LatLng,
    title: String,
    snippet: String,
    onMarkerClick: () -> Boolean
) {
    val context = LocalContext.current

    val bitmap by produceState<Bitmap?>(initialValue = null, url) {
        value = loadBitmapFromUrl(context, url)
    }

    val bitmapDescriptor = remember(bitmap) {
        bitmap?.let { BitmapDescriptorFactory.fromBitmap(it) }
    }

    Marker(
        state = remember { MarkerState(position = position) },
        title = title,
        snippet = snippet,
        icon = bitmapDescriptor,
        onClick = { onMarkerClick() }
    )
}
