package anartzmugika.welcomeactivity.ui.adapters

/**
 * Created by anartzmugika on 7/9/17.
 */

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * View pager adapter
 */
class MyViewPageAdapter(private val context: Context, private val layouts: IntArray) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = layoutInflater!!.inflate(layouts[position], container, false)
        container.addView(view)

        return view
    }

    override fun getCount(): Int = layouts.size

    override fun isViewFromObject(view: View, obj: Any): Boolean = view === obj


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}