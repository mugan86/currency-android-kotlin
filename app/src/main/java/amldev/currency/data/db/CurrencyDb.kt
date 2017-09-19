package amldev.currency.data.db

import amldev.currency.extensions.*
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

    // fun checkIf



    // selectCurrencyMoneyWithUpdateData(MoneyCurrenciesTable.NAME, )

    fun saveBaseConversionMoneyValues(currency: Currency) = dbHelper.use {
        //If contain values update in this current date, ignore to delete and go to else
        if (checkIfBaseMoneyUpdateCurrencyValues(currency.baseMoneySymbol)) {
            println("Delete because exist ${currency.baseMoneySymbol} and no update value (OR NO EXIST VALUE)")
            deleteSelect(MoneyCurrenciesTable.NAME, MoneyCurrenciesTable.ID_BASE, currency.baseMoneySymbol)
            //Currency -- Select money / Money: Conversion money values
            currency.moneyConversion.map { money -> //Money with value of currency
                with(dataMapper.convertCurrencyFromDomain(money, currency.baseMoneySymbol)) {
                    insert(MoneyCurrenciesTable.NAME, *map.toVarargArray())
                }
            }
        } else {
            println("Value update to current data: ${DateTime.currentData}")
        }

    }

    fun checkIfBaseMoneyUpdateCurrencyValues(baseMoneySymbol:String ) = dbHelper.use {
        val consult = select(MoneyCurrenciesTable.NAME)
                .whereArgs("(${MoneyCurrenciesTable.ID_BASE} = {${MoneyCurrenciesTable.ID_BASE}}) and (${MoneyCurrenciesTable.UPDATED_DATE} = {${MoneyCurrenciesTable.UPDATED_DATE}})",
                        MoneyCurrenciesTable.ID_BASE to baseMoneySymbol,
                        MoneyCurrenciesTable.UPDATED_DATE to DateTime.currentData).parseList{ MoneyCurrencies(HashMap(it))}
        println("Check if exist values: " + consult.size)
        return@use consult.size == 0
    }

    // TODO USe to test!!!
    fun getSelectMoneyAndCurrencies(baseMoneySymbol: String): Currency = dbHelper.use {


        val list = select(MoneyCurrenciesTable.NAME).whereArgs("(${MoneyCurrenciesTable.ID_BASE} = {${MoneyCurrenciesTable.ID_BASE}}) and (${MoneyCurrenciesTable.UPDATED_DATE} = {${MoneyCurrenciesTable.UPDATED_DATE}})",
                MoneyCurrenciesTable.ID_BASE to baseMoneySymbol,
                MoneyCurrenciesTable.UPDATED_DATE to DateTime.currentData).parseList { MoneyCurrencies(HashMap(it)) }
        val moneyList: MutableList<Money> = mutableListOf()

        list.map {
            moneyCurrencies ->  moneyList.add(Money(baseMoneySymbol, moneyCurrencies.value_conversion.toDouble(), "", "", 0, ""))
            println(getMoneyListItems().size.toString() + " " + moneyCurrencies._id_conversion_money)
            val findValue = getMoneyListItems().filter({ it.symbol === moneyCurrencies._id_conversion_money})
            println(findValue)

        }
        return@use Currency(baseMoneySymbol, "", moneyList, DateTime.currentData)
    }


}

