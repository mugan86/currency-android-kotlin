package amldev.currency.ui.fragments.settings

import amldev.currency.R
import amldev.currency.data.Constants
import amldev.currency.extensions.DataPreference
import amldev.currency.ui.activities.PreferencesActivity
import amldev.i18n.LanguageEnum
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.ListPreference
import android.preference.PreferenceFragment
import android.view.MenuItem

/**
 * Created by anartzmugika on 30/8/17.
 */
/**
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class GeneralPreferenceFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_general)
        setHasOptionsMenu(true)

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences
        // to their values. When their values change, their summaries are
        // updated to reflect the new value, per the Android Design
        // guidelines.


        PreferencesActivity.bindPreferenceSwitch(findPreference("example_switch"))
        // PreferencesActivity.bindPreferenceSummaryToValue(findPreference(Constants.SELECT_LANGUAGE))

        val selectLanguage = findPreference(Constants.SELECT_LANGUAGE) as ListPreference

        val selectLangCode = DataPreference.getPreference(activity, Constants.SELECT_LANGUAGE);

        println("Select lang code: " + selectLangCode)

        val value = String.format(String.format(resources.getString(R.string.select_language_summary),
                LanguageEnum.SELECT.getLanguageName(DataPreference.getPreference(activity, Constants.SELECT_LANGUAGE), activity)))
        println(value)
        selectLanguage.setSummary(value)

        selectLanguage.setOnPreferenceChangeListener { preference, newValue ->
            println(newValue.toString())
            DataPreference.setPreference(activity, Array<String>(1){Constants.UPDATE_LANGUAGE}, Array<String>(1){"1"})
            DataPreference.setPreference(activity, Array<String>(1){Constants.SELECT_LANGUAGE}, Array<String>(1){ newValue.toString()})
            activity.recreate()
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            startActivity(Intent(activity, PreferencesActivity::class.java))
            activity.finish()
            activity.overridePendingTransition(0,0)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

