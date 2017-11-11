package amldev.currency

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by anartzmugika on 10/11/17.
 */
@Module
class AppModule(val app: App) {
    @Provides
    @Singleton
    fun provideApp() = app
}