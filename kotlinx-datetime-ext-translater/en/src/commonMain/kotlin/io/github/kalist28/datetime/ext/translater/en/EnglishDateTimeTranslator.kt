@file:Suppress("unused")

/**
 * English translation implementation for date and time.
 */
package io.github.kalist28.datetime.ext.translater.en

import io.github.kalist28.datetime.ext.translater.DateTimeTranslator
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month

/**
 * English translator for months and days of week.
 */
object EnglishDateTimeTranslator : DateTimeTranslator {
    private val monthNames = mapOf(
        Month.JANUARY to "January",
        Month.FEBRUARY to "February",
        Month.MARCH to "March",
        Month.APRIL to "April",
        Month.MAY to "May",
        Month.JUNE to "June",
        Month.JULY to "July",
        Month.AUGUST to "August",
        Month.SEPTEMBER to "September",
        Month.OCTOBER to "October",
        Month.NOVEMBER to "November",
        Month.DECEMBER to "December"
    )

    private val monthNamesAbbreviated = mapOf(
        Month.JANUARY to "Jan",
        Month.FEBRUARY to "Feb",
        Month.MARCH to "Mar",
        Month.APRIL to "Apr",
        Month.MAY to "May",
        Month.JUNE to "Jun",
        Month.JULY to "Jul",
        Month.AUGUST to "Aug",
        Month.SEPTEMBER to "Sep",
        Month.OCTOBER to "Oct",
        Month.NOVEMBER to "Nov",
        Month.DECEMBER to "Dec"
    )

    private val dayOfWeekNames = mapOf(
        DayOfWeek.MONDAY to "Monday",
        DayOfWeek.TUESDAY to "Tuesday",
        DayOfWeek.WEDNESDAY to "Wednesday",
        DayOfWeek.THURSDAY to "Thursday",
        DayOfWeek.FRIDAY to "Friday",
        DayOfWeek.SATURDAY to "Saturday",
        DayOfWeek.SUNDAY to "Sunday"
    )

    private val dayOfWeekNamesAbbreviated = mapOf(
        DayOfWeek.MONDAY to "Mon",
        DayOfWeek.TUESDAY to "Tue",
        DayOfWeek.WEDNESDAY to "Wed",
        DayOfWeek.THURSDAY to "Thu",
        DayOfWeek.FRIDAY to "Fri",
        DayOfWeek.SATURDAY to "Sat",
        DayOfWeek.SUNDAY to "Sun"
    )

    override fun getMonthName(month: Month, abbreviated: Boolean): String {
        return if (abbreviated) {
            monthNamesAbbreviated[month] ?: month.name
        } else {
            monthNames[month] ?: month.name
        }
    }

    override fun getDayOfWeekName(dayOfWeek: DayOfWeek, abbreviated: Boolean): String {
        return if (abbreviated) {
            dayOfWeekNamesAbbreviated[dayOfWeek] ?: dayOfWeek.name
        } else {
            dayOfWeekNames[dayOfWeek] ?: dayOfWeek.name
        }
    }
}


