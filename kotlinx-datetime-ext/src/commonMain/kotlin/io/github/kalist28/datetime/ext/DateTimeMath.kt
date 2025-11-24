@file:Suppress("unused")
@file:OptIn(ExperimentalTime::class)

/**
 * Mathematical operations for date and time objects.
 * 
 * Provides operator functions and methods for adding/subtracting durations
 * to/from date and time objects, inspired by java.time API.
 */
package io.github.kalist28.datetime.ext

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Adds a [Duration] to this [LocalDateTime].
 * 
 * ```
 * val dateTime = LocalDateTime(2023, 1, 7, 21, 0)
 * val afterFiveDays = dateTime + 5.days
 * ```
 */
operator fun LocalDateTime.plus(duration: Duration): LocalDateTime {
    return plus(duration, TimeZone.currentSystemDefault())
}

/**
 * Adds a [Duration] to this [LocalDateTime].
 *
 * ```
 * val dateTime = LocalDateTime(2023, 1, 7, 21, 0)
 * val afterFiveDays = dateTime + 5.days
 * ```
 */
fun LocalDateTime.plus(
    duration: Duration,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    return toInstant(timeZone)
        .plus(duration)
        .toLocalDateTime(timeZone)
}

/**
 * Subtracts a [Duration] from this [LocalDateTime].
 * 
 * ```
 * val dateTime = LocalDateTime(2023, 1, 7, 21, 0)
 * val beforeThreeHours = dateTime - 3.hours
 * ```
 */
operator fun LocalDateTime.minus(duration: Duration): LocalDateTime {
    return toInstant(TimeZone.currentSystemDefault())
        .minus(duration)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}

/**
 * Subtracts a [Duration] from this [LocalDateTime].
 *
 * ```
 * val dateTime = LocalDateTime(2023, 1, 7, 21, 0)
 * val beforeThreeHours = dateTime - 3.hours
 * ```
 */
fun LocalDateTime.minus(
    duration: Duration,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    return toInstant(timeZone)
        .minus(duration)
        .toLocalDateTime(timeZone)
}

/**
 * Adds a [Duration] to this [LocalDate].
 * 
 * ```
 * val date = LocalDate(2023, 1, 7)
 * val afterFiveDays = date + 5.days
 * ```
 */
operator fun LocalDate.plus(duration: Duration): LocalDate {
    return this.plus(duration.inWholeDays.toInt(), DateTimeUnit.DAY)
}

/**
 * Subtracts a [Duration] from this [LocalDate].
 * 
 * ```
 * val date = LocalDate(2023, 1, 7)
 * val beforeThreeDays = date - 3.days
 * ```
 */
operator fun LocalDate.minus(duration: Duration): LocalDate {
    return this.minus(duration.inWholeDays.toInt(), DateTimeUnit.DAY)
}

/**
 * Adds a [Duration] to this [LocalTime].
 * 
 * ```
 * val time = LocalTime(10, 30)
 * val afterTwoHours = time + 2.hours
 * ```
 */
operator fun LocalTime.plus(duration: Duration): LocalTime {
    val totalSeconds = duration.inWholeSeconds
    val hoursToAdd = (totalSeconds / 3600).toInt()
    val minutesToAdd = ((totalSeconds % 3600) / 60).toInt()
    val secondsToAdd = (totalSeconds % 60).toInt()
    
    var newSecond = second + secondsToAdd
    var newMinute = minute + minutesToAdd
    var newHour = hour + hoursToAdd
    
    if (newSecond >= 60) {
        newMinute += newSecond / 60
        newSecond %= 60
    }
    
    if (newMinute >= 60) {
        newHour += newMinute / 60
        newMinute %= 60
    }
    
    newHour %= 24
    
    return LocalTime(newHour, newMinute, newSecond, nanosecond)
}

/**
 * Subtracts a [Duration] from this [LocalTime].
 * 
 * ```
 * val time = LocalTime(10, 30)
 * val beforeTwoHours = time - 2.hours
 * ```
 */
operator fun LocalTime.minus(duration: Duration): LocalTime {
    val totalSeconds = duration.inWholeSeconds
    val hoursToSubtract = (totalSeconds / 3600).toInt()
    val minutesToSubtract = ((totalSeconds % 3600) / 60).toInt()
    val secondsToSubtract = (totalSeconds % 60).toInt()
    
    var newSecond = second - secondsToSubtract
    var newMinute = minute - minutesToSubtract
    var newHour = hour - hoursToSubtract
    
    if (newSecond < 0) {
        newMinute += (newSecond - 59) / 60
        newSecond = ((newSecond % 60) + 60) % 60
    }
    
    if (newMinute < 0) {
        newHour += (newMinute - 59) / 60
        newMinute = ((newMinute % 60) + 60) % 60
    }
    
    if (newHour < 0) {
        newHour = ((newHour % 24) + 24) % 24
    }
    
    return LocalTime(newHour, newMinute, newSecond, nanosecond)
}

