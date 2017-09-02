package amldev.currency.extensions

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager


/**
 * Created by anartzmugika on 2/9/17.
 */
class DataPreference(private val _context: Context) {
    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor

    // shared pref mode
    private val PRIVATE_MODE = 0

    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    var isFirstTimeLaunch: Boolean
        get() = pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
        set(isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
            editor.commit()
        }

    companion object {

        // Shared preferences file name
        private val PREF_NAME = "intro-welcome"

        private val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"

        fun getPreferencesFile(context: Context): SharedPreferences =
                // This sample app persists the registration ID in shared preferences, but
                // how you store the regID in your app is up to you.
                PreferenceManager.getDefaultSharedPreferences(context)

        fun getPreference(context: Context, propertyName: String): String {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sharedPreferences.getString(propertyName, "")
        }

        fun setPreference(context: Context, propertyNames: Array<String>, propertyValues: Array<String>) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()

            for (i in propertyNames.indices) {
                editor.putString(propertyNames[i], propertyValues[i])
            }

            editor.apply()
        }


    }
}