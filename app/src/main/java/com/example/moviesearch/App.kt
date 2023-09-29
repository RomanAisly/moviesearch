package com.example.moviesearch

import android.app.Application
import com.example.remote_module.DaggerRemoteComponent
import di.AppComponent
import di.DaggerAppComponent
import di.modules.DatabaseModule
import di.modules.DomainModule


class App: Application() {

    lateinit var dagger: AppComponent
    override fun onCreate() {
        super.onCreate()
        instance = this

        val remoteProvider = DaggerRemoteComponent.create()
        dagger = DaggerAppComponent.builder()
            .remoteProvider(remoteProvider)
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}