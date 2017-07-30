package data

import com.google.gson.Gson
import java.net.URL

/******************************************************************************************************************
 * Created by Anartz Mugika on 19/07/2017.
 * Take data from server with pass values in constructor
 ******************************************************************************************************************/
class CurrencyRequest(private val baseMoney: String) {
    companion object {
        val symbols = "AUD,CAD,CHF,CNY,EUR,GBP,INR,JPY,MYR,RUB,SGD,USD"
        private val URL_LOCALHOST = "https://api.fixer.io/latest?symbols=$symbols&base="
    }

    fun execute() : CurrencyResult = Gson().fromJson(URL(URL_LOCALHOST + baseMoney).readText(), CurrencyResult::class.java)
}


