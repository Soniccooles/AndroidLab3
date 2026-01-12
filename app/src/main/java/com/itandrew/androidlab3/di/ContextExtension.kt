package com.itandrew.androidlab3.di

import android.content.Context
import com.itandrew.androidlab3.MyApplication

val Context.appComponent: AppComponent
    get() = when(this) {
        is MyApplication -> this.appComponent
        else -> this.applicationContext.appComponent
    }