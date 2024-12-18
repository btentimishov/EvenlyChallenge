package com.baktyiar.evenlychallenge.presentation.ui.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.baktyiar.evenlychallenge.domain.model.Poi
import com.baktyiar.evenlychallenge.presentation.ui.MainActivity
import com.baktyiar.evenlychallenge.presentation.ui.components.CustomTopAppBar
import com.baktyiar.evenlychallenge.presentation.ui.components.MapView
import com.baktyiar.evenlychallenge.utils.openLink
import com.baktyiar.evenlychallenge.utils.shareLink

@Composable
fun PoiDetailScreen(
    poi: Poi? = null,
    onBack: () -> Unit
) {
    val activity = LocalContext.current as? MainActivity
    val scrollState = rememberScrollState()

    if (poi == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("POI not found", style = MaterialTheme.typography.bodyLarge)
        }
        return
    }
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = poi.name,
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onBack = onBack
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                poi.formattedAddress?.let { addr ->
                    Text(
                        text = "Address:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = addr,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (poi.distance != null) {
                    Text(
                        text = "Distance: ${poi.distance}m",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                if (poi.categories.isNotEmpty()) {
                    Text(
                        text = "Categories:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        poi.categories.forEachIndexed { index, category ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                val iconUrl = category.iconUrl
                                if (iconUrl != null) {
                                    AsyncImage(
                                        model = iconUrl,
                                        contentDescription = category.name,
                                        modifier = Modifier.size(40.dp)
                                    )
                                } else {
                                    Icon(
                                        Icons.Default.Info,
                                        contentDescription = category.name,
                                        modifier = Modifier.size(40.dp)
                                    )
                                }
                                Text(
                                    text = category.name,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
                if (poi.link.isNotBlank()) {
                    Text(
                        text = "More Info:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = poi.link,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable {
                            openLink(poi.link, activity)
                        }
                    )
                }
                Surface(
                    tonalElevation = 3.dp,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    Box {
                        MapView(name = poi.name, location = poi.location)
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    onClick = {
                        shareLink(poi.link, activity)
                    }
                ) {
                    Icon(Icons.Default.Share, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Share Link")
                }
            }
        }
    )
}
