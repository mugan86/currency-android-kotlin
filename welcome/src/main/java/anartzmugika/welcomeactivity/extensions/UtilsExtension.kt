package anartzmugika.welcomeactivity.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.widget.CheckBox

/**
 * Created by anartzmugika on 7/9/17.
 */

    /**
     * Making notification bar transparent
     */
    fun changeStatusBarColor(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = (context as AppCompatActivity).window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun startNewActivity(from: Context, to: Class<*>) {
        from.startActivity(Intent(from, to))
        (from as AppCompatActivity).finish()
        from.overridePendingTransition(0, 0)
    }

    fun unselectCheckBox(languages: Array<CheckBox>, checked: Int) {

        for (i in languages.indices) {
            if (i != checked) languages[i].isChecked = false
        }
    }

    fun initializeDefaultLanguage(language: String?): String {
        if (language == null)
            return "en"
        else if (language == "en" || language == "es" || language == "eu") return language
        return "en"
    }

