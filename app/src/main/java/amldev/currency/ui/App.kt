package amldev.currency.ui

import amldev.currency.extensions.DelegatesExt
import android.app.Application

/**
 * Created by anartzmugika on 21/8/17.
 */
class App: Application() {
    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}