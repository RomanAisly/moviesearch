package com.example.moviesearch

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.remote_module.DaggerRemoteComponent
import di.AppComponent
import di.DaggerAppComponent
import di.modules.DatabaseModule
import di.modules.DomainModule
import view.notifications.NotificationConstants.CHANNEL_ID


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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val name = "WatchLaterChannel"
            val descriptionText = "FilmsSearch notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notifChannel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notifManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notifManager.createNotificationChannel(notifChannel)
        }
    }

    companion object {
        lateinit var instance: App
            private set
    }
}