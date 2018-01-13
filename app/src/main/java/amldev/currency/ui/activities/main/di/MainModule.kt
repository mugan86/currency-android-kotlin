package amldev.currency.ui.activities.main.di

/**
 * Created by anartzmugika on 7/1/18.
 */

import amldev.currency.interfaces.Provider
import amldev.currency.ui.activities.main.MainActivity
import amldev.currency.ui.activities.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainModule(val activity: MainActivity) {
    // @Provides fun provideMainPresenter() = MainPresenter(activity)
    @Provides
    fun provideMainPresenter(provider: Provider) = MainPresenter(activity, provider)
}