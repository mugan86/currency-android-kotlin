package amldev.currency.ui.activities

import amldev.currency.R
import amldev.currency.data.db.CurrencyDbHelper
import android.widget.Toast
import android.preference.Preference
import android.preference.EditTextPreference
import android.preference.ListPreference
import android.os.Bundle
import android.preference.PreferenceFragment
import android.os.Build
import android.annotation.TargetApi
import amldev.currency.extensions.AppCompatPreferenceActivity
import amldev.i18n.LocaleHelper
import android.view.MenuItem;


/**
 * Created by anartzmugika on 29/8/17.
 */
class SettingsActivity : AppCompatPreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        load()

        //https://bitbucket.org/csandroid/webview-example-codesyntax/src/141bd484afc6972de716d4d76b38b6529e8e304d/app/src/main/java/com/codesyntax/web/MyPreferencesActivity.java?at=master&fileviewer=file-view-default
    }

    /**
     * Set up the [android.app.ActionBar], if the API is available.
     */
    private fun setupActionBar() {
        val actionBar = supportActionBar
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle("Options")
        }
    }

    fun load() {
        setupActionBar()
        fragmentManager.beginTransaction().replace(android.R.id.content, GeneralPreferenceFragment()).commit()
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class GeneralPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.settings)
            setHasOptionsMenu(true)

            var necessary_upload_first_access = false

            val select_lang_pref = findPreference("select_language_preference") as ListPreference

            val dbHelper = CurrencyDbHelper

            val name_last_name_preference = findPreference("name_last_name_preference") as EditTextPreference

            // name_last_name_preference.setDefaultValue(dbHelper.getUser().getName())
            // name_last_name_preference.setTitle(dbHelper.getUser().getName())


            /*if (DataPreference.getPreference(activity, "select_language_preference").equals("")) {
                DataPreference.setPreference(activity.applicationContext, arrayOf("select_language_preference"), arrayOf<String>(DataPreference.getLocaleLanguage(activity)))
                necessary_upload_first_access = true

            }

            select_lang_pref.setDefaultValue(DataPreference.getPreference(activity, "select_language_preference"))
            select_lang_pref.title = resources.getString(R.string.select_language_pref_text)
            select_lang_pref.summary = "TEst"*/

            if (necessary_upload_first_access) (activity as SettingsActivity).load()


            select_lang_pref.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
                //set your shared preference value equal to checked
                println("Aldatuta: " + newValue.toString())
                LocaleHelper.setLocale(activity, newValue.toString())
                //(activity as SettingsActivity).load()

                true
            }

            name_last_name_preference.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
                // dbHelper.updateUserProfileData(dbHelper.getUser().getName(), newValue.toString())
                println("Change value: " + newValue.toString())
                (activity as SettingsActivity).load()
                true
            }

        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.getItemId()
            if (id == android.R.id.home) {
                Toast.makeText(activity.applicationContext, "Close!", Toast.LENGTH_LONG).show()
                //startActivity(new Intent(getActivity(), SettingsActivity.class));
                activity.finish()
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }
}