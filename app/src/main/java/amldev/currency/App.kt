package amldev.currency

import amldev.currency.di.AppComponent
import amldev.currency.di.AppModule
import amldev.currency.di.DaggerAppComponent
import amldev.currency.extensions.DelegatesExt
import android.app.Application
import android.content.Context

/**
 * Created by anartzmugika on 21/8/17.
 */
class App: Application() {
    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    lateinit var context: Context
    /*override fun onCreate() {
        super.onCreate()
        instance = this
    }*/

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        instance = this
        context = applicationContext
    }
}