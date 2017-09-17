package amldev.currency.extensions

/**
 * Created by anartzmugika on 22/8/17.
 */

import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder

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