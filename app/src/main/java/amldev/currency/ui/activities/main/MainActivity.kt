package amldev.currency.ui.activities.main

import amldev.currency.R
import amldev.currency.data.Constants
import amldev.currency.domain.model.Extra
import amldev.currency.domain.model.Money
import amldev.currency.extensions.*
import amldev.currency.ui.activities.main.di.MainModule
import amldev.currency.ui.activities.money_conversions.SelectMoneyConversionsActivity
import amldev.currency.ui.activities.preferences.PreferencesActivity
import amldev.currency.ui.adapters.MoneyAdapter
import amldev.i18n.LocaleHelper
import anartzmugika.welcomeactivity.extensions.PrefManager
import anartzmugika.welcomeactivity.ui.activities.WelcomeActivity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fab.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainPresenter.View {

    @Inject lateinit var presenter: MainPresenter
    private val component by lazy { app.component.plus(MainModule(this)) }

    private val adapter = MoneyAdapter { itemClicked(it) }

    //To use LocaleHelper select language
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    override fun onResume() {
        super.onResume()
        if (PrefManager(this@MainActivity).isFirstTimeLaunch) {
            startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
            overridePendingTransition(0,0)
        }
        if (DataPreference.getPreference(this, Constants.UPDATE_LANGUAGE).equals("1")) {
            DataPreference.setPreference(this, Array<String>(1){Constants.UPDATE_LANGUAGE}, Array<String>(1){"0"})
            this.recreate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)
        addToolbar(title = resources.getString(R.string.app_name), subtitle = resources.getString(R.string.select_your_base_money))
        addActions()
        moneysList.layoutManager = LinearLayoutManager(this)
        moneysList.adapter = adapter
        presenter.onCreate()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.itemId) {
            R.id.action_share -> {
                startActivity(getDefaultShareIntent(this@MainActivity, null))
                return true
            }
            R.id.settings -> {
                navigate<PreferencesActivity>(finishThisActivity = false)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun addActions() {
        sendOpinionInGooglePlay.setOnClickListener {
            goToMarket()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        onCreate(null)
    }

    override fun showProgress() = progress.show()
    override fun hideProgress() = progress.hide()

    override fun navigateTo(money: Money) {
        val extras: List<Extra> = listOf(Extra("symbol", money.symbol), Extra("name", money.name), Extra("flag", money.flag))
        navigate<SelectMoneyConversionsActivity>(extras, finishThisActivity = false)
    }

    override fun updateData(media: List<Money>) {
        adapter.items = media
        sendOpinionInGooglePlay.show()
    }

    override fun itemClicked(money: Money) {
        presenter.itemClicked(money)
    }
}
