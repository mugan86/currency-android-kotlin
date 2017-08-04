package com.kotlin.amugika.gridview.ui.adapter

import amldev.currency.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.grid_item_money_conversion.view.*
import amldev.currency.ui.utils.ctx

/**
 * Created by anartzmugika on 3/8/17.
 */

class MoneysConversionsCustomGrid(private val symbols: Array<String>, private val Imageid: IntArray) : BaseAdapter() {

    override fun getCount(): Int = symbols.size

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0L

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var grid: View
        if (convertView == null) {
            grid = (parent.ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.grid_item_money_conversion, null)
            grid.gridText.text = symbols[position]
            grid.gridImage.setImageResource(Imageid[position])
        } else { grid = convertView }

        return grid
    }
}