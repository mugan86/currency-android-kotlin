package amldev.currency.ui.adapters

import amldev.currency.R
import amldev.currency.domain.model.Currency
import amldev.currency.domain.model.Money
import amldev.currency.extensions.ctx
import amldev.currency.extensions.numberInStringFormatWithDecimals
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.grid_item_money_conversion.view.*
import kotlin.properties.Delegates

/**
 * Created by anartzmugika on 3/8/17.
 */

class MoneysConversionsCustomGrid(moneys: List<Money>, private val currency: Currency, private val inputValue: Double) : BaseAdapter() {

    var moneys: List<Money> by Delegates.observable(moneys, { _, _, _ ->
        notifyDataSetChanged()
    })

    override fun getCount(): Int = moneys.size

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0L

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val grid: View
        if (convertView == null) {
            grid = (parent.ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.grid_item_money_conversion, null)
            var conversion = 0.0
            currency.moneyConversion.filter{it.symbol == moneys[position].symbol}.map{conversion = it.currencyValue}
            grid.gridText.text = numberInStringFormatWithDecimals(conversion * inputValue, 4)
            grid.gridImage.setImageResource(moneys[position].icon)
        } else { grid = convertView }

        return grid
    }
}