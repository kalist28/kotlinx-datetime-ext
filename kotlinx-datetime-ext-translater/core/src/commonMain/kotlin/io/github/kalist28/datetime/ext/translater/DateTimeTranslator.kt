@file:Suppress("unused")

/**
 * Base interface for date and time translations.
 * 
 * Provides methods to translate month names and day of week names
 * for different locales.
 */
package io.github.kalist28.datetime.ext.translater

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month

/**
 * Interface for translating month and day of week names.
 */
interface DateTimeTranslator {
    /**
     * Returns the localized name of the month.
     * 
     * @param month The month to translate
     * @param abbreviated Whether to return abbreviated form (e.g., "Jan" vs "January")
     * @return The localized month name
     */
    fun getMonthName(month: Month, abbreviated: Boolean = false): String

    /**
     * Returns the localized name of the day of week.
     * 
     * @param dayOfWeek The day of week to translate
     * @param abbreviated Whether to return abbreviated form (e.g., "Mon" vs "Monday")
     * @return The localized day of week name
     */
    fun getDayOfWeekName(dayOfWeek: DayOfWeek, abbreviated: Boolean = false): String
}


