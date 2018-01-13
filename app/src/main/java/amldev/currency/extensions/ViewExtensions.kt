package amldev.currency.extensions

/***********************************
 * Created by Anartz Mugika (mugan86@gmail.com) on 31/7/17.
 ************************/
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

val View.ctx: Context
    get() = context

fun ViewGroup.inflate(resource: Int): View =
        LayoutInflater.from(context).inflate(resource, this, false)

inline fun <reified T : View> View.find(id: Int): T = findViewById<T>(id)

fun View.show() = run { visibility = View.VISIBLE }

fun View.hide() = run { visibility = View.GONE }