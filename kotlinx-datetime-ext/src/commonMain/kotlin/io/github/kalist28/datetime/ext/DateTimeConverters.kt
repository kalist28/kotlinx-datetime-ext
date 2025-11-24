@file:Suppress("unused")
@file:OptIn(ExperimentalTime::class)

/**
 * Converters for date and time objects.
 * 
 * Provides conversion functions between LocalDateTime/LocalDate and timestamps (milliseconds),
 * inspired by common conversion patterns.
 */
package io.github.kalist28.datetime.ext

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Converts this [LocalDateTime] to a UTC timestamp in milliseconds.
 * 
 * ```
 * val dateTime = LocalDateTime(2023, 1, 1, 12, 0)
 * val timestamp = dateTime.timestampUtc // milliseconds since epoch in UTC
 * ```
 */
val LocalDateTime.timestampUtc: Long
    get() = toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

/**
 * Converts this [LocalDateTime] to a UTC timestamp in milliseconds.
 * 
 * ```
 * val dateTime = LocalDateTime(2023, 1, 1, 12, 0)
 * val timestamp = dateTime.timestamp // milliseconds since epoch in UTC
 * ```
 */
val LocalDateTime.timestamp: Long
    get() = toInstant(TimeZone.UTC).toEpochMilliseconds()

/**
 * Converts this [LocalDate] to a UTC timestamp in milliseconds (at start of day).
 * 
 * ```
 * val date = LocalDate(2023, 1, 1)
 * val timestamp = date.timestampUtc // milliseconds since epoch at 00:00:00 UTC
 * ```
 */
val LocalDate.timestampUtc: Long
    get() = LocalDateTime(this, LocalTime(0, 0)).timestampUtc

/**
 * Converts this [LocalDate] to a UTC timestamp in milliseconds (at start of day in UTC).
 */
val LocalDate.timestamp: Long
    get() = LocalDateTime(this, LocalTime(0, 0)).timestamp

/**
 * Converts a timestamp (milliseconds since epoch) to [LocalDateTime] in the system timezone.
 * 
 * ```
 * val timestamp = 1672574400000L
 * val dateTime = timestamp.toDateTime() // LocalDateTime in system timezone
 * ```
 */
fun Long.toDateTime(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    return Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
}

/**
 * Converts a timestamp (milliseconds since epoch) to [LocalDateTime] in UTC.
 * 
 * ```
 * val timestamp = 1672574400000L
 * val dateTime = timestamp.toLocalDateTime() // LocalDateTime in UTC
 * ```
 */
fun Long.toLocalDateTime(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDateTime {
    return Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone)
}

/**
 * Converts a timestamp (milliseconds since epoch) to [LocalDate] in the system timezone.
 * 
 * ```
 * val timestamp = 1672574400000L
 * val date = timestamp.toLocalDate() // LocalDate in system timezone
 * ```
 */
fun Long.toLocalDate(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): LocalDate {
    return toLocalDateTime(timeZone).date
}

/**
 * Extension property to convert Long timestamp to LocalDateTime using system timezone.
 * 
 * ```
 * val timestamp = 1672574400000L
 * val dateTime = timestamp.asDateTime // LocalDateTime in system timezone
 * ```
 */
val Long.asDateTime: LocalDateTime
    get() = Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
