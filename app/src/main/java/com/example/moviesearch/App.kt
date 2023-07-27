package com.example.moviesearch

import android.app.Application
import di.AppComponent
import di.DaggerAppComponent
import di.modules.DatabaseModule
import di.modules.DomainModule
import di.modules.RemoteModule


class App : Application() {

    lateinit var dagger: AppComponent
    override fun onCreate() {
        super.onCreate()

        instance = this
        dagger = DaggerAppComponent.builder().remoteModule(RemoteModule()).databaseModule(
            DatabaseModule()
        ).domainModule(DomainModule(this)).build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}