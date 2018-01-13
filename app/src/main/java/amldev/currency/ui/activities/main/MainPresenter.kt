package amldev.currency.ui.activities.main

import amldev.currency.domain.model.Money
import amldev.currency.interfaces.Provider

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

    fun onCreate() {
        loadData()
    }

    private fun loadData() {
        view.showProgress()
        provide.dataAsync { media ->
            view.updateData(media)
            view.hideProgress()
        }

    }

    fun itemClicked(item: Money) {
        view.navigateTo(item)
    }
}