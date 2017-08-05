package amldev.currency.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import amldev.currency.R
import android.view.View
import com.kotlin.amugika.gridview.ui.adapter.MoneysConversionsCustomGrid
import data.CurrencyRequest
import domain.commands.RequestCurrencyCommand
import kotlinx.android.synthetic.main.activity_select_money_conversions.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.uiThread

class SelectMoneyConversionsActivity : AppCompatActivity() {
    internal var imageId = intArrayOf(R.drawable.ic_australia, R.drawable.ic_canada, R.drawable.ic_china, R.drawable.ic_switzerland, R.drawable.ic_europe, R.drawable.ic_united_kingdom, R.drawable.ic_india, R.drawable.ic_japan, R.drawable.ic_malaysia, R.drawable.ic_russia, R.drawable.ic_singapore, R.drawable.ic_uuss)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_money_conversions)

        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        val symbol = intent.getStringExtra("symbol")
                ?: throw IllegalStateException("field symbol missing in Intent")

        val name = intent.getStringExtra("name")
                ?: throw IllegalStateException("field name missing in Intent")

        val flag = intent.getStringExtra("flag")
                ?: throw IllegalStateException("field flag missing in Intent")

        println("Get Intent Extra data:  $symbol / $name / $flag")

        selectMoneyInfoTextView.text = "Your select money is $name"

        selectLanguageFlag.setImageDrawable(resources.getDrawable(getFlagDrawable(flag)))

        inputConvertInfoTextView.text = "Input value to convert with $symbol: "


        editConversionValueImageButton.setOnClickListener {
            inputMoneyValueToConvertEditText.visibility = View.VISIBLE
            selectMoneyValueToConvertTextView.visibility = View.GONE
            editConversionValueImageButton.visibility = View.GONE
            inputMoneyValueToConvertEditText.selectAll()

        }

        val progress = indeterminateProgressDialog("This a progress dialog")

        doAsync {
            progress.show()
            var result = RequestCurrencyCommand(symbol).execute();
            println(result)
            println(result.getSelectMoneyCurrency("USD").currencyValue)

            println()


            uiThread {
                val moneys = CurrencyRequest().getMoneyList(this@SelectMoneyConversionsActivity).filter{ it.symbol != symbol}
                val adapter = MoneysConversionsCustomGrid(moneys, result, 1.0) //TODO use edittext value
                conversionOtherMoneyGridView.adapter = adapter
                // conversionOtherMoneyGridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> Toast.makeText(parent.context, "You Clicked at " + moneys[+position].name, Toast.LENGTH_SHORT).show() }
                progress.dismiss()
            }
        }


    }

    private fun getFlagDrawable(flag: String) =
            this.resources.getIdentifier("ic_$flag", "drawable", this.packageName)
}
