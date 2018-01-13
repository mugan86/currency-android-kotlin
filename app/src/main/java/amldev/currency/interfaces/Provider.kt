package amldev.currency.interfaces

import amldev.currency.extensions.MoneysListUnit

/**
 * Created by anartzmugika on 13/1/18.
 */
interface Provider {
    fun dataAsync(f: MoneysListUnit)
}