package amldev.currency.ui.activities.money_conversions

import amldev.currency.R
import amldev.currency.data.Constants
import amldev.currency.data.db.CurrencyDb
import amldev.currency.data.server.CurrencyRequest
import amldev.currency.domain.model.Currency
import amldev.currency.extensions.*
import amldev.currency.ui.activities.money_conversions.di.SelectMoneyConversionsModule
import amldev.currency.ui.adapters.MoneysConversionsCustomGrid
import amldev.i18n.LocaleHelper
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_select_money_conversions.*
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject

class SelectMoneyConversionsActivity : AppCompatActivity(), SelectMoneyConversionsPresenter.View {

    @Inject lateinit var presenter: SelectMoneyConversionsPresenter
    private val component by lazy { app.component.plus(SelectMoneyConversionsModule(this)) }

    private val extras: MutableList<String> = mutableListOf("", "", "")

    override fun updateData(currency: Currency) {
        addMoneyConversionsData(currency, currency.baseMoneySymbol)
    }

    override fun showProgress() = progress.show()
    override fun hideProgress() = progress.hide()

    //To use LocaleHelper select language
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_money_conversions)
        component.inject(this)
        //Get Intent Extras values
        getIntentExtras()
        selectMoneyInfoTextView.text = String.format(resources.getString(R.string.select_money_txt), extras[1])
        selectLanguageFlag.setImageDrawable(ActivityCompat.getDrawable(this, getFlagDrawable(this@SelectMoneyConversionsActivity, extras[2])))
        inputConvertInfoTextView.text = String.format(resources.getString(R.string.input_value_to_convert), extras[0])
        addActions()
        addToolbar(subtitle = extras[1], title = resources.getString(R.string.app_name), backButton = true)
        presenter.onCreate(Currency(extras[0], extras[1], ArrayList(), ""), applicationContext)
    }

    private fun availableInputMoneyToMakeCurrencyConversions() {
        inputMoneyValueToConvertEditText.show()
        selectMoneyValueToConvertTextView.hide()
        editConversionValueImageButton.hide()
        showHideKeyBoardForce(inputMoneyValueToConvertEditText, true, this)
    }

    private fun addActions() {
        editConversionValueImageButton.setOnClickListener {
            availableInputMoneyToMakeCurrencyConversions()
        }

        selectMoneyValueToConvertTextView.setOnClickListener {
            availableInputMoneyToMakeCurrencyConversions()
        }

        reloadDataButton.setOnClickListener {
            recreate()
        }

        inputMoneyValueToConvertEditText.afterTextChanged {
            //Use extension in ActionExtensions.kt
            if (it.isEmpty()) addMoneyConversionsData(Currency(extras[0], extras[1], ArrayList(), ""), extras[0])
            else if (inputMoneyValueToConvertEditText.text.toString().last() != '.')
                addMoneyConversionsData(Currency(extras[0], extras[1], ArrayList(), ""), extras[0], inputMoneyValueToConvertEditText.text.toString().toFloat())
        }
    }

    private fun addMoneyConversionsData(result: Currency, symbol: String, value: Float = 1.0.toFloat()) {
        //Load list of moneys except select money base
        val moneys = CurrencyRequest().getMoneyList(this@SelectMoneyConversionsActivity).filter{ it.symbol != symbol}
        showProgress()
        if (moneys.isEmpty()) {
            toast(resources.getString(R.string.no_correct_load_data))
            reloadDataLinearLayout.show()
            conversionOtherMoneyGridView.hide()
        } else {
            reloadDataLinearLayout.hide()
            conversionOtherMoneyGridView.show()
            conversionOtherMoneyGridView.adapter = MoneysConversionsCustomGrid(moneys, CurrencyDb().getSelectMoneyAndCurrencies(result.baseMoneySymbol), value.toDouble())
        }
        hideProgress()
    }

    override fun onDestroy() {
        super.onDestroy()
        showHideKeyBoardForce(inputMoneyValueToConvertEditText, false, this)
    }

    override fun onPause() {
        super.onPause()
        showHideKeyBoardForce(inputMoneyValueToConvertEditText, false, this)
    }

    private fun getIntentExtras() {
        extras[0] = intent.getStringExtra(Constants.MONEY_GETSTREXTRA_SYMBOL_VALUE) ?: Constants.DEFAULT_MONEY_SYMBOL
        extras[1] = intent.getStringExtra(Constants.MONEY_GETSTREXTRA_NAME_VALUE) ?: Constants.DEFAULT_MONEY_NAME
        extras[2] = intent.getStringExtra(Constants.MONEY_GETSTREXTRA_FLAG_VALUE) ?: Constants.DEFAULT_MONEY_FLAG
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        onCreate(null)
    }

}
