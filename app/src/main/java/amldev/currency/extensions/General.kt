package amldev.currency.extensions

import amldev.currency.App
import amldev.currency.domain.model.Extra
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

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
inline fun <reified T : Activity> Activity.navigate(extras: List<Extra> = emptyList<Extra>(), finishThisActivity: Boolean = true) {
    val i = Intent(this, T::class.java)
    extras.map { data -> i.putExtra(data.label, data.value) }
    startActivity(i)
    if (finishThisActivity) finish()
    overridePendingTransition(0, 0)
}


val Activity.app: App
    get() = application as App

val Context.context: Context
    get() = applicationContext as Context

/**
 * Created by anartzmugika on 23/11/17.
 * More info: https://antonioleiva.com/kotlin-android-extension-functions/vhttps://antonioleiva.com/kotlin-android-extension-functions/
 */
fun Context.showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}