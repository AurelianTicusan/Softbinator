package com.softbinator

import android.app.Application

class SoftbinatorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: SoftbinatorApplication
    }

}