package domain.mappers

import data.CurrencyRequest
import data.CurrencyResult
import data.Rates
import domain.model.Currency
import domain.model.Money
import java.text.DateFormat
import java.util.*

/***********************************************************************************************************************
 * Created by Anartz Mugika on 22/07/2017.
 * ---------------------------------------------------------------------------------------------------------------------
 * Call from "RequestCurrencyCommand". In this class map correctly to our preferences to show in final result
 ***********************************************************************************************************************/
class CurrencyDataMapper {
    fun convertFromDataModel(currency: CurrencyResult) : Currency{
        // Pass money name
        return Currency(currency.base, ""
                , getMoneyConversions(currency.rates), convertDate(Calendar.getInstance().timeInMillis))
    }

    private fun convertDate(date: Long) = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault()).format(date)

    private fun getMoneyConversions(rates: Rates): List<Money> {
        val symbols = CurrencyRequest.symbols.split(",".toRegex())
        return listOf<Money>(Money(symbols[0], rates.AUD),
                                Money(symbols[1], rates.CAD),
                                Money(symbols[2], rates.CHF),
                                Money(symbols[3], rates.CNY),
                                Money(symbols[4], rates.EUR),
                                Money(symbols[5], rates.GBP),
                                Money(symbols[6], rates.INR),
                                Money(symbols[7], rates.JPY),
                                Money(symbols[8], rates.MYR),
                                Money(symbols[9], rates.RUB),
                                Money(symbols[10], rates.SGD),
                                Money(symbols[11], rates.USD)
                )
    }
}