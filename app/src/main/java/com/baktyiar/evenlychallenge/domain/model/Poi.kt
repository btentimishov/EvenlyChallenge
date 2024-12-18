package com.baktyiar.evenlychallenge.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Poi(
    val id: String,
    val name: String,
    val location: Coordinates,
    val link: String,
    val formattedAddress: String?,
    val distance: Int?,
    val categories: List<PoiCategory>
)

@Serializable
data class PoiCategory(
    val name: String,
    val iconUrl: String?
)

@Serializable
data class Coordinates(
    val latitude: Double,
    val longitude: Double
)
