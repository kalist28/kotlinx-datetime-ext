@file:Suppress("unused")

/**
 * String templates for DateTimeBundle formatting.
 *
 * Provides interface for localizable strings used in date/time formatting,
 * with Russian implementation.
 */
package io.github.kalist28.datetime.ext.formatted.bundle

/**
 * Interface for all string templates used in DateTimeBundle formatting.
 *
 * Each field is a lambda that accepts a number and returns a formatted string
 * (for relative time like "5 минут назад").
 *
 * This API is experimental and may change in future versions.
 */
@DateTimeFormattedBundleExperimentalApi
interface DateTimeBundleStrings {
    /** Returns "только что" or similar (just now). */
    val justNow: String

    /** Returns formatted string for "in a few seconds" (e.g., "через несколько секунд"). */
    val inFewSeconds: String

    /** Returns formatted string for minutes ago (e.g., "5 минут назад"). */
    fun minutesAgo(minutes: Long): String

    /** Returns formatted string for minutes in future (e.g., "через 5 минут"). */
    fun inMinutes(minutes: Long): String

    /** Returns formatted string for hours ago (e.g., "3 часа назад"). */
    fun hoursAgo(hours: Long): String

    /** Returns formatted string for hours in future (e.g., "через 3 часа"). */
    fun inHours(hours: Long): String

    /** Returns formatted string for days ago (e.g., "5 дней назад"). */
    fun daysAgo(days: Long): String

    /** Returns formatted string for days in future (e.g., "через 5 дней"). */
    fun inDays(days: Long): String

    /** Returns formatted string for months ago (e.g., "2 месяца назад"). */
    fun monthsAgo(months: Long): String

    /** Returns formatted string for months in future (e.g., "через 2 месяца"). */
    fun inMonths(months: Long): String

    /** Returns formatted string for years ago (e.g., "2 года назад"). */
    fun yearsAgo(years: Long): String

    /** Returns formatted string for years in future (e.g., "через 2 года"). */
    fun inYears(years: Long): String

    /** Connector between date and time (e.g., "в" for Russian). */
    val dateConnector: String

    /** Year suffix (e.g., "г." for Russian). */
    val yearSuffix: String

    /** Array of 12 month names in genitive case (e.g., ["января", "февраля", ...]). */
    val months: Array<String>

    /** Array of 12 short month names (e.g., ["янв", "фев", ...]). */
    val shortMonths: Array<String>

    /** Array of 7 day names, starting from Monday (e.g., ["Понедельник", "Вторник", ...]). */
    val daysOfWeek: Array<String>
}

/**
 * Russian implementation of DateTimeBundleStrings.
 *
 * Provides all strings formatted according to Russian grammar rules,
 * including proper pluralization.
 *
 * This API is experimental and may change in future versions.
 */
@DateTimeFormattedBundleExperimentalApi
class RussianDateTimeBundleStrings : DateTimeBundleStrings {
    override val justNow: String = "только что"

    override val inFewSeconds: String = "через несколько секунд"

    override fun minutesAgo(minutes: Long): String =
        PluralUtils.pluralize(minutes, "минуту назад", "минуты назад", "минут назад")

    override fun inMinutes(minutes: Long): String =
        PluralUtils.pluralize(minutes, "минуту", "минуты", "минут").let { "через $it" }

    override fun hoursAgo(hours: Long): String =
        PluralUtils.pluralize(hours, "час назад", "часа назад", "часов назад")

    override fun inHours(hours: Long): String =
        PluralUtils.pluralize(hours, "час", "часа", "часов").let { "через $it" }

    override fun daysAgo(days: Long): String =
        PluralUtils.pluralize(days, "день назад", "дня назад", "дней назад")

    override fun inDays(days: Long): String =
        PluralUtils.pluralize(days, "день", "дня", "дней").let { "через $it" }

    override fun monthsAgo(months: Long): String =
        PluralUtils.pluralize(months, "месяц назад", "месяца назад", "месяцев назад")

    override fun inMonths(months: Long): String =
        PluralUtils.pluralize(months, "месяц", "месяца", "месяцев").let { "через $it" }

    override fun yearsAgo(years: Long): String =
        PluralUtils.pluralize(years, "год назад", "года назад", "лет назад")

    override fun inYears(years: Long): String =
        PluralUtils.pluralize(years, "год", "года", "лет").let { "через $it" }

    override val dateConnector: String = "в"

    override val yearSuffix: String = "г."

    override val months: Array<String> = arrayOf(
        "января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря"
    )

    override val shortMonths: Array<String> = arrayOf(
        "янв", "фев", "мар", "апр", "май", "июн",
        "июл", "авг", "сен", "окт", "ноя", "дек"
    )

    override val daysOfWeek: Array<String> = arrayOf(
        "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"
    )
}

/**
 * English implementation of DateTimeBundleStrings.
 *
 * Provides all strings formatted according to English language rules.
 *
 * This API is experimental and may change in future versions.
 */
@DateTimeFormattedBundleExperimentalApi
class EnglishDateTimeBundleStrings : DateTimeBundleStrings {
    override val justNow: String = "just now"

    override val inFewSeconds: String = "in a few seconds"

    override fun minutesAgo(minutes: Long): String =
        PluralUtils.pluralizeEnglish(minutes, "minute ago", "minutes ago")

    override fun inMinutes(minutes: Long): String =
        PluralUtils.pluralizeEnglish(minutes, "minute", "minutes").let { "in $it" }

    override fun hoursAgo(hours: Long): String =
        PluralUtils.pluralizeEnglish(hours, "hour ago", "hours ago")

    override fun inHours(hours: Long): String =
        PluralUtils.pluralizeEnglish(hours, "hour", "hours").let { "in $it" }

    override fun daysAgo(days: Long): String =
        PluralUtils.pluralizeEnglish(days, "day ago", "days ago")

    override fun inDays(days: Long): String =
        PluralUtils.pluralizeEnglish(days, "day", "days").let { "in $it" }

    override fun monthsAgo(months: Long): String =
        PluralUtils.pluralizeEnglish(months, "month ago", "months ago")

    override fun inMonths(months: Long): String =
        PluralUtils.pluralizeEnglish(months, "month", "months").let { "in $it" }

    override fun yearsAgo(years: Long): String =
        PluralUtils.pluralizeEnglish(years, "year ago", "years ago")

    override fun inYears(years: Long): String =
        PluralUtils.pluralizeEnglish(years, "year", "years").let { "in $it" }

    override val dateConnector: String = "at"

    override val yearSuffix: String = ""

    override val months: Array<String> = arrayOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    override val shortMonths: Array<String> = arrayOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    override val daysOfWeek: Array<String> = arrayOf(
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    )
}
