package anartzmugika.welcomeactivity.extensions

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by anartzmugika on 5/9/17.
 */

class PrefManager(private val _context: Context) {

    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor

    // shared pref mode
    private var PRIVATE_MODE = 0

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
        private const val PREF_NAME = "welcome-preferences-app"

        private const val IS_FIRST_TIME_LAUNCH = "firstLaunch"
    }

}