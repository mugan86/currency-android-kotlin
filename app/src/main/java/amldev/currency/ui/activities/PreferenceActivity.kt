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
import amldev.i18n.LocaleHelper
import android.support.v7.widget.Toolbar
import android.view.MenuItem;
import android.view.View
import android.view.ViewGroup
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.LinearLayout



/**
 * Created by anartzmugika on 29/8/17.
 */
class SettingsActivity : AppCompatPreferenceActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //load()

        val root = findViewById<View>(android.R.id.list).parent.parent.parent as LinearLayout
        val toolbar = LayoutInflater.from(this).inflate(R.layout.toolbar, root, false) as Toolbar
        root.addView(toolbar, 0)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        fragmentManager.beginTransaction().replace(android.R.id.content, GeneralPreferenceFragment()).commit()
    }

    /**
     * Set up the [android.app.ActionBar], if the API is available.
     */
    private fun setupActionBar() {
        layoutInflater.inflate(R.layout.toolbar, findViewById<View>(android.R.id.content) as ViewGroup)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun load() {
        setupActionBar()
        fragmentManager.beginTransaction().replace(android.R.id.content, GeneralPreferenceFragment()).commit()

        val horizontalMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                2f, resources.displayMetrics).toInt()

        val verticalMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                2f, resources.displayMetrics).toInt()

        val topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (resources.getDimension(4).toInt() + 30).toFloat(),
                resources.displayMetrics).toInt()

        listView.setPadding(horizontalMargin, topMargin, horizontalMargin, verticalMargin)
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