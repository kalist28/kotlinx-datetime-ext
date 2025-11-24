@file:Suppress("unused")

/**
 * Russian translation implementation for date and time.
 */
package io.github.kalist28.datetime.ext.translater.ru

import io.github.kalist28.datetime.ext.translater.DateTimeTranslator
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month

/**
 * Russian translator for months and days of week.
 */
object RussianDateTimeTranslator : DateTimeTranslator {
    private val monthNames = mapOf(
        Month.JANUARY to "Январь",
        Month.FEBRUARY to "Февраль",
        Month.MARCH to "Март",
        Month.APRIL to "Апрель",
        Month.MAY to "Май",
        Month.JUNE to "Июнь",
        Month.JULY to "Июль",
        Month.AUGUST to "Август",
        Month.SEPTEMBER to "Сентябрь",
        Month.OCTOBER to "Октябрь",
        Month.NOVEMBER to "Ноябрь",
        Month.DECEMBER to "Декабрь"
    )

    private val monthNamesAbbreviated = mapOf(
        Month.JANUARY to "Янв",
        Month.FEBRUARY to "Фев",
        Month.MARCH to "Мар",
        Month.APRIL to "Апр",
        Month.MAY to "Май",
        Month.JUNE to "Июн",
        Month.JULY to "Июл",
        Month.AUGUST to "Авг",
        Month.SEPTEMBER to "Сен",
        Month.OCTOBER to "Окт",
        Month.NOVEMBER to "Ноя",
        Month.DECEMBER to "Дек"
    )

    private val dayOfWeekNames = mapOf(
        DayOfWeek.MONDAY to "Понедельник",
        DayOfWeek.TUESDAY to "Вторник",
        DayOfWeek.WEDNESDAY to "Среда",
        DayOfWeek.THURSDAY to "Четверг",
        DayOfWeek.FRIDAY to "Пятница",
        DayOfWeek.SATURDAY to "Суббота",
        DayOfWeek.SUNDAY to "Воскресенье"
    )

    private val dayOfWeekNamesAbbreviated = mapOf(
        DayOfWeek.MONDAY to "Пн",
        DayOfWeek.TUESDAY to "Вт",
        DayOfWeek.WEDNESDAY to "Ср",
        DayOfWeek.THURSDAY to "Чт",
        DayOfWeek.FRIDAY to "Пт",
        DayOfWeek.SATURDAY to "Сб",
        DayOfWeek.SUNDAY to "Вс"
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


