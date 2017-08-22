package amldev.currency.data.db

import amldev.currency.ui.App
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.*

/*************************************************************************************************
 * Created by anartzmugika on 21/8/17.
 * Define database create and upgrade situations
 ************************************************************************************************/
class CurrencyDbHelper : ManagedSQLiteOpenHelper(App.instance,
        CurrencyDbHelper.DB_NAME, null, CurrencyDbHelper.DB_VERSION) {

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(MoneyInfoTable.NAME, true)
        db.dropTable(MoneyCurrenciesTable.NAME, true)
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(MoneyInfoTable.NAME, true,
                MoneyInfoTable.SYMBOL to TEXT + PRIMARY_KEY,
                MoneyInfoTable.MONEY to TEXT,
                MoneyInfoTable.FLAG to TEXT)
        Log.d("Create table", "Create ${MoneyInfoTable.NAME} succesfully!")

        db.createTable(MoneyCurrenciesTable.NAME, true,
                MoneyCurrenciesTable.ID to TEXT + PRIMARY_KEY,
                MoneyCurrenciesTable.ID_BASE to TEXT,
                MoneyCurrenciesTable.ID_CONVERSION_MONEY to TEXT,
                MoneyCurrenciesTable.VALUE_CONVERSION to TEXT,
                MoneyCurrenciesTable.UPDATED_DATE to TEXT)
        Log.d("Create table", "Create ${MoneyCurrenciesTable.NAME} succesfully!")
    }

    companion object {
        val DB_NAME = "currency.db"
        val DB_VERSION = 2
        val instance by lazy { CurrencyDbHelper() }
    }

}