package amldev.currency.extensions

import amldev.currency.R
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.toolbar.*

/********************************************************************************
 * Created by Anartz Mugika (mugan86@gmail.com) on 13/1/18.
 ********************************************************************************/
fun AppCompatActivity.addToolbar(color: Int = resources.getColor(R.color.White), title: String = resources.getString(R.string.app_name), subtitle: String = "",
                                 navigationIcon: Int = R.drawable.ic_back, backButton: Boolean = false) {
    supportActionBar?.setDisplayHomeAsUpEnabled(false)

    toolbar.title = title
    toolbar.setTitleTextColor(color)
    if (subtitle != "") toolbar.subtitle = subtitle
    setSupportActionBar(toolbar)

    if (backButton) {
        toolbar.navigationIcon = resources.getDrawable(navigationIcon)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            //What to do on back clicked
            finish()
        })
    }
}