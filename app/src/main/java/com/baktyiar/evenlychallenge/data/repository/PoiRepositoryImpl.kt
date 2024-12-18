package com.baktyiar.evenlychallenge.data.repository

import android.util.Log
import com.baktyiar.evenlychallenge.data.api.FoursquareApi
import com.baktyiar.evenlychallenge.data.mapper.toDomainPoi
import com.baktyiar.evenlychallenge.domain.model.Poi
import com.baktyiar.evenlychallenge.domain.repository.PoiRepository
import javax.inject.Inject

class PoiRepositoryImpl @Inject constructor(
    private val api: FoursquareApi
) : PoiRepository {
    override suspend fun getNearbyPois(lat: Double, lng: Double): Result<List<Poi>> {
        return try {
            val response = api.getNearbyPlaces("$lat,$lng")
            Log.d("PoiRepositoryImpl", "Response: $response")
            val pois = response.results.map { it.toDomainPoi() }
            Result.success(pois)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}