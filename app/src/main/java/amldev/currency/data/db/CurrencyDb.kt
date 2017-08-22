package amldev.currency.data.db

import amldev.currency.extensions.clear
import domain.model.Currency

/***************************************************************************************************
 * Created by anartzmugika on 22/8/17.
 ***************************************************************************************************/

class CurrencyDb(
        val dbHelper: CurrencyDbHelper = CurrencyDbHelper.instance, val dataMapper: DbDataMapper = DbDataMapper()) {

    fun saveMoney(currency: Currency) = dbHelper.use {
        clear(MoneyInfoTable.NAME)

        /*with(dataMapper.convertFromDomain(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }*/
    }
}

