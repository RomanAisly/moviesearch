package com.example.moviesearch

import android.app.Application
import di.AppComponent
import di.DaggerAppComponent


class App : Application() {

    lateinit var dagger: AppComponent
    override fun onCreate() {
        super.onCreate()

        instance = this
        dagger = DaggerAppComponent.create()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}