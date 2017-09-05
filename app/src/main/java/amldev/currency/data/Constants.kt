package amldev.currency.data

import android.content.Context

/***************************************************************************************************
 * Created by anartzmugika on 4/9/17.
 * Add constants to use in app.
 **************************************************************************************************/
class Constants {
    companion object {

        //Manage languages info
        val SELECT_LANGUAGE = "SELECT_LANGUAGE"
        val UPDATE_LANGUAGE = "UPDATE_LANGUAGE"

        //Default money data
        val DEFAULT_MONEY_SYMBOL = "EUR"
        val DEFAULT_MONEY_NAME = "Euro"
        val DEFAULT_MONEY_FLAG = "europe"
        val MONEY_GETSTREXTRA_SYMBOL_VALUE = "symbol"
        val MONEY_GETSTREXTRA_NAME_VALUE = "name"
        val MONEY_GETSTREXTRA_FLAG_VALUE = "flag"

        //Share constants
        val INTENT_TYPE_TEXT = "text/plain"
        val INTENT_TYPE_IMAGE = "image/*"
        val SHARE_IMAGE_DEFAULT = "share_image.png"
        val URL_FILE_START = "file://"

        //URLs
        private val GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id="
        private val MARKET_URL = "market://details?id="
        fun ourAppUrlAndroidMarket (context: Context) = "$MARKET_URL${packageName(context)}"
        fun ourAppUrlGooglePlay (context: Context) = "$GOOGLE_PLAY_URL${packageName(context)}"
        private fun packageName(context: Context) = context.packageName
    }
}
