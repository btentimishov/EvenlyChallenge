package com.baktyiar.evenlychallenge.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoursquareResponse(
    @Json(name = "results") val results: List<FoursquarePoi>
)

@JsonClass(generateAdapter = true)
data class FoursquarePoi(
    @Json(name = "fsq_id") val fsq_id: String,
    val name: String?,
    val link: String?,
    val geocodes: Geocodes?,
    val categories: List<Category>?,
    val distance: Int?,
    val location: Location?
)

@JsonClass(generateAdapter = true)
data class Geocodes(
    val main: LatLngData?
)

@JsonClass(generateAdapter = true)
data class LatLngData(
    val latitude: Double?,
    val longitude: Double?
)

@JsonClass(generateAdapter = true)
data class Category(
    val id: Int?,
    val name: String?,
    @Json(name = "short_name") val shortName: String?,
    @Json(name = "plural_name") val pluralName: String?,
    val icon: Icon?
)

@JsonClass(generateAdapter = true)
data class Icon(
    val prefix: String?,
    val suffix: String?
)

@JsonClass(generateAdapter = true)
data class Location(
    val address: String?,
    @Json(name = "address_extended") val addressExtended: String?,
    @Json(name = "formatted_address") val formattedAddress: String?,
    val locality: String?,
    val region: String?,
    val postcode: String?,
    val country: String?
)
