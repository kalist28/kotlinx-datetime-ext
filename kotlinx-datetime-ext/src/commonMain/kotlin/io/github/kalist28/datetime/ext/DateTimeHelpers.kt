@file:Suppress("unused")
@file:OptIn(ExperimentalTime::class)

/**
 * Helper functions for date and time operations.
 * 
 * Provides convenient extension functions for common date/time operations
 * like getting start/end of day, calculating duration between dates, etc.
 */
package io.github.kalist28.datetime.ext

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Returns a [LocalDateTime] at the start of this day (00:00:00).
 * 
 * ```
 * val date = LocalDate(2023, 12, 17)
 * val startOfDay = date.atStartOfDay() // LocalDateTime(2023, 12, 17, 0, 0)
 * ```
 */
fun LocalDate.atStartOfDay(): LocalDateTime {
    return LocalDateTime(this, LocalTime(0, 0, 0, 0))
}

/**
 * Returns a [LocalDateTime] at the end of this day (23:59:59.999999999).
 * 
 * ```
 * val date = LocalDate(2023, 12, 17)
 * val endOfDay = date.atEndOfDay() // LocalDateTime(2023, 12, 17, 23, 59, 59, 999999999)
 * ```
 */
fun LocalDate.atEndOfDay(): LocalDateTime {
    return LocalDateTime(this, LocalTime(23, 59, 59, 999_999_999))
}

/**
 * Calculates the [Duration] between this [LocalDateTime] and [other].
 * 
 * Uses the system timezone for conversion. For timezone-aware duration calculation,
 * use [durationUntil] with explicit timezone.
 * 
 * ```
 * val first = LocalDateTime(2023, 1, 1, 10, 0)
 * val second = LocalDateTime(2023, 1, 1, 12, 30)
 * val duration = first durationUntil second // 2.5 hours
 * ```
 */
infix fun LocalDateTime.durationUntil(other: LocalDateTime): Duration {
    return durationUntil(other, TimeZone.currentSystemDefault())
}

/**
 * Calculates the [Duration] between this [LocalDateTime] and [other] in the specified [timeZone].
 * 
 * ```
 * val first = LocalDateTime(2023, 1, 1, 10, 0)
 * val second = LocalDateTime(2023, 1, 1, 12, 30)
 * val duration = first.durationUntil(second, TimeZone.of("UTC"))
 * ```
 */
fun LocalDateTime.durationUntil(
    other: LocalDateTime,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Duration {
    val firstInstant = this.toInstant(timeZone)
    val secondInstant = other.toInstant(timeZone)
    return secondInstant - firstInstant
}

/**
 * Calculates the [Duration] between this [LocalDate] and [other].
 * 
 * ```
 * val first = LocalDate(2023, 1, 1)
 * val second = LocalDate(2023, 1, 5)
 * val duration = first durationUntil second // 4 days
 * ```
 */
infix fun LocalDate.durationUntil(other: LocalDate): Duration {
    return this.durationUntil(other, TimeZone.currentSystemDefault())
}

/**
 * Calculates the [Duration] between this [LocalDate] and [other] in the specified [timeZone].
 */
fun LocalDate.durationUntil(
    other: LocalDate,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Duration {
    return this.atStartOfDay().durationUntil(other.atStartOfDay(), timeZone)
}
