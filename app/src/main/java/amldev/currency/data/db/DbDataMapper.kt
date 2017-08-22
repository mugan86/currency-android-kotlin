package amldev.currency.data.db

import domain.model.Money

/**
 * Created by anartzmugika on 22/8/17.
 */
class DbDataMapper() {

    fun convertMoneyFromDomain(moneys: Money) = with(moneys) {
        MoneyInfo(symbol, name, flag)
    }

}