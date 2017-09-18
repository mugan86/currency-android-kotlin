package amldev.currency.ui.activities

import amldev.currency.R
import amldev.currency.data.Constants
import amldev.currency.data.db.CurrencyDb
import amldev.currency.extensions.showHideKeyBoardForce
import amldev.i18n.LocaleHelper
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.kotlin.amugika.gridview.ui.adapter.MoneysConversionsCustomGrid
import data.CurrencyRequest
import domain.commands.RequestCurrencyCommand
import domain.model.Currency
import kotlinx.android.synthetic.main.activity_select_money_conversions.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.util.*

class SelectMoneyConversionsActivity : AppCompatActivity() {
    //To use LocaleHelper select language
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_money_conversions)

        //Get Intent Extras values

        val extraData = getIntentExtras()

        selectMoneyInfoTextView.text = String.format(resources.getString(R.string.select_money_txt), extraData[1])

        selectLanguageFlag.setImageDrawable(resources.getDrawable(getFlagDrawable(extraData[2])))

        inputConvertInfoTextView.text = String.format(resources.getString(R.string.input_value_to_convert), extraData[0])

        val progress = indeterminateProgressDialog(resources.getString(R.string.loading_data))
        var result = Currency(extraData[0], extraData[1], ArrayList() , "")

        doAsync {
            progress.show()

            //TODO Check IF EXIST VALUE IN DB TO GET VALUE from DB
            if (!CurrencyDb().checkIfBaseMoneyUpdateCurrencyValues(extraData[0])) {
                println("Situation to get data from database")
                // CurrencyDb().getMoneyListItems()

                // TODO Extract select money all currencies list

            }
            result = RequestCurrencyCommand(extraData[0], this@SelectMoneyConversionsActivity).execute();

            uiThread {
                // TODO CHECK IF SAVE CORRECT
                //Check if exist value and update data is diferent to current data to update or insert
                // CurrencyDb().saveBaseConversionMoneyValues(result)
                addMoneyConversionsData(result, extraData[0])
                progress.dismiss()
            }
        }

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

        addToolbar(extraData[1])
    }

    private fun availableInputMoneyToMakeCurrencyConversions() {
        inputMoneyValueToConvertEditText.visibility = View.VISIBLE
        selectMoneyValueToConvertTextView.visibility = View.GONE
        editConversionValueImageButton.visibility = View.GONE
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
            recreate();
        }
    }

    private fun addMoneyConversionsData(result: Currency, symbol: String, value: Float = 1.0.toFloat()) {
        val moneys = CurrencyRequest().getMoneyList(this@SelectMoneyConversionsActivity).filter{ it.symbol != symbol}
        if (moneys.isEmpty()) {
            toast(resources.getString(R.string.no_correct_load_data))
            reloadDataLinearLayout.visibility = View.VISIBLE
            conversionOtherMoneyGridView.visibility = View.GONE
        } else {
            reloadDataLinearLayout.visibility = View.GONE
            conversionOtherMoneyGridView.visibility = View.VISIBLE
            val adapter = MoneysConversionsCustomGrid(moneys, result, value.toDouble())
            conversionOtherMoneyGridView.adapter = adapter
        }
    }

    private fun getFlagDrawable(flag: String) =
            this.resources.getIdentifier("ic_$flag", "drawable", this.packageName)

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

    private fun addToolbar(name: String) {
        setSupportActionBar(toolbar)

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(resources.getString(R.string.app_name))
        toolbar.setSubtitle("$name")

        toolbar.setNavigationIcon(resources.getDrawable(R.drawable.ic_back))
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            //What to do on back clicked
            finish()
        })
    }

    private fun getIntentExtras(): Array<String> {
        var data: Array<String> = arrayOf("", "", "")
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
