package com.avsoftware.catsnaps

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CatSnapsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())

            Timber.d("API Key: ${BuildConfig.CAT_API_KEY}")
        }
    }
}