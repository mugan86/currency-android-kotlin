package amldev.currency.data.db

/**
 * Created by anartzmugika on 22/8/17.
 */


class CurrencyDb(
        val dbHelper: CurrencyDbHelper = CurrencyDbHelper.instance, val dataMapper: DbDataMapper = DbDataMapper()) {

    fun test() {
        dbHelper.use {  }
    }
}
