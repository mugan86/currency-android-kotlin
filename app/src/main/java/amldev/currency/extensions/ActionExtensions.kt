package amldev.currency.extensions

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.gson.JsonParser
import java.io.InputStreamReader

/**
 * Created by anartzmugika on 6/8/17.
 */
fun showHideKeyBoardForce(editText: EditText, show: Boolean, context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (show) {
        editText.requestFocus()
        editText.selectAll()
        inputMethodManager.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0)
    }
    else inputMethodManager.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
fun getJSONResource(context: Context, name: String): String? {
    try {
        context.getAssets().open("$name.json").use({ `is` ->
            val parser = JsonParser()
            return parser.parse(InputStreamReader(`is`)).toString()
        })
    } catch (e: Exception) {

    }

    return null
}