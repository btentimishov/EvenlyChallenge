package com.baktyiar.evenlychallenge.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baktyiar.evenlychallenge.R
import com.baktyiar.evenlychallenge.domain.model.Coordinates
import com.baktyiar.evenlychallenge.presentation.ui.MainActivity
import com.baktyiar.evenlychallenge.presentation.ui.components.ClickableLink
import com.baktyiar.evenlychallenge.presentation.ui.components.CustomTopAppBar
import com.baktyiar.evenlychallenge.presentation.ui.components.MapView
import com.baktyiar.evenlychallenge.presentation.ui.components.SocialLinkItem
import com.baktyiar.evenlychallenge.utils.shareLink


@Composable
fun EvenlyDetailScreen(onBack: () -> Unit) {
    val activity = LocalContext.current as? MainActivity
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Evenly GmbH",
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Evenly Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Text(
                "Experts in digital product development and software engineering, building apps and skills.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("Location: Berlin, Germany")
            Text("Founded: 2010")
            Text("Company size: 11-50 employees")
            Text("Industry: Technology, Information and Internet")

            Spacer(modifier = Modifier.height(8.dp))
            Text("Specialties:", style = MaterialTheme.typography.titleMedium)
            Text("• Mobile Apps\n• iOS Apps\n• Android Apps\n• tvOS Apps")

            Spacer(modifier = Modifier.height(8.dp))
            Text("Website:", style = MaterialTheme.typography.titleMedium)
            ClickableLink(text = "https://linktr.ee/evenlyio", url = "https://linktr.ee/evenlyio")

            Spacer(modifier = Modifier.height(8.dp))
            Text("Contact & Imprint:", style = MaterialTheme.typography.titleMedium)
            ClickableLink(text = "Contact", url = "https://evenly.io/contact/")
            ClickableLink(text = "Imprint", url = "https://evenly.io/imprint")

            Spacer(modifier = Modifier.height(8.dp))
            Text("Social Media:", style = MaterialTheme.typography.titleMedium)
            // Social links with icons (replace URLs or icons as needed)
            SocialLinkItem(
                iconRes = R.drawable.linkedin,
                text = "LinkedIn",
                url = "https://www.linkedin.com/company/evenly-gmbh/"
            )
            SocialLinkItem(
                iconRes = R.drawable.twitter,
                text = "Twitter",
                url = "https://x.com/evenly_io"
            )
            SocialLinkItem(
                iconRes = R.drawable.instagram,
                text = "Instagram",
                url = "https://www.instagram.com/evenlyio/"
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text("Overview:", style = MaterialTheme.typography.titleMedium)
            Text("We are experts in building apps and frameworks for iPhone, iPad, and Android")

            Spacer(modifier = Modifier.height(8.dp))
            MapView(
                "Evenly HQ",
                location = Coordinates(52.500342, 13.425170)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                onClick = {
                    shareLink(
                        link = "https://foursquare.com/v/evenly-hq/560d09a0498e04e7a4318441",
                        activity = activity
                    )
                }
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Share Link")
            }
        }
    }
}