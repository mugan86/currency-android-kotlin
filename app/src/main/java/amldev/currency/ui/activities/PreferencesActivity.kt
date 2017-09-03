package amldev.currency.ui.activities

import amldev.currency.extensions.DataPreference
import amldev.currency.ui.fragments.settings.GeneralPreferenceFragment
import amldev.i18n.LocaleHelper
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.preference.*


/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 *
 * See [
 * Android Design: Settings](http://developer.android.com/design/patterns/settings.html) for design guidelines and the [Settings
 * API Guide](http://developer.android.com/guide/topics/ui/settings.html) for more information on developing a Settings UI.
 */
class PreferencesActivity : AppCompatPreferenceActivity() {

    init {
        instance = this
    }

    //To use LocaleHelper select language
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        load()
    }

    fun load() {
        // setupActionBar()
        val actionBar = supportActionBar
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Options"
        }
        fragmentManager.beginTransaction().replace(android.R.id.content, GeneralPreferenceFragment()).commit()
    }

    companion object {

        private var instance: PreferencesActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        /**
         * A preference value change listener that updates the preference's summary
         * to reflect its new value.
         */
        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->
            val stringValue = value.toString()

            val languageBeforeChange = DataPreference.getPreference(applicationContext(), "SELECT_LANGUAGE")

            println("Value in preferences $stringValue / Before select language: $languageBeforeChange")

            if (preference is ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                val index = preference.findIndexOfValue(stringValue)

                // Set the summary to reflect the new value.
                preference.setSummary(
                        if (index >= 0)
                            preference.entries[index]
                        else
                            null)

            } else if (preference is SwitchPreference) {
                val v = value as Boolean
                println(v)
                if (v) {
                    println("Active!!!")
                }
                else {
                    println("Not active")
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.summary = stringValue
            }
            true
        }

        /**
         * Helper method to determine if the device has an extra-large screen. For
         * example, 10" tablets are extra-large.
         */
        private fun isXLargeTablet(context: Context): Boolean {
            return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE
        }
        fun bindPreferenceSwitch(preference: Preference) {
            val optionSwitchPreference = preference as SwitchPreference

            optionSwitchPreference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { arg0,  optionSwitchPreference ->
                    val isSwitchOn = optionSwitchPreference as Boolean
                    if (isSwitchOn) println("Active!!!")
                    else println("Not active")
                    true
            }
        }


        /**
         * Binds a preference's summary to its value. More specifically, when the
         * preference's value is changed, its summary (line of text below the
         * preference title) is updated to reflect the value. The summary is also
         * immediately updated upon calling this method. The exact display format is
         * dependent on the type of preference.
         *
         * @see .sBindPreferenceSummaryToValueListener
         */
        fun bindPreferenceSummaryToValue(preference: Preference) {
            // Set the listener to watch for value changes.
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

            // Trigger the listener immediately with the preference's
            // current value.
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.context)
                            .getString(preference.key, ""))
        }
    }
}
