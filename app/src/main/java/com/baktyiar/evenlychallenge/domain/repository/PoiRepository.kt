package com.baktyiar.evenlychallenge.domain.repository

import com.baktyiar.evenlychallenge.domain.model.Poi

interface PoiRepository {
    suspend fun getNearbyPois(lat: Double, lng: Double): Result<List<Poi>>
}