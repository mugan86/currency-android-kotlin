package data

import amldev.currency.extensions.getFlagDrawable
import amldev.currency.extensions.getJSONResource
import amldev.currency.extensions.isNetworkConnected
import android.content.Context
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.google.gson.Gson
import domain.model.Money
import java.net.URL

/******************************************************************************************************************
 * Created by Anartz Mugika on 19/07/2017.
 * Take data from server (if neetwork connected) or from assets (not connected) with pass values in constructor
 ******************************************************************************************************************/
class CurrencyRequest(private val baseMoney: String = "EUR") {
    companion object {
        val symbols = "AUD,CAD,CHF,CNY,EUR,GBP,INR,JPY,MYR,RUB,SGD,USD"
        private val URL_LOCALHOST = "https://api.fixer.io/latest?symbols=$symbols&base="
    }

    fun execute(context:Context) : CurrencyResult {
        if (isNetworkConnected(context)) return Gson().fromJson(URL(URL_LOCALHOST + baseMoney).readText(), CurrencyResult::class.java)
        //Without Internet, takae assets/currencies/baseMoney.json file
        return Gson().fromJson(getJSONResource(context, "currencies/${baseMoney.toLowerCase()}"), CurrencyResult::class.java)
    }


    //Load start money info list to use to select our base money
    fun getMoneyList(context:Context) : ArrayList<Money> {
        var moneys: ArrayList<Money> = ArrayList()
        ((Parser().parse(StringBuilder(getJSONResource(context, "list-currencies")))
                as JsonObject)["currencies"] as JsonArray<*>).map {
            money ->
            val data = money as JsonObject

            moneys.add(Money(data["symbol"].toString(), 0.0, data["name"].toString(), data["flag"].toString(), getFlagDrawable(context, data["flag"].toString())))
        }
        return moneys
    }

}


