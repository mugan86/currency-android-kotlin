package amldev.currency.interfaces

import amldev.currency.domain.model.Currency
import amldev.currency.extensions.CurrencyUnit
import amldev.currency.extensions.MoneysListUnit

/**
 * Created by anartzmugika on 13/1/18.
 */
interface Provider {
    fun loadCurrenciesList(f: MoneysListUnit)
    fun loadMoneySelectData(selectCurrency: Currency, f: CurrencyUnit)
}