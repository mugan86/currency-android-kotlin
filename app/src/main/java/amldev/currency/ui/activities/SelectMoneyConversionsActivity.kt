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
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.uiThread

class SelectMoneyConversionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_money_conversions)

        //val baseMoneySymbol: String, val baseMoneyName: String, val moneyConversion: List<Money>, val date: String

        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        val symbol = intent.getStringExtra("symbol")
                ?: throw IllegalStateException("field symbol missing in Intent")

        val name = intent.getStringExtra("name")
                ?: throw IllegalStateException("field name missing in Intent")

        val flag = intent.getStringExtra("flag")
                ?: throw IllegalStateException("field flag missing in Intent")

        var result = Currency(symbol, name, ArrayList() , "")

        println("Get Intent Extra data:  $symbol / $name / $flag")

        selectMoneyInfoTextView.text = "Your select money is $name"

        selectLanguageFlag.setImageDrawable(resources.getDrawable(getFlagDrawable(flag)))

        inputConvertInfoTextView.text = "Input value to convert with $symbol: "


        editConversionValueImageButton.setOnClickListener {
            inputMoneyValueToConvertEditText.visibility = View.VISIBLE
            selectMoneyValueToConvertTextView.visibility = View.GONE
            editConversionValueImageButton.visibility = View.GONE
            showHideKeyBoardForce(inputMoneyValueToConvertEditText, true, this)

            // linearlayout = inputMoneyValueToConvertEditText

        }

        val progress = indeterminateProgressDialog("Cargando los datos...")

        doAsync {
            progress.show()
            result = RequestCurrencyCommand(symbol).execute();
            println(result)
            println(result.getSelectMoneyCurrency("USD").currencyValue)

            println()


            uiThread {
                addMoneyConversionsData(result, symbol)
                // conversionOtherMoneyGridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> Toast.makeText(parent.context, "You Clicked at " + moneys[+position].name, Toast.LENGTH_SHORT).show() }
                progress.dismiss()
            }
        }

        inputMoneyValueToConvertEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            //TODO Pending to check
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.length == 0) {
                    addMoneyConversionsData(result, symbol)
                }
                else {
                    addMoneyConversionsData(result,symbol, inputMoneyValueToConvertEditText.toString().toDouble())
                }

            }
        })


    }

    private fun addMoneyConversionsData(result: Currency, symbol: String, value: Double = 1.0) {
        val moneys = CurrencyRequest().getMoneyList(this@SelectMoneyConversionsActivity).filter{ it.symbol != symbol}
        val adapter = MoneysConversionsCustomGrid(moneys, result, value) //TODO use edittext value
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


}
