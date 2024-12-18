package com.baktyiar.evenlychallenge.presentation.viewmodel

import com.baktyiar.evenlychallenge.domain.usecase.GetNearbyPoisUseCase
import com.baktyiar.evenlychallenge.presentation.viewmodel.FakeSuccessPoiRepository.Companion.fakePois
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PoiViewModelTest {

    @Test
    fun initialState_isLoading() = runBlocking {
        val viewModel = PoiViewModel(GetNearbyPoisUseCase(FakeSuccessPoiRepository()))
        assertEquals(PoiUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun loadPois_updatesStateToSuccess() = runBlocking {
        val viewModel = PoiViewModel(GetNearbyPoisUseCase(FakeSuccessPoiRepository()))

        assertEquals(PoiUiState.Loading, viewModel.uiState.value)

        val job = viewModel.loadPois()
        job.join()
        assertEquals(PoiUiState.Success(fakePois), viewModel.uiState.value)
    }

    @Test
    fun loadPois_withError_updatesStateToError() = runBlocking {

        val viewModel = PoiViewModel(GetNearbyPoisUseCase(FakeErrorPoiRepository()))
        assertEquals(PoiUiState.Loading, viewModel.uiState.value)

        val job = viewModel.loadPois()
        job.join()

        val currentState = viewModel.uiState.value
        if (currentState is PoiUiState.Error) {
            assertEquals("Test error", currentState.message)
        } else {
            throw AssertionError("Expected PoiUiState.Error but got $currentState")
        }
    }
}

