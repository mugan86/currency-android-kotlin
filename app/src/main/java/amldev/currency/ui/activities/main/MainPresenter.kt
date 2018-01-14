package amldev.currency.ui.activities.main

import amldev.currency.domain.model.Money
import amldev.currency.interfaces.Provider
import android.content.Context

/**
 * Created by anartzmugika on 12/1/18.
 */
class MainPresenter(val view: View, val provide: Provider) {

    interface View {
        fun updateData(media: List<Money>)
        fun showProgress()
        fun hideProgress()
        fun navigateTo(money: Money)
        fun itemClicked(money: Money)
    }

    fun onCreate(context: Context) {
        loadData(context)
    }

    private fun loadData(context: Context) {
        view.showProgress()
        provide.loadCurrenciesList(context) { media ->
            view.updateData(media)
            view.hideProgress()
        }

    }

    fun itemClicked(item: Money) {
        view.navigateTo(item)
    }
}