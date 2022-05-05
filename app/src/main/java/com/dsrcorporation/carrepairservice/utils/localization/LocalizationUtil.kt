package uz.rounded.asaxiybooks.utils.localization

import android.content.Context
import android.os.Build
import java.util.*

object LocalizationUtil {
    fun applyLanguage(context: Context, language: String): Context {
        val locale = Locale(language)
        val configuration = context.resources.configuration
        val displayMetrics = context.resources.displayMetrics
        Locale.setDefault(locale)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
            context.createConfigurationContext(configuration)
        } else {
            configuration.locale = locale
            context.resources.updateConfiguration(configuration, displayMetrics)
            context
        }
    }
}