package data

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.google.gson.Gson
import com.google.gson.JsonParser
import domain.model.Money
import java.io.InputStreamReader
import java.net.URL

/******************************************************************************************************************
 * Created by Anartz Mugika on 19/07/2017.
 * Take data from server with pass values in constructor
 ******************************************************************************************************************/
class CurrencyRequest(private val baseMoney: String = "EUR") {
    companion object {
        val symbols = "AUD,CAD,CHF,CNY,EUR,GBP,INR,JPY,MYR,RUB,SGD,USD"
        private val URL_LOCALHOST = "https://api.fixer.io/latest?symbols=$symbols&base="
    }

    fun execute() : CurrencyResult = Gson().fromJson(URL(URL_LOCALHOST + baseMoney).readText(), CurrencyResult::class.java)

    fun getMoneyList(context:Context) : ArrayList<Money> {
        var moneys: ArrayList<Money> = ArrayList()
        ((Parser().parse(StringBuilder(getJSONResource(context)))
                as JsonObject)["currencies"] as JsonArray<*>).map {
            money ->
            val data = money as JsonObject
            moneys.add(Money(data["symbol"].toString(), 0.0, data["name"].toString(), data["flag"].toString()))
        }
        return moneys
    }

    private @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    fun getJSONResource(context: Context): String? {
        try {
            context.getAssets().open("list-currencies.json").use({ `is` ->
                val parser = JsonParser()
                return parser.parse(InputStreamReader(`is`)).toString()
            })
        } catch (e: Exception) {

        }

        return null
    }
}


