package amldev.currency.ui.activities

import amldev.currency.R
import amldev.currency.data.db.CurrencyDb
import amldev.currency.data.db.CurrencyDbHelper
import amldev.currency.extensions.getDefaultShareIntent
import amldev.currency.ui.adapters.MoneyAdapter
import amldev.i18n.LocaleHelper
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import data.CurrencyRequest
import domain.model.Money
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    var moneys: List<Money> = mutableListOf()
    //To use LocaleHelper select language
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addToolbar()

        addActions()

        moneysList.layoutManager = LinearLayoutManager(this)
        val progress = indeterminateProgressDialog(resources.getString(R.string.loading_currency_list_txt))
        doAsync {

            progress.show()
            //Load list currencies and log symbol and name

            val db = CurrencyDbHelper

            println(db.DB_NAME)



            if (CurrencyDb().getMoneyListItemsSize () == 0) {
                println("Load JSON File")
                loadDataFromJSONFileAndStoreInDB()
            }
            else{
                println("Get database info")
                moneys = CurrencyDb().getMoneyListItems()
            }



            uiThread {

                val adapter = MoneyAdapter(moneys , {
                    openConversionsWithSelectMoney(it.symbol, it.name, it.flag)
                })
                moneysList.adapter = adapter
                progress.dismiss()
                selectLanguageFab.visibility = View.VISIBLE


                // println(CurrencyDb().getMoneyListItemsSize().toString())


                // println("***********************Take money list from sqlite database ${moneys.size}")
            }
        }

    }

    private fun loadDataFromJSONFileAndStoreInDB () {
        moneys = CurrencyRequest().getMoneyList(this@MainActivity)
        moneys.map {
            println("Start to map money and save in sqlite db")
            CurrencyDb().saveMoney(Money(it.symbol, 0.0, it.name, it.flag))

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

    private fun addToolbar() {
        setSupportActionBar(toolbar)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(false);

        toolbar.setTitle(resources.getString(R.string.app_name))
        toolbar.setSubtitle(resources.getString(R.string.select_your_base_money))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.getItemId()) {
            R.id.action_share -> {
                startActivity(getDefaultShareIntent(this@MainActivity, null))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun addActions() {
        selectLanguageFab.setOnClickListener {
            LocaleHelper.languageOptionsDialog(this@MainActivity)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        onCreate(null)
    }
}
