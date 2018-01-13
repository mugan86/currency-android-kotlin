package amldev.currency.data.providers

import amldev.currency.App
import amldev.currency.data.db.CurrencyDb
import amldev.currency.data.server.CurrencyRequest
import amldev.currency.domain.model.Money
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

    private var data = emptyList<Money>()
    override fun dataAsync(f: MoneysListUnit) {
        doAsync {
            if (data.isEmpty()) data = dataSync()
            uiThread { f(data) }
        }
    }

    private fun loadDataFromJSONFileAndStoreInDB(context: Context): MoneysList {
        val moneysList: MoneysList = CurrencyRequest().getMoneyList(context)
        moneysList.map { CurrencyDb().saveMoney(Money(it.symbol, 0.0, it.name, it.flag)) }
        return moneysList
    }

    private fun dataSync(): MoneysList {
        Thread.sleep(500) //Medio segundo para ejecutar la asincronia
        println("Load data....")
        val moneysList: MoneysList?
        if (CurrencyDb().getMoneyListItemsSize() == 0) moneysList = loadDataFromJSONFileAndStoreInDB(App().context) //In first time or first call to day
        else moneysList = CurrencyDb().getMoneyListItems() //Take data from SQLITE
        return moneysList
    }

    /*doAsync {

        progress.show()
        //Load list currencies and log symbol and name
        if (CurrencyDb().getMoneyListItemsSize () == 0) {
            println("Load JSON File")
            loadDataFromJSONFileAndStoreInDB()
        }
        else{
            println("Get database info")
            moneys = CurrencyDb().getMoneyListItems()
        }

        uiThread {

            val adapter = MoneyAdapter(moneys , {
                openConversionsWithSelectMoney(it.symbol, it.name, it.flag)
            })
            moneysList.adapter = adapter
            progress.dismiss()
            sendOpinionInGooglePlay.visibility = View.VISIBLE
        }
    }*/
}