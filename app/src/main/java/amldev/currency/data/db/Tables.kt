package amldev.currency.data.db

/***************************************************************************************************
 * Created by Anartz Mugika on 21/8/17.
 ***************************************************************************************************/

object MoneyInfoTable {
    val NAME = "MoneyInfoTable" //Table Name
    val MONEY = "money"
    val ID = "_id"
    val SYMBOL = "symbol"
    val FLAG = "flag"
}

object MoneyCurrenciesTable {
    val NAME = "MoneyCurrenciesTable" //Table Name
    val UPDATED_DATE = "updated_date"
    val ID_BASE = "_id_base"
    val ID_CONVERSION_MONEY = "_id_conversion_money"
    val VALUE_CONVERSION = "value_conversion"
}