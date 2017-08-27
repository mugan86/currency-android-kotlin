package amldev.currency.ui.adapters

import amldev.currency.R
import amldev.currency.extensions.ctx
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import domain.model.Money
import kotlinx.android.synthetic.main.item_money.view.*

/***************************************************************
 * Created by anartzmugika on 31/7/17.
 *
 * Adapter to load money info in RecyclerView List with custom flag
 *******************************************************************/
class MoneyAdapter (
        val moneyList: List<Money>, val itemClick: (Money) -> Unit)
    : RecyclerView.Adapter<MoneyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_money, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Bind custom item (define in item_money.xml)
        holder.bindForecast(moneyList[position])
    }

    override fun getItemCount(): Int = moneyList.size

    class ViewHolder(view: View,
                     val itemClick: (Money) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindForecast(money: Money) {
            with(money) {
                Picasso.with(itemView.ctx).load(getFlagDrawable(itemView.ctx, flag)).into(itemView.icon)
                itemView.symbolTextView.text = symbol
                itemView.nameTextView.text = name
                itemView.setOnClickListener { itemClick(this) }
            }
        }
        private fun getFlagDrawable(context: Context, flag: String) =
                context.resources.getIdentifier("ic_$flag", "drawable", context.packageName)
    }

    interface OnItemClickListener {
        operator fun invoke(money: Money)
    }
}