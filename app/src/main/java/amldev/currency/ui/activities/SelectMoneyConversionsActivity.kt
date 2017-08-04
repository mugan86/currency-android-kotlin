package amldev.currency.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import amldev.currency.R
import android.widget.AdapterView
import android.widget.Toast
import com.kotlin.amugika.gridview.ui.adapter.MoneysConversionsCustomGrid
import kotlinx.android.synthetic.main.activity_select_money_conversions.*

class SelectMoneyConversionsActivity : AppCompatActivity() {
    internal var nations = arrayOf("Australia", "Canada", "China", "Europe", "India", "Japan", "Malaysia", "Russia", "Singapore", "Switzerland", "United Kingdom", "United states")
    internal var imageId = intArrayOf(R.drawable.ic_australia, R.drawable.ic_canada, R.drawable.ic_china, R.drawable.ic_europe, R.drawable.ic_india, R.drawable.ic_japan, R.drawable.ic_malaysia, R.drawable.ic_russia, R.drawable.ic_singapore, R.drawable.ic_switzerland, R.drawable.ic_united_kingdom, R.drawable.ic_uuss)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_money_conversions)

        val adapter = MoneysConversionsCustomGrid(this, nations, imageId)
        conversionOtherMoneyGridView.adapter = adapter
        conversionOtherMoneyGridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> Toast.makeText(this, "You Clicked at " + nations[+position], Toast.LENGTH_SHORT).show() }

    }
}
