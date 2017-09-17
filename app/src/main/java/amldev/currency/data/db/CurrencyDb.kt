package amldev.currency.data.db

import amldev.currency.extensions.DateTime
import amldev.currency.extensions.deleteSelect
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

    fun getMoneyListItems(): List<Money> = dbHelper.use({
        return@use dataMapper.getCurrencyMoneysListFromLocalDB(items = select(MoneyInfoTable.NAME).parseList { MoneyInfo(HashMap(it)) })
    })

    fun getMoneyListItemsSize () : Int = getMoneyListItems().size

    fun saveBaseConversionMoneyValues(currency: Currency) = dbHelper.use {
        //Currency -- Select money / Money: Conversion money values
        currency.moneyConversion.map { money -> //Money with value of currency
            with(dataMapper.convertCurrencyFromDomain(money, currency.baseMoneySymbol)) {

                //TODO Write conditions to correct manage!!!
                if (checkIfExistConversionMoney(currency.baseMoneySymbol + money.symbol)) {
                    println("Delete because exist ${currency.baseMoneySymbol + money.symbol}")
                    deleteSelect(MoneyCurrenciesTable.NAME, MoneyCurrenciesTable.ID, currency.baseMoneySymbol + money.symbol)
                }
                else {

                }
                insert(MoneyCurrenciesTable.NAME, *map.toVarargArray())

            }
        }

    }

    fun checkIfExistConversionMoney(id: String) : Boolean = dbHelper.use {
        println("SELECT * FROM ${MoneyCurrenciesTable.NAME} WHERE ${MoneyCurrenciesTable.ID} = '$id' AND ${MoneyCurrenciesTable.UPDATED_DATE} = '${DateTime.currentData}'")
        //TODO Check if UPDATED DATE too
        if ((select(MoneyCurrenciesTable.NAME)
                .whereSimple("${MoneyCurrenciesTable.ID} = ?", id)
                .parseList { MoneyCurrencies(HashMap(it))}).size > 0) {
            return@use true
        }
        return@use false
    }
}

