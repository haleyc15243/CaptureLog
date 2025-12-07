package com.rhc.capturelog

import android.app.Application
import com.rhc.capturelog.di.initKoin

class CaptureLogApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
