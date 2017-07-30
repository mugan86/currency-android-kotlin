package amldev.currency

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import domain.commands.RequestCurrencyCommand
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        doAsync {

            applicationContext.assets.open("list-currencies.json").use { input ->
                println(input)
            }

            var result = RequestCurrencyCommand("EUR").execute();
            uiThread {
                println(result.baseMoneySymbol + "  " + result.moneyConversion.toString())

                // TODO Unresolved reference size
                println(result.size)
                println(result.getSelectMoneyCurrency("USD").currencyValue)
            }
        }

    }

}
