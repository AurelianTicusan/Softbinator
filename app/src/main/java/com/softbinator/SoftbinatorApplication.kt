package com.softbinator

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SoftbinatorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: SoftbinatorApplication
    }

}