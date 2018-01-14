package amldev.currency.data.providers

import amldev.currency.App
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


/**
 * Created by anartzmugika on 13/1/18.
 */
class CurrencyProvider : Provider {

    /***********************************************************************************************
     * MainActivity Provider Data
     ***********************************************************************************************/
    private var data = emptyList<Money>()

    override fun loadCurrenciesList(f: MoneysListUnit) {
        doAsync {
            if (data.isEmpty()) data = loadCurrenciesSync()
            uiThread { f(data) }
        }
    }

    private fun loadDataFromJSONFileAndStoreInDB(context: Context): MoneysList {
        val moneysList: MoneysList = CurrencyRequest().getMoneyList(context)
        moneysList.map { CurrencyDb().saveMoney(Money(it.symbol, 0.0, it.name, it.flag)) }
        return moneysList
    }

    //To Use in MainActivity.kt
    private fun loadCurrenciesSync(): MoneysList {
        Thread.sleep(500) //Medio segundo para ejecutar la asincronia
        println("Load data....")
        val moneysList: MoneysList?
        if (CurrencyDb().getMoneyListItemsSize() == 0) moneysList = loadDataFromJSONFileAndStoreInDB(App().context!!) //In first time or first call to day
        else moneysList = CurrencyDb().getMoneyListItems() //Take data from SQLITE
        return moneysList
    }

    /***********************************************************************************************
     * SelectMoneyConversionsActivity Provider Data
     ***********************************************************************************************/
    private var selectCurrency: Currency = Currency()

    override fun loadMoneySelectData(selectCurrency_: Currency, context: Context, f: CurrencyUnit) {
        doAsync {
            selectCurrency = loadSelectMoneyCurrency(selectCurrency_.baseMoneySymbol, context)
            uiThread { f(selectCurrency) }
        }
    }

    private fun loadSelectMoneyCurrency(symbol: String, context: Context): Currency {
        Thread.sleep(500) //Medio segundo para ejecutar la asincronia
        println("Load data....")
        if (CurrencyDb().checkIfBaseMoneyHaveUpdateData(symbol)) { // Take data from sqlite database
            return CurrencyDb().getSelectMoneyAndCurrencies(symbol)
        }
        // Take data from server or local json files
        return RequestCurrencyCommand(symbol, context).execute()
    }

}