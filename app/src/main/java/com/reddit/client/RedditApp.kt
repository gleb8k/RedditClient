package com.reddit.client

import com.reddit.client.di.AppComponent
import com.reddit.client.di.NetworkModule
import android.app.Application
import com.reddit.client.di.DaggerAppComponent

class RedditApp : Application() {

    private object Holder {
        lateinit var instance: RedditApp
    }

    companion object {
        fun getInstance(): RedditApp {
            return Holder.instance
        }
    }

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        Holder.instance = this
        component = DaggerAppComponent.builder()
                .networkModule(NetworkModule())
                .build()
    }
}