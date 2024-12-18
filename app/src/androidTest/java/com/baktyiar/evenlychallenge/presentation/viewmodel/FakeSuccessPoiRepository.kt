package com.baktyiar.evenlychallenge.presentation.viewmodel

import com.baktyiar.evenlychallenge.domain.model.Coordinates
import com.baktyiar.evenlychallenge.domain.model.Poi
import com.baktyiar.evenlychallenge.domain.model.PoiCategory
import com.baktyiar.evenlychallenge.domain.repository.PoiRepository

class FakeSuccessPoiRepository : PoiRepository {
    companion object {
        val fakePois = listOf(
            Poi(
                id = "1",
                name = "Fake Cafe",
                location = Coordinates(52.5200, 13.4050),
                link = "http://example.com/fakecafe",
                formattedAddress = "123 Fake St, Berlin",
                distance = 100,
                categories = listOf(
                    PoiCategory(name = "Cafe", iconUrl = "http://example.com/icon_cafe.png")
                )
            ),
            Poi(
                id = "2",
                name = "Fake Museum",
                location = Coordinates(52.5201, 13.4051),
                link = "http://example.com/fakemuseum",
                formattedAddress = "456 Museum Ln, Berlin",
                distance = 200,
                categories = listOf(
                    PoiCategory(name = "Museum", iconUrl = "http://example.com/icon_museum.png")
                )
            )
        )
    }

    override suspend fun getNearbyPois(lat: Double, lng: Double): Result<List<Poi>> {
        return Result.success(fakePois)
    }
}