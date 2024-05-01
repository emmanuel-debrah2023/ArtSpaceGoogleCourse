package com.example.artspacegooglecourse

import android.app.Application
import com.example.artspacegooglecourse.data.AppContainer
import com.example.artspacegooglecourse.data.DefaultAppContainer

class AppContainerInstance : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}