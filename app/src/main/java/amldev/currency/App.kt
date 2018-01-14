package amldev.currency

import amldev.currency.di.AppComponent
import amldev.currency.di.AppModule
import amldev.currency.di.DaggerAppComponent
import amldev.currency.extensions.DelegatesExt
import android.app.Application

/**
 * Created by anartzmugika on 21/8/17.
 */
class App: Application() {
    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        component.inject(this)
    }
}