package amldev.currency.data.db

import domain.model.Money

/**
 * Created by anartzmugika on 22/8/17.
 */
class DbDataMapper() {

    fun convertMoneyFromDomain(money: Money) = with(money) {
        MoneyInfo(symbol, name, flag)
    }
}