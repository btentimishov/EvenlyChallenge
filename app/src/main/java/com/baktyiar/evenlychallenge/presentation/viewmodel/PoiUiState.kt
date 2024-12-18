package com.baktyiar.evenlychallenge.presentation.viewmodel

import com.baktyiar.evenlychallenge.domain.model.Poi

sealed class PoiUiState {
    object Loading : PoiUiState()
    data class Success(val pois: List<Poi>) : PoiUiState()
    data class Error(val message: String) : PoiUiState()
}