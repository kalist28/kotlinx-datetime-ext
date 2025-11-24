@file:Suppress("unused")
@file:OptIn(ExperimentalTime::class)

/**
 * Query functions for date and time objects.
 * 
 * Provides boolean properties and functions to check various conditions
 * like isPast, isHoliday, etc.
 * 
 * Some functions inspired by klock library (https://github.com/MaTriXy/klock)
 */
package io.github.kalist28.datetime.ext

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlin.time.ExperimentalTime
import kotlinx.datetime.number

/**
 * Checks if this [LocalDateTime] is in the past.
 * 
 * ```
 * val pastDate = LocalDateTime(2020, 1, 1, 12, 0)
 * val isPast = pastDate.isPast // true
 * ```
 */
val LocalDateTime.isPast: Boolean
    get() = this < dateTimeNow()

/**
 * Checks if this [LocalDate] is in the past.
 * 
 * ```
 * val pastDate = LocalDate(2020, 1, 1)
 * val isPast = pastDate.isPast // true
 * ```
 */
val LocalDate.isPast: Boolean
    get() = this < dateNow()

/**
 * Checks if this [LocalDate] is a holiday (Saturday or Sunday).
 * 
 * ```
 * val date = LocalDate(2023, 1, 7) // Saturday
 * val isHoliday = date.isHoliday // true
 * ```
 */
val LocalDate.isHoliday: Boolean
    get() = listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(dayOfWeek)

/**
 * Checks if this [LocalDate] is a weekday (Monday to Friday).
 * 
 * ```
 * val date = LocalDate(2023, 1, 9) // Monday
 * val isWeekday = date.isWeekday // true
 * ```
 */
val LocalDate.isWeekday: Boolean
    get() = !isHoliday

/**
 * Checks if the year of this [LocalDate] is a leap year.
 * 
 * Inspired by klock library (https://github.com/MaTriXy/klock)
 * 
 * ```
 * val date = LocalDate(2020, 1, 1)
 * val isLeap = date.isLeapYear // true
 * ```
 */
val LocalDate.isLeapYear: Boolean
    get() = year.isLeap()

/**
 * Checks if the specified year is a leap year.
 * 
 * Inspired by klock library (https://github.com/MaTriXy/klock)
 * 
 * ```
 * val isLeap = 2020.isLeap() // true
 * val isNotLeap = 2021.isLeap() // false
 * ```
 */
fun Int.isLeap(): Boolean {
    return (this % 4 == 0 && this % 100 != 0) || (this % 400 == 0)
}

/**
 * Returns the number of days in the month of this [LocalDate].
 * 
 * Inspired by klock library (https://github.com/MaTriXy/klock)
 * 
 * ```
 * val date = LocalDate(2023, 2, 1) // February
 * val days = date.daysInMonth // 28
 * 
 * val leapYearDate = LocalDate(2020, 2, 1) // February in leap year
 * val daysLeap = leapYearDate.daysInMonth // 29
 * ```
 */
val LocalDate.daysInMonth: Int
    get() = when (month.number) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (year.isLeap()) 29 else 28
        else -> throw IllegalStateException("Invalid month: ${month.number}")
    }

/**
 * Returns the number of days in the year of this [LocalDate].
 * 
 * Inspired by klock library (https://github.com/MaTriXy/klock)
 * 
 * ```
 * val date = LocalDate(2020, 1, 1) // Leap year
 * val days = date.daysInYear // 366
 * ```
 */
val LocalDate.daysInYear: Int
    get() = if (year.isLeap()) 366 else 365

