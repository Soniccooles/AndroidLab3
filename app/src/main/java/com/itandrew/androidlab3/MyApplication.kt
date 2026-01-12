package com.itandrew.androidlab3

import android.app.Application
import com.itandrew.androidlab3.di.AppComponent
import com.itandrew.androidlab3.di.DaggerAppComponent

class MyApplication : Application() {

    lateinit var appComponent: AppComponent
            private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}