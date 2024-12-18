package com.baktyiar.evenlychallenge.domain.usecase

import com.baktyiar.evenlychallenge.domain.model.Poi
import com.baktyiar.evenlychallenge.domain.repository.PoiRepository
import javax.inject.Inject

class GetNearbyPoisUseCase @Inject constructor(
    private val repository: PoiRepository
) {
    suspend operator fun invoke(lat: Double, lng: Double): Result<List<Poi>> {
        return repository.getNearbyPois(lat, lng)
    }
}