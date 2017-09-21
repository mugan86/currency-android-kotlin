package amldev.currency.domain.mappers

import amldev.currency.data.server.CurrencyRequest
import amldev.currency.data.server.CurrencyResult
import amldev.currency.data.server.Rates
import amldev.currency.domain.model.Currency
import amldev.currency.domain.model.Money
import java.text.DateFormat
import java.util.*

/***********************************************************************************************************************
 * Created by Anartz Mugika on 22/07/2017.
 * ---------------------------------------------------------------------------------------------------------------------
 * Call from "RequestCurrencyCommand". In this class map correctly to our preferences to show in final result
 ***********************************************************************************************************************/
class CurrencyDataMapper {

    fun convertFromDataModel(currency: CurrencyResult) = with(currency) {
        // Pass money name
        Currency(base, ""
                , getMoneyConversions(rates), convertDate(Calendar.getInstance().timeInMillis))
    }

    private fun convertDate(date: Long) = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault()).format(date)

    private fun getMoneyConversions(rates: Rates) = with(rates) {
            val symbols = CurrencyRequest.symbols.split(",".toRegex())
            listOf<Money>(Money(symbols[0], AUD),
                    Money(symbols[1], CAD),
                    Money(symbols[2], CHF),
                    Money(symbols[3], CNY),
                    Money(symbols[4], EUR),
                    Money(symbols[5], GBP),
                    Money(symbols[6], INR),
                    Money(symbols[7], JPY),
                    Money(symbols[8], MYR),
                    Money(symbols[9], RUB),
                    Money(symbols[10], SGD),
                    Money(symbols[11], USD)
            )
    }
}
