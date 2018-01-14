package amldev.currency.ui.activities.money_conversions.di

import amldev.currency.interfaces.Provider
import amldev.currency.ui.activities.money_conversions.SelectMoneyConversionsActivity
import amldev.currency.ui.activities.money_conversions.SelectMoneyConversionsPresenter
import dagger.Module
import dagger.Provides

/********************************************************************************
 * Created by Anartz Mugika (mugan86@gmail.com) on 13/1/18.
 ********************************************************************************/
@Module
class SelectMoneyConversionsModule(val activity: SelectMoneyConversionsActivity) {
    @Provides
    fun provideSelectMoneyConversionPresenter(provider: Provider) = SelectMoneyConversionsPresenter(activity, provider)
}