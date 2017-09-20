package amldev.currency.extensions

/**
 * Created by anartzmugika on 22/8/17.
 */

import android.database.sqlite.SQLiteDatabase
import domain.model.Currency
import domain.model.Money
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder
import android.database.DatabaseUtils



fun <T : Any> SelectQueryBuilder.parseList(parser: (Map<String, Any?>) -> T): List<T> =
               parseList(object : MapRowParser<T> {
                       override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
                   })

fun <T : Any> SelectQueryBuilder.parseOpt(parser: (Map<String, Any?>) -> T): T? =
               parseOpt(object : MapRowParser<T> {
                   override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
                    })

fun SQLiteDatabase.clear(tableName: String) {
    execSQL("delete from $tableName")
}

fun SQLiteDatabase.deleteSelect(tableName: String, whereColumn: String, value: String) {
    execSQL("delete from $tableName where $whereColumn = '${value}'")
}

fun SQLiteDatabase.checkIfValuesUpdate(baseMoney: String): Boolean = DatabaseUtils.longForQuery(this, "SELECT COUNT(_id_base)" +
        "FROM MoneyCurrenciesTable WHERE _id_base = ? AND updated_date = ?",  arrayOf(baseMoney, DateTime.currentData)).toInt() > 0

fun SQLiteDatabase.getMoneyWithCurrencies(baseMoney: String): Currency {
    val cursor = rawQuery("SELECT MCT._id_conversion_money, value_conversion, flag, updated_date " +
            "FROM MoneyCurrenciesTable MCT, MoneyInfoTable MT WHERE MCT._id_base = MT.symbol AND MCT._id_base = ?", arrayOf(baseMoney))

    val moneyList = mutableListOf<Money>()
    var currencyName = ""
    var update_date  = ""
    try {
        if (cursor.moveToFirst()) {
            do {
                if (currencyName == "") {
                    currencyName = cursor.getString(2)
                    update_date = cursor.getString(3)
                }
                moneyList.add(Money(cursor.getString(0), cursor.getDouble(1), "", cursor.getString(2)))
            } while (cursor.moveToNext())
        }
    } finally {
        cursor.close()
    }

    return Currency(baseMoney, currencyName, moneyList, update_date)
}