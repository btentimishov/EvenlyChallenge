package com.baktyiar.evenlychallenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baktyiar.evenlychallenge.domain.usecase.GetNearbyPoisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoiViewModel @Inject constructor(
    private val getNearbyPoisUseCase: GetNearbyPoisUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PoiUiState>(PoiUiState.Loading)
    val uiState: StateFlow<PoiUiState> = _uiState.asStateFlow()

    fun loadPois() = viewModelScope.launch {
        _uiState.update { PoiUiState.Loading }
        val result = getNearbyPoisUseCase(
            lat = 52.500342,
            lng = 13.425170
        )

        result.fold(
            onSuccess = { pois ->
                _uiState.update { PoiUiState.Success(pois) }
            },
            onFailure = { exception ->
                _uiState.update { PoiUiState.Error(exception.localizedMessage ?: "Unknown error") }
            }
        )
    }
}
