package amldev.currency.ui.adapters.holders

/**
 * Created by anartzmugika on 12/1/18.
 */
import amldev.currency.domain.model.Money
import amldev.currency.extensions.MoneyItemUnit
import amldev.currency.extensions.ctx
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_money.view.*


class ViewHolder(view: View,
                 val itemClick: MoneyItemUnit) : RecyclerView.ViewHolder(view) {

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