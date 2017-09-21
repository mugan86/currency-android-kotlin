package amldev.currency.extensions

/**
 * Created by anartzmugika on 21/9/17.
 */

/************************************************************************************************************
 * Convert and round double value in string with specific decimals values
 * ------------------------------------------------------------------------
 *
 * For example: value = 148.390 / decimals = 2 => 148.39
 ***********************************************************************************************************/
fun numberInStringFormatWithDecimals (value: Double, decimals: Int) : String = "%.${decimals}f".format(value)