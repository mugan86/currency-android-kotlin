package amldev.currency.ui.activities.money_conversions

import amldev.currency.domain.model.Currency
import amldev.currency.interfaces.Provider
import android.content.Context

/**
 * Created by anartzmugika on 13/1/18.
 */
class SelectMoneyConversionsPresenter(val view: View, val provide: Provider) {

    interface View {
        fun updateData(currency: Currency)
        fun showProgress()
        fun hideProgress()
    }

    fun onCreate(currency: Currency, context: Context) {
        loadData(currency, context)
    }

    private fun loadData(currency: Currency, context: Context) {
        view.showProgress()
        provide.loadMoneySelectData(currency, context) { view.updateData(currency) }
    }
}