package amldev.currency.data.db

/***************************************************************************************************
 * Created by anartzmugika on 22/8/17.
 ***************************************************************************************************/

class MoneyInfo(var map: MutableMap<String, Any?>) {
    // Properties must be equal tables columns names
    var _id: String by map
    var money: String by map
    var symbol: String by map
    var flag: String by map

    constructor(_id: String, money: String, symbol: String, flag: String) : this(HashMap()) {
        this._id = _id
        this.money = money
        this.symbol = symbol
        this.flag = flag
    }
}

class MoneyCurrencies (var map: MutableMap<String, Any?>) {
    var _id_base: String by map
    var _id_conversion_money: String by map
    var updated_date: String by map
    var value_conversion: String by map

    constructor(_id_base: String, _id_conversion_money: String, updated_date: String, value_conversion: String): this(HashMap()) {
        this._id_base = _id_base
        this._id_conversion_money = _id_conversion_money
        this.updated_date = updated_date
        this.value_conversion = value_conversion
    }
}
