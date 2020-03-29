package com.bit.gamechangetest

import android.app.Application
import com.facebook.stetho.Stetho

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
        AppObjectController.init(this)

    }
}