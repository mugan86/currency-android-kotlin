package amldev.currency.di

import amldev.currency.App
import amldev.currency.data.providers.CurrencyProvider
import amldev.currency.interfaces.Provider
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

    @Provides
    @Singleton
    fun provideMediaProvider(): Provider = CurrencyProvider()
}