package dev.test.project

import android.app.Application

class App: Application() {

    companion object {
        private var appInstance: App? = null
        @JvmStatic fun getInstance(): App {
            return appInstance as App
        }
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}