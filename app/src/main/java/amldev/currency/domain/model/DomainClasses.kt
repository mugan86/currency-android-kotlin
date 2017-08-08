package domain.model

import amldev.currency.R

/***************************************************************************************************************
 * Created by Anartz Mugika on 19/07/2017. Updated in 05/08/2017.
 * Finish result model definition to use in Currency result
 **************************************************************************************************************/
data class Currency (val baseMoneySymbol: String, val baseMoneyName: String,
                     val moneyConversion: List<Money>, val date: String) {

    fun getSelectMoneyCurrency(selectMoney: String) : Money = moneyConversion.single(){ (it.symbol == selectMoney) }

    fun size() : Int = moneyConversion.size

}

data class Money (val symbol: String = "",
                  val currencyValue: Double = 0.0,
                  val name: String = "",
                  val flag: String = "united_nations",
                  val icon: Int = R.drawable.ic_united_nations)

