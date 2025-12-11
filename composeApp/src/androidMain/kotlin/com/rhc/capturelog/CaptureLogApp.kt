package com.rhc.capturelog

import android.app.Application
import com.rhc.capturelog.di.initKoin
import org.koin.android.ext.koin.androidContext

class CaptureLogApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CaptureLogApp)
        }
    }
}
