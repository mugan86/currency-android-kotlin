package amldev.currency.data.db

import amldev.currency.R
import amldev.currency.extensions.parseList
import amldev.currency.extensions.toVarargArray
import domain.model.Money
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/***************************************************************************************************
 * Created by anartzmugika on 22/8/17.
 * Define Database operations class.
 ***************************************************************************************************/

class CurrencyDb(
        val dbHelper: CurrencyDbHelper = CurrencyDbHelper.instance, val dataMapper: DbDataMapper = DbDataMapper()) {

    fun saveMoney(money: Money) = dbHelper.use {
        with(dataMapper.convertMoneyFromDomain(money)) {
            insert(MoneyInfoTable.NAME, *map.toVarargArray())
        }
    }

    fun getMoneyListItems(): List<Money> = dbHelper.use {
        // select(MoneyInfoTable.NAME, MoneyInfoTable.SYMBOL, MoneyInfoTable.MONEY, MoneyInfoTable.FLAG).parseOpt {  }
        val items = select(MoneyInfoTable.NAME).parseList { MoneyInfo(HashMap(it)) }
        var moneys : MutableList<Money> = mutableListOf()
        items.map {
            moneys.add(Money(it.symbol, 0.0, it.money, it.flag, R.drawable.ic_united_nations))
        }
        return@use moneys
    }

    fun getMoneyListItemsSize () : Int = getMoneyListItems().size
}

