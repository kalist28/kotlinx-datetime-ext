@file:Suppress("unused")

/**
 * Extension functions for date and time translations.
 */
package io.github.kalist28.datetime.ext.translater

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.format.MonthNames
import kotlin.collections.map

/**
 * Returns the localized name of the month using the provided translator.
 * 
 * @param translator The translator to use
 * @param abbreviated Whether to return abbreviated form
 * @return The localized month name
 */
fun Month.getLocalizedName(translator: DateTimeTranslator, abbreviated: Boolean = false): String {
    return translator.getMonthName(this, abbreviated)
}

/**
 * Returns the localized name of the day of week using the provided translator.
 * 
 * @param translator The translator to use
 * @param abbreviated Whether to return abbreviated form
 * @return The localized day of week name
 */
fun DayOfWeek.getLocalizedName(translator: DateTimeTranslator, abbreviated: Boolean = false): String {
    return translator.getDayOfWeekName(this, abbreviated)
}

/**
 * Returns the localized name of the month for this date using the provided translator.
 * 
 * @param translator The translator to use
 * @param abbreviated Whether to return abbreviated form
 * @return The localized month name
 */
fun LocalDate.getMonthName(translator: DateTimeTranslator, abbreviated: Boolean = false): String {
    return month.getLocalizedName(translator, abbreviated)
}

/**
 * Returns the localized name of the day of week for this date using the provided translator.
 * 
 * @param translator The translator to use
 * @param abbreviated Whether to return abbreviated form
 * @return The localized day of week name
 */
fun LocalDate.getDayOfWeekName(translator: DateTimeTranslator, abbreviated: Boolean = false): String {
    return dayOfWeek.getLocalizedName(translator, abbreviated)
}

fun DateTimeTranslator.getMonthNames(abbreviated: Boolean = false) = Month.entries
    .map { month -> getMonthName(month, abbreviated) }

fun MonthNames.map(block: (String) -> String) =
    MonthNames(names.map(block))

fun MonthNames.mapIndexed(block: (Int, String) -> String) =
    MonthNames(names.mapIndexed(block))

