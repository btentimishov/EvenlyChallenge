package com.baktyiar.evenlychallenge

import android.app.Application
import com.google.android.gms.maps.MapsInitializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EvenlyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(this)
    }
}
