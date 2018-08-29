package amldev.currency.data.providers

import amldev.currency.data.db.CurrencyDb
import amldev.currency.data.server.CurrencyRequest
import amldev.currency.domain.commands.RequestCurrencyCommand
import amldev.currency.domain.model.Currency
import amldev.currency.domain.model.Money
import amldev.currency.extensions.CurrencyUnit
import amldev.currency.extensions.MoneysList
import amldev.currency.extensions.MoneysListUnit
import amldev.currency.interfaces.Provider
import android.content.Context
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/********************************************************************************
 * Created by Anartz Mugika (mugan86@gmail.com) on 13/1/18.
 ********************************************************************************/
class CurrencyProvider : Provider {
    fun loadPreferenceData() {
        println("TO Implement")
    }

    /***********************************************************************************************
     * MainActivity Provider Data
     ***********************************************************************************************/
    private var data = emptyList<Money>()

    override fun loadCurrenciesList(context: Context, f: MoneysListUnit) {
        doAsync {
            if (data.isEmpty()) data = loadCurrenciesSync(context)
            uiThread { f(data) }
        }
    }

    private fun loadDataFromJSONFileAndStoreInDB(context: Context): MoneysList {
        val moneysList: MoneysList = CurrencyRequest().getMoneyList(context)
        moneysList.map { CurrencyDb().saveMoney(Money(it.symbol, 0.0, it.name, it.flag)) }
        return moneysList
    }

    //To Use in MainActivity.kt
    private fun loadCurrenciesSync(context: Context): MoneysList {
        if (CurrencyDb().getMoneyListItemsSize() == 0) return loadDataFromJSONFileAndStoreInDB(context) //In first time or first call to day
        return CurrencyDb().getMoneyListItems() //Take data from SQLITE
    }

    /***********************************************************************************************
     * SelectMoneyConversionsActivity Provider Data
     ***********************************************************************************************/
    private var selectCurrency: Currency = Currency()

    override fun loadMoneySelectData(selectCurrency_: Currency, context: Context, f: CurrencyUnit) {
        doAsync {
            selectCurrency = loadSelectMoneyCurrency(selectCurrency_.baseMoneySymbol, context)
            // Check if money value save and update. If not update, save after delete
            if (!CurrencyDb().checkIfBaseMoneyHaveUpdateData(selectCurrency_.baseMoneySymbol)) {
                CurrencyDb().saveBaseConversionMoneyValues(selectCurrency)
            }
            uiThread { f(selectCurrency) }
        }
    }

    private fun loadSelectMoneyCurrency(symbol: String, context: Context): Currency {
        if (CurrencyDb().checkIfBaseMoneyHaveUpdateData(symbol)) return CurrencyDb().getSelectMoneyAndCurrencies(symbol) // Take data from sqlite database
        // Take data from server or local json files
        return RequestCurrencyCommand(symbol, context).execute()
    }
}