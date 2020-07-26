package com.imazurenko.td

import android.app.Application

class TdApplication : Application() {

    lateinit var appComponent: AppComponent

    private fun initDagger(app: TdApplication): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

}