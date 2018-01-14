package amldev.currency.extensions

/**
 * Created by anartzmugika on 12/1/18.
 */
import amldev.currency.domain.model.Currency
import amldev.currency.domain.model.Money

typealias MoneyItemUnit = (Money) -> Unit

typealias MoneysListUnit = (List<Money>) -> Unit

typealias MoneysList = List<Money>

typealias CurrencyUnit = (Currency) -> Unit

typealias StringUnit = (String) -> Unit
