package amldev.currency.ui.activities

import amldev.currency.R
import amldev.currency.ui.adapters.MoneyAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import data.CurrencyRequest
import domain.model.Money
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    var moneys = ArrayList<Money>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        helloTextView.text = "Hello!!!!"
        moneysList.layoutManager = LinearLayoutManager(this)

        doAsync {

            //Load list currencies and log symbol and name

            moneys = CurrencyRequest().getMoneyList(this@MainActivity)

            uiThread {

                val adapter = MoneyAdapter(moneys) { /*toast("${it.symbol} / ${it.name}")*/ openConversionsWithSelectMoney(it.symbol, it.name, it.flag) }
                moneysList.adapter = adapter
            }
        }

    }

    private fun openConversionsWithSelectMoney(symbol: String, name: String, flag: String) {
        val intent = Intent(this, SelectMoneyConversionsActivity::class.java)
        intent.putExtra("symbol", symbol)
        intent.putExtra("name", name)
        intent.putExtra("flag", flag)
        startActivity(intent)
        overridePendingTransition(0,0)

    }
}
