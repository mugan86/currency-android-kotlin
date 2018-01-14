package amldev.currency.ui.adapters

import amldev.currency.R
import amldev.currency.domain.model.Money
import amldev.currency.extensions.MoneyItemUnit
import amldev.currency.extensions.ctx
import amldev.currency.ui.adapters.holders.ViewHolder
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlin.properties.Delegates

/***************************************************************
 * Created by anartzmugika on 31/7/17. Modify 13/01/2018
 *
 * Adapter to load money info in RecyclerView List with custom flag
 *******************************************************************/
class MoneyAdapter (
        items: List<Money> = emptyList(), private val itemClick: MoneyItemUnit)
    : RecyclerView.Adapter<ViewHolder>() {

    var items: List<Money> by Delegates.observable(items, { _, _, _ ->
        notifyDataSetChanged()
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_money, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Bind custom item (define in item_money.xml)
        holder.bindForecast(items[position])
    }

    override fun getItemCount(): Int = items.size
}