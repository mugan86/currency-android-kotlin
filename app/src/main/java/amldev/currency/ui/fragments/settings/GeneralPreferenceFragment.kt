package amldev.currency.ui.fragments.settings

import amldev.currency.R
import amldev.currency.ui.activities.Settings2Activity
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
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
        Settings2Activity.bindPreferenceSummaryToValue(findPreference("example_text"))
        Settings2Activity.bindPreferenceSummaryToValue(findPreference("example_list"))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            startActivity(Intent(activity, Settings2Activity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}