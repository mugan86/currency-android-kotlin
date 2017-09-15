package amldev.currency.data.db

import amldev.currency.extensions.parseList
import amldev.currency.extensions.toVarargArray
import domain.model.Currency
import domain.model.Money
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

    fun getMoneyListItems(): List<Money> = dbHelper.use {
        return@use dataMapper.getCurrencyMoneysListFromLocalDB(items = select(MoneyInfoTable.NAME).parseList { MoneyInfo(HashMap(it)) })
    }

    fun getMoneyListItemsSize () : Int = getMoneyListItems().size

    fun saveBaseConversionMoneyValues(currency: Currency) = dbHelper.use {
        currency.moneyConversion.map { money ->
            with(dataMapper.convertCurrencyFromDomain(money, currency.baseMoneySymbol)) {
                insert(MoneyCurrenciesTable.NAME, *map.toVarargArray())
            }
        }

    }
}

