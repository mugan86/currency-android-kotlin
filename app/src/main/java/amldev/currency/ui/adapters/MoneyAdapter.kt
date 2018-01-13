package amldev.currency.ui.adapters

import amldev.currency.R
import amldev.currency.domain.model.Money
import amldev.currency.extensions.MoneyItem
import amldev.currency.extensions.ctx
import amldev.currency.ui.adapters.holders.ViewHolder
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/***************************************************************
 * Created by anartzmugika on 31/7/17.
 *
 * Adapter to load money info in RecyclerView List with custom flag
 *******************************************************************/
class MoneyAdapter (
        val moneyList: List<Money>, val itemClick: MoneyItem)
    : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_money, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Bind custom item (define in item_money.xml)
        holder.bindForecast(moneyList[position])
    }

    override fun getItemCount(): Int = moneyList.size

    interface OnItemClickListener {
        operator fun invoke(money: Money)
    }
}