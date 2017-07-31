package amldev.currency.ui.adapters

import amldev.currency.R
import amldev.currency.ui.utils.ctx
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import domain.model.Money
import kotlinx.android.synthetic.main.item_money.view.*


/**
 * Created by anartzmugika on 31/7/17.
 */
class MoneyAdapter (
        val moneyList: ArrayList<Money>, val itemClick: (Money) -> Unit)
    : RecyclerView.Adapter<MoneyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_money, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Bind custom item (define in item_forecast.xml)
        holder.bindForecast(moneyList[position])
    }

    override fun getItemCount(): Int = moneyList.size

    class ViewHolder(view: View,
                     val itemClick: (Money) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindForecast(money: Money) {
            with(money) {
                // Picasso.with(itemView.ctx).load(iconUrl).into(itemView.icon)
                itemView.symbolTextView.text = symbol
                itemView.nameTextView.text = name
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }

    interface OnItemClickListener {
        operator fun invoke(money: Money)
    }
}