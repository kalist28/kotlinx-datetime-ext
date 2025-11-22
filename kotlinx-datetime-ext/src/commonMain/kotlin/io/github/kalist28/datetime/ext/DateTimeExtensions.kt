@file:Suppress("unused")
@file:OptIn(ExperimentalTime::class)

/**
 * Extensions for working with date and time based on kotlinx-datetime.
 * 
 * This module provides convenient functions for getting the current date and time
 * in various formats with timezone support.
 */
package io.github.kalist28.datetime.ext

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Returns the current moment in time as an [Instant].
 * 
 * [Instant] represents a moment in time on the timeline, independent of timezone.
 * 
 * @return The current moment in time
 * 
 * ```
 * val now = instantNow()
 * println(now) // 2024-01-15T10:30:45.123Z
 * ```
 */
fun instantNow(): Instant = Clock.System.now()

/**
 * Returns the current date as a [LocalDate].
 * 
 * Uses the system timezone by default to determine the current date.
 * 
 * @return The current date in the system timezone
 * 
 * ```
 * val today = dateNow()
 * println(today) // 2024-01-15
 * ```
 */
fun dateNow(): LocalDate = dateTimeNow().date

/**
 * Returns the current time as a [LocalTime].
 * 
 * Uses the system timezone by default to determine the current time.
 * 
 * @return The current time in the system timezone
 * 
 * ```
 * val now = timeNow()
 * println(now) // 10:30:45
 * ```
 */
fun timeNow(): LocalTime = dateTimeNow().time

/**
 * Returns the current date and time as a [LocalDateTime].
 * 
 * Converts the current moment in time to local date and time considering the specified timezone.
 * 
 * @param timeZone The timezone for time conversion. Defaults to the system timezone.
 * @return The current date and time in the specified timezone
 * 
 * ```
 * // Using system timezone
 * val localNow = dateTimeNow()
 * println(localNow) // 2024-01-15T10:30:45
 * 
 * // Using specific timezone
 * val utcNow = dateTimeNow(TimeZone.of("UTC"))
 * println(utcNow) // 2024-01-15T07:30:45 (if system timezone is UTC+3)
 * ```
 */
fun dateTimeNow(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    return instantNow().toLocalDateTime(timeZone)
}
