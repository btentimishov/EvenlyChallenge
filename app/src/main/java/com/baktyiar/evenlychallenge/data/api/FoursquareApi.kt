package com.baktyiar.evenlychallenge.data.api


import retrofit2.http.GET
import retrofit2.http.Query

interface FoursquareApi {
    @GET("places/nearby")
    suspend fun getNearbyPlaces(
        @Query("ll") latLong: String,
        @Query("limit") limit: Int = 50
    ): FoursquareResponse
}
