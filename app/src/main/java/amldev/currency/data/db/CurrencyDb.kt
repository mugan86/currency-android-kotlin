package amldev.currency.data.db

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

    fun getMoneyListItemsSize() = dbHelper.use {
        select(MoneyInfoTable.NAME, MoneyInfoTable.NAME, MoneyInfoTable.SYMBOL, MoneyInfoTable.FLAG)
    }
}

