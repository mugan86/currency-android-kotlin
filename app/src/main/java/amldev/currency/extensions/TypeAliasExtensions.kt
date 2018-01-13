package amldev.currency.extensions

/**
 * Created by anartzmugika on 12/1/18.
 */
import amldev.currency.domain.model.Money

typealias MoneyItem = (Money) -> Unit

typealias MoneysListUnit = (List<Money>) -> Unit

typealias MoneysList = List<Money>

typealias MoneyItems = List<MoneyItem>