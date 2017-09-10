package anartzmugika.welcomeactivity.ui.activities

import amldev.i18n.LocaleHelper
import anartzmugika.welcomeactivity.R
import anartzmugika.welcomeactivity.extensions.PrefManager
import anartzmugika.welcomeactivity.extensions.initializeDefaultLanguage
import anartzmugika.welcomeactivity.extensions.startNewActivity
import anartzmugika.welcomeactivity.extensions.unselectCheckBox
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*


class SelectLanguageActivity : AppCompatActivity() {

    public override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (LocaleHelper.getLanguage(this) == "")
            LocaleHelper.changeLang(this,
                    initializeDefaultLanguage(Locale.getDefault().language))

        selectLanguageTextView.text = String.format(getString(R.string.select_language_is), LocaleHelper.getLanguage(this))

        btn_start_again.setOnClickListener {
            // We normally won't show the welcome slider again in real app
            // but this is for testing
            // make first time launch TRUE
            PrefManager(applicationContext).isFirstTimeLaunch = true
            startNewActivity(this@SelectLanguageActivity, WelcomeActivity::class.java)
        }

        val languages = arrayOf(englishLanguageCheckbox, spanishLanguageCheckbox, basqueLanguageCheckbox)

        if (LocaleHelper.getLanguage(this) == "en") {
            languages[0].isChecked = true
            unselectCheckBox(languages, 0)
        }
        if (LocaleHelper.getLanguage(this) == "es") {
            languages[1].isChecked = true
            unselectCheckBox(languages, 1)
        }
        if (LocaleHelper.getLanguage(this) == "eu") {
            languages[2].isChecked = true
            unselectCheckBox(languages, 2)
        }

        englishLanguageCheckbox.setOnClickListener { manageLanguagesOptions(0) }

        spanishLanguageCheckbox.setOnClickListener { manageLanguagesOptions(1) }

        basqueLanguageCheckbox.setOnClickListener { manageLanguagesOptions(2) }
    }

    private fun manageLanguagesOptions(select: Int) {

        if (select == 0) {
            LocaleHelper.changeLang(this,
                    "en")
        } else if (select == 1) {
            LocaleHelper.changeLang(this,
                    "es")

        } else {
            LocaleHelper.changeLang(this,
                    "eu")
        }

    }
}
