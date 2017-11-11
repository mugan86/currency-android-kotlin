package amldev.currency

import dagger.Component
import javax.inject.Singleton

/**
 * Created by anartzmugika on 10/11/17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: App)
}