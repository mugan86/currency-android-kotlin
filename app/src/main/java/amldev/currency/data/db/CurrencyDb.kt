package amldev.currency.data.db

import amldev.currency.extensions.*
import amldev.currency.domain.model.Currency
import amldev.currency.domain.model.Money
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/***************************************************************************************************
 * Created by anartzmugika on 22/8/17.
 * Define Database operations class.
 * Options:
 * Save all money currency items after load from assets JSON File
 * Extract Currency Money List (if exist items after save when load data from JSON in start)
 * Get DB Money Currency List size
 ***************************************************************************************************/

class CurrencyDb (val dbHelper: CurrencyDbHelper = CurrencyDbHelper.instance,
                  val dataMapper: DbDataMapper = DbDataMapper()) {

    fun saveMoney(money: Money) = dbHelper.use {
        with(dataMapper.convertMoneyFromDomain(money)) { insert(MoneyInfoTable.NAME, *map.toVarargArray()) }
    }

    fun getMoneyListItems(): List<Money> = dbHelper.use({
        return@use dataMapper.getCurrencyMoneysListFromLocalDB(items = select(MoneyInfoTable.NAME).parseList { MoneyInfo(HashMap(it)) })
    })

    fun getMoneyListItemsSize () : Int = getMoneyListItems().size

    fun saveBaseConversionMoneyValues(currency: Currency) = dbHelper.use {
        // println("Delete because exist ${currency.baseMoneySymbol} and no update value (OR NO EXIST VALUE)")
        deleteSelect(MoneyCurrenciesTable.NAME, MoneyCurrenciesTable.ID_BASE, currency.baseMoneySymbol)
        //Currency -- Select money / Money: Conversion money values
        currency.moneyConversion.map { money -> //Money with value of currency
            with(dataMapper.convertCurrencyFromDomain(money, currency.baseMoneySymbol)) {
                insert(MoneyCurrenciesTable.NAME, *map.toVarargArray())
            }
        }
    }

    fun checkIfBaseMoneyHaveUpdateData(baseMoneySymbol:String ) = dbHelper.use {
        return@use checkIfValuesUpdate(baseMoneySymbol)
    }

    fun getSelectMoneyAndCurrencies(baseMoneySymbol: String): Currency = dbHelper.use {
        println(checkIfValuesUpdate(baseMoneySymbol))
        return@use getMoneyWithCurrencies(baseMoneySymbol)
    }


}

