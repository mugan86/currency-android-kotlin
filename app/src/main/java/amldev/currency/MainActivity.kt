package amldev.currency

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import domain.commands.RequestCurrencyCommand
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.JsonParser
import android.os.Build
import android.support.annotation.RequiresApi
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helloTextView.text = "Hello!!!!"

        doAsync {

            println(getJSONResource(applicationContext))

            val moneys = (Parser().parse(StringBuilder(getJSONResource(applicationContext))) as JsonObject)["currencies"] as JsonArray<*>

            println(moneys.toString())
            println(moneys.size)

            for (money in moneys) {
                val data = money as JsonObject
                val symbol = data["symbol"].toString()
                val name = data["name"].toString()
                println("$symbol: $name")
            }

            // moneys.map { money -> println((money as JsonObject).toString()) }

            var result = RequestCurrencyCommand("EUR").execute();
            uiThread {
                println(result.baseMoneySymbol + "  " + result.moneyConversion.toString())

                // TODO Unresolved reference size
                println(result.size)
                println(result.getSelectMoneyCurrency("USD").currencyValue)
            }
        }

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
