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
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_select_money_conversions.*
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject

class SelectMoneyConversionsActivity : AppCompatActivity(), SelectMoneyConversionsPresenter.View {

    @Inject lateinit var presenter: SelectMoneyConversionsPresenter
    private val component by lazy { app.component.plus(SelectMoneyConversionsModule(this)) }

    private val adapter = MoneysConversionsCustomGrid(moneys = emptyList(), currency = Currency(), inputValue = 0.0)
    override fun updateData(currency: Currency) {
        println("Update data ${currency}")
        adapter.moneys = currency.moneyConversion

        // CurrencyRequest().getMoneyList(this@SelectMoneyConversionsActivity).filter{ it.symbol != currency.baseMoneySymbol}

        // addMoneyConversionsData(currency, currency.baseMoneySymbol)

    }

    override fun showProgress() {
        println(2)
    }

    override fun hideProgress() {
        println(2)
    }


    //To use LocaleHelper select language
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_money_conversions)
        component.inject(this)

        conversionOtherMoneyGridView.adapter = adapter

        //Get Intent Extras values
        val extraData = getIntentExtras()

        selectMoneyInfoTextView.text = String.format(resources.getString(R.string.select_money_txt), extraData[1])

        selectLanguageFlag.setImageDrawable(resources.getDrawable(getFlagDrawable(this@SelectMoneyConversionsActivity, extraData[2])))

        inputConvertInfoTextView.text = String.format(resources.getString(R.string.input_value_to_convert), extraData[0])

        // val progress = indeterminateProgressDialog(resources.getString(R.string.loading_data))
        var result = Currency(extraData[0], extraData[1], ArrayList() , "")

        /*doAsync {
            progress.show()

            if (CurrencyDb().checkIfBaseMoneyHaveUpdateData(extraData[0])) { // Take data from sqlite database
                result = CurrencyDb().getSelectMoneyAndCurrencies(extraData[0])
            } else { // Take data from server or local json files
                result = RequestCurrencyCommand(extraData[0], this@SelectMoneyConversionsActivity).execute()
            }

            uiThread {
                addMoneyConversionsData(result, extraData[0])
                progress.dismiss()
            }
        }*/

        //Add InputMoneyValueConvertEditText View actions
        inputMoneyValueToConvertEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) addMoneyConversionsData(result, extraData[0])
                else if (inputMoneyValueToConvertEditText.text.toString().last() != '.')
                    addMoneyConversionsData(result, extraData[0], inputMoneyValueToConvertEditText.text.toString().toFloat())
            }
        })

        addActions()

        addToolbar(subtitle = extraData[1], title = resources.getString(R.string.app_name), backButton = true)

        presenter.onCreate(Currency(extraData[0], extraData[1], ArrayList(), ""), applicationContext)
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
    }

    private fun addMoneyConversionsData(result: Currency, symbol: String, value: Float = 1.0.toFloat()) {
        //Load list of moneys except select money base
        val moneys = CurrencyRequest().getMoneyList(this@SelectMoneyConversionsActivity).filter{ it.symbol != symbol}
        if (moneys.isEmpty()) {
            toast(resources.getString(R.string.no_correct_load_data))
            reloadDataLinearLayout.show()
            conversionOtherMoneyGridView.hide()
        } else {
            reloadDataLinearLayout.hide()
            conversionOtherMoneyGridView.show()
            val adapter = MoneysConversionsCustomGrid(moneys, result, value.toDouble())

            // Check if money value save and update. If not update, save after delete
            if (!CurrencyDb().checkIfBaseMoneyHaveUpdateData(symbol)) { CurrencyDb().saveBaseConversionMoneyValues(result) }

            conversionOtherMoneyGridView.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("Destroy activity and hide keyboard!")
        showHideKeyBoardForce(inputMoneyValueToConvertEditText, false, this)
    }

    override fun onPause() {
        super.onPause()
        println("Pause activity and hide keyboard!")
        showHideKeyBoardForce(inputMoneyValueToConvertEditText, false, this)
    }

    private fun getIntentExtras(): Array<String> {
        val data: Array<String> = arrayOf("", "", "")
        data.set(0, intent.getStringExtra(Constants.MONEY_GETSTREXTRA_SYMBOL_VALUE) ?: Constants.DEFAULT_MONEY_SYMBOL)
        data.set(1, intent.getStringExtra(Constants.MONEY_GETSTREXTRA_NAME_VALUE)?: Constants.DEFAULT_MONEY_NAME)
        data.set(2, intent.getStringExtra(Constants.MONEY_GETSTREXTRA_FLAG_VALUE)?: Constants.DEFAULT_MONEY_FLAG)
        return data
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        onCreate(null)
    }


}
