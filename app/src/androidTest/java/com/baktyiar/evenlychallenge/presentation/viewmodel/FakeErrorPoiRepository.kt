package com.baktyiar.evenlychallenge.presentation.viewmodel

import com.baktyiar.evenlychallenge.domain.model.Poi
import com.baktyiar.evenlychallenge.domain.repository.PoiRepository

class FakeErrorPoiRepository : PoiRepository {
    override suspend fun getNearbyPois(lat: Double, lng: Double): Result<List<Poi>> {
        return Result.failure(RuntimeException("Test error"))
    }
}