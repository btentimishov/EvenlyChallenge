package com.baktyiar.evenlychallenge.data.mapper

import com.baktyiar.evenlychallenge.data.api.Category
import com.baktyiar.evenlychallenge.data.api.FoursquarePoi
import com.baktyiar.evenlychallenge.domain.model.Coordinates
import com.baktyiar.evenlychallenge.domain.model.Poi
import com.baktyiar.evenlychallenge.domain.model.PoiCategory

fun Category.toDomainCategory(): PoiCategory {
    val iconUrl = if (this.icon?.prefix != null && this.icon.suffix != null) {
        this.icon.prefix + "bg_64" + this.icon.suffix
    } else null

    return PoiCategory(
        name = this.name ?: "Unknown",
        iconUrl = iconUrl
    )
}

fun FoursquarePoi.toDomainPoi(): Poi {
    val latitude = this.geocodes?.main?.latitude ?: 0.0
    val longitude = this.geocodes?.main?.longitude ?: 0.0

    val categories = this.categories?.map { it.toDomainCategory() } ?: emptyList()

    return Poi(
        id = this.fsq_id,
        name = this.name ?: "Unknown",
        location = Coordinates(latitude, longitude),
        link = "https://foursquare.com/v/${this.fsq_id}",
        formattedAddress = this.location?.formattedAddress,
        distance = this.distance,
        categories = categories
    )
}
