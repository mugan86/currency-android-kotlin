package domain.model

/***************************************************************************************************************
 * Created by Anartz Mugika on 19/07/2017.
 * Finish result model definition to use in Currency result
 **************************************************************************************************************/
data class Currency (val baseMoneySymbol: String, val baseMoneyName: String,
                     val moneyConversion: List<Money>, val date: String) {
    fun getSelectMoneyCurrency(selectMoney: String) : Money {
        moneyConversion.filter{
            (it.symbol == selectMoney)
        }.map {  return it}
        return Money()
    }

    val size: Int get() = 12

}

data class Money (val symbol: String = "", val currencyValue: Double = 0.0, val name: String = "")

