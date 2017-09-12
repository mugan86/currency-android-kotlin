package amldev.i18n

import android.content.Context

/***********
 * Created by anartzmugika on 12/9/17.
 */
enum class LanguageEnum {
    SELECT;

    fun getLanguageName(lang: String, context: Context): String {
        when (this) {
            SELECT -> return findLanguageNameWithLanguageCode(lang, context)
            else -> return context.resources.getString(R.string.english)
        }
    }
    // TODO revise!!!!
    private fun findLanguageNameWithLanguageCode(lang: String, context: Context): String {
        println("*********************" + lang)
        if (lang.equals("eu")) {
            context.resources.getString(R.string.basque)
        } else if (lang.equals("es")) {
            return context.resources.getString(R.string.spanish)
        }
        return context.resources.getString(R.string.english)
    }

}