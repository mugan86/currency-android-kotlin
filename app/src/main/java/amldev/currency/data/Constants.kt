package amldev.currency.data

import android.content.Context

/**
 * Created by anartzmugika on 4/9/17.
 */
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
        fun ourAppUrlAndroidMarket (context: Context) = "https://play.google.com/store/apps/details?id=" + packageName(context)
        fun ourAppUrlGooglePlay (context: Context) = "https://play.google.com/store/apps/details?id=" + packageName(context)
        private fun packageName(context: Context) = context.packageName
    }
}
