package amldev.currency.ui.activities

import amldev.currency.R
import amldev.currency.ui.utils.showHideKeyBoardForce
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
import org.jetbrains.anko.uiThread

class SelectMoneyConversionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_money_conversions)
        val symbol = intent.getStringExtra("symbol")
                ?: throw IllegalStateException("field symbol missing in Intent")

        val name = intent.getStringExtra("name")
                ?: throw IllegalStateException("field name missing in Intent")

        val flag = intent.getStringExtra("flag")
                ?: throw IllegalStateException("field flag missing in Intent")


        selectMoneyInfoTextView.text = "Your select money is $name"

        selectLanguageFlag.setImageDrawable(resources.getDrawable(getFlagDrawable(flag)))

        inputConvertInfoTextView.text = "Input value to convert with $symbol: "

        val progress = indeterminateProgressDialog("Cargando los datos...")
        var result = Currency(symbol, name, ArrayList() , "")

        doAsync {
            progress.show()
            result = RequestCurrencyCommand(symbol).execute();

            uiThread {
                addMoneyConversionsData(result, symbol)
                progress.dismiss()
            }
        }

        //Add InputMoneyValueConvertEditText View actions

        inputMoneyValueToConvertEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) addMoneyConversionsData(result, symbol)
                else if (inputMoneyValueToConvertEditText.text.toString().last() != '.')
                    addMoneyConversionsData(result,symbol, inputMoneyValueToConvertEditText.text.toString().toFloat())
            }
        })

        editConversionValueImageButton.setOnClickListener {
            availableInputMoneyToMakeCurrencyConversions()
        }

        selectMoneyValueToConvertTextView.setOnClickListener {
            availableInputMoneyToMakeCurrencyConversions()
        }

        addToolbar(name)
    }

    private fun availableInputMoneyToMakeCurrencyConversions() {
        inputMoneyValueToConvertEditText.visibility = View.VISIBLE
        selectMoneyValueToConvertTextView.visibility = View.GONE
        editConversionValueImageButton.visibility = View.GONE
        showHideKeyBoardForce(inputMoneyValueToConvertEditText, true, this)

        // linearlayout = inputMoneyValueToConvertEditText
    }

    private fun addMoneyConversionsData(result: Currency, symbol: String, value: Float = 1.0.toFloat()) {
        val moneys = CurrencyRequest().getMoneyList(this@SelectMoneyConversionsActivity).filter{ it.symbol != symbol}
        val adapter = MoneysConversionsCustomGrid(moneys, result, value.toDouble())
        conversionOtherMoneyGridView.adapter = adapter
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

        toolbar.setTitle("Currency converter")
        toolbar.setSubtitle("$name")

        toolbar.setNavigationIcon(resources.getDrawable(R.drawable.ic_back))
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            //What to do on back clicked
            finish()
        })
    }


}
