package com.baktyiar.evenlychallenge.presentation.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.baktyiar.evenlychallenge.domain.model.PoiCategory
import com.baktyiar.evenlychallenge.domain.model.Coordinates
import com.baktyiar.evenlychallenge.domain.model.Poi
import com.baktyiar.evenlychallenge.presentation.ui.screens.PoiDetailScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PoiDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun poiDetailScreen_displaysPoiInformation() {
        val testPoi = Poi(
            id = "123",
            name = "Ethereum Dev",
            location = Coordinates(52.5200, 13.4050),
            categories = listOf(PoiCategory("Technology", "https://example.com/icon.png")),
            distance = 500,
            formattedAddress = "Berlin, Germany",
            link = "https://example.com/"
        )

        composeTestRule.setContent {
            PoiDetailScreen(
                poi = testPoi,
                onBack = {}
            )
        }

        composeTestRule.onNodeWithText(testPoi.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(testPoi.formattedAddress!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(testPoi.categories.first().name).assertIsDisplayed()
        composeTestRule.onNodeWithText(testPoi.link).assertIsDisplayed()
        composeTestRule.onNodeWithText("Share Link").assertIsDisplayed()
    }
}
