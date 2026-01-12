package com.itandrew.androidlab3

import android.app.Application
import com.itandrew.androidlab3.di.DaggerAppComponent

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val component: DaggerAppComponent
    }
}