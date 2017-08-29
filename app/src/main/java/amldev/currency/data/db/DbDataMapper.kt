package amldev.currency.data.db

import amldev.currency.R
import domain.model.Money

/***************************************************************************************************
 * Created by anartzmugika on 22/8/17.
 *
 **************************************************************************************************/
class DbDataMapper() {

    fun convertMoneyFromDomain(money: Money) = with(money) {
        MoneyInfo(symbol, name, flag)
    }

    fun getCurrencyMoneysListFromLocalDB(items: List<MoneyInfo>) : List<Money> {
        var moneys : MutableList<Money> = mutableListOf()
        items.map { moneys.add(Money(it.symbol, 0.0, it.money, it.flag, R.drawable.ic_united_nations)) }
        return moneys
    }
}