package com.baktyiar.evenlychallenge.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun openLink(
    link: String,
    activity: Activity?
) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    activity?.startActivity(intent)
}

fun shareLink(
    link: String,
    activity: Activity?
) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, link)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    activity?.startActivity(shareIntent)
}


suspend fun loadBitmapFromUrl(context: Context, url: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()
        val result = (loader.execute(request) as? SuccessResult)?.drawable
        (result as? BitmapDrawable)?.bitmap
    }
}