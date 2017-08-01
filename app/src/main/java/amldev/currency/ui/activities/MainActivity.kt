package amldev.currency.ui.activities

import amldev.currency.R
import amldev.currency.ui.adapters.MoneyAdapter
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.doAsync
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.JsonParser
import android.os.Build
import android.support.annotation.RequiresApi
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import domain.model.Money
import org.jetbrains.anko.toast
import java.io.InputStreamReader
import android.support.v7.widget.LinearLayoutManager
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helloTextView.text = "Hello!!!!"

        val moneys = ArrayList<Money>()

        moneysList.layoutManager = LinearLayoutManager(this)

        doAsync {

            //Load list currencies and log symbol and name

            ((Parser().parse(StringBuilder(getJSONResource(applicationContext)))
                    as JsonObject)["currencies"] as JsonArray<*>).map {
                money ->
                    val data = money as JsonObject
                    val symbol = data["symbol"].toString()
                    val name = data["name"].toString()
                    val flag = data["flag"].toString()
                    println("$symbol: $name ----> Flag: $flag")
                    moneys.add(Money(symbol, 0.0, name, flag))
            }

            println(moneys.size)

            uiThread {

                val adapter = MoneyAdapter(moneys) { toast("${it.symbol} / ${it.name}") }
                moneysList.adapter = adapter
            }



            /*var result = RequestCurrencyCommand("EUR").execute();
            uiThread {
                println(result.baseMoneySymbol + "  " + result.moneyConversion.toString())

                // TODO Unresolved reference size
                println(result.size)
                println(result.getSelectMoneyCurrency("USD").currencyValue)
            }*/
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
