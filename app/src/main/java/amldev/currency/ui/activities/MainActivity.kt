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
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jetbrains.anko.indeterminateProgressDialog


class MainActivity : AppCompatActivity() {
    var moneys = ArrayList<Money>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(false);

        toolbar.setTitle("Currency converter")
        toolbar.setSubtitle("Select your base money")

        introTextView.text = "Select your base money to make all currency conversions"
        moneysList.layoutManager = LinearLayoutManager(this)
        val progress = indeterminateProgressDialog("Loading currency list...")
        doAsync {

            progress.show()
            //Load list currencies and log symbol and name

            moneys = CurrencyRequest().getMoneyList(this@MainActivity)

            uiThread {

                val adapter = MoneyAdapter(moneys) { /*toast("${it.symbol} / ${it.name}")*/ openConversionsWithSelectMoney(it.symbol, it.name, it.flag) }
                moneysList.adapter = adapter
                progress.dismiss()
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
