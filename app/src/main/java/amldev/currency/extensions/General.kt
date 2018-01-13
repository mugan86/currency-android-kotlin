package amldev.currency.extensions

import amldev.currency.App
import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * Created by anartzmugika on 2/1/18.
 */


/***************************************************************************************************************
 * ¿Alguna vez has intentado utilizar la clase de un tipo genérico dentro de una función? Debido al type erasure
 * (borrado de tipos) que realiza la JVM cuando compila, esto es imposible. Por eso, habrás visto (o incluso usado)
 * muchas veces que se pasa un .class como parámetro de una función.
 *
 * Gracias a las funciones reified, esto va a cambiar.
 *****************************************************************************************************************/
inline fun <reified T : Activity> Context.startActivity() {
    startActivity(Intent(this, T::class.java))
}


val Activity.app: App
    get() = application as App

val Context.context: Context
    get() = applicationContext as Context