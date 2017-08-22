package amldev.currency.extensions

/**
 * Created by anartzmugika on 22/8/17.
 */
fun <K, V : Any> Map<K, V?>.toVarargArray(): Array<out Pair<K, V>> =
        map({ Pair(it.key, it.value!!) }).toTypedArray()