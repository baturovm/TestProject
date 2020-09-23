package dev.test.project

import android.app.Application
import io.realm.Realm

class App: Application() {

    companion object {
        private var appInstance: App? = null
        @JvmStatic fun getContext(): App {
            return appInstance as App
        }
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        appInstance = this
    }
}